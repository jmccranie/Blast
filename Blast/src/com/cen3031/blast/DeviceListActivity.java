package com.cen3031.blast;
//asdd
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DeviceListActivity extends Activity {
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;
	private BluetoothAdapter mBluetoothAdapter;
    private Button mStart;
    private Button mStop;
    private ProgressBar mProgressBar;
    private TextView mDeviceName;
    private TextView mDiscoverability;
    private TextView mScanningTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_device_list);
		
		mStart = (Button) findViewById(R.id.button_start_scan);
		mStop = (Button) findViewById(R.id.button_stop_scan);
		mProgressBar = (ProgressBar) findViewById(R.id.pb_scanning);
		mDeviceName = (TextView) findViewById(R.id.tv_device_name);
		mDiscoverability = (TextView) findViewById(R.id.tv_discoverability);
		mScanningTextView = (TextView) findViewById(R.id.tv_scanning);
		
        // Initialize array adapters. One for already paired devices and
        // one for newly discovered devices
        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);
        mNewDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);
        
        // Find and set up the ListView for paired devices
        ListView pairedListView = (ListView) findViewById(R.id.lv_paired_devices);
        pairedListView.setAdapter(mPairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(mDeviceClickListener);

        // Find and set up the ListView for newly discovered devices
        ListView newDevicesListView = (ListView) findViewById(R.id.lv_new_devices);
        newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
        newDevicesListView.setOnItemClickListener(mDeviceClickListener);
        
        // Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);
        
        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);
        
        // Register for broadcasts when the scan mode has changed
        filter = new IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        this.registerReceiver(mReceiver, filter);
        
        // Register for broadcasts when the local device name has changed
        filter = new IntentFilter(BluetoothAdapter.ACTION_LOCAL_NAME_CHANGED);
        this.registerReceiver(mReceiver, filter);
        
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
        // Get a set of currently paired devices
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        // If there are paired devices, add each one to the ArrayAdapter
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        } 
        else {
            String noDevices = getResources().getText(R.string.none_paired).toString();
            mPairedDevicesArrayAdapter.add(noDevices);
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_device_list, menu);
		return true;
	}
	
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Make sure we're not doing discovery anymore
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.cancelDiscovery();
        }

        // Unregister broadcast listeners
        this.unregisterReceiver(mReceiver);
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        
        mDeviceName.setText("Device Name: " + mBluetoothAdapter.getName());
        
        if (mBluetoothAdapter.getScanMode() == BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            mDiscoverability.setText("Discoverable: Yes");
        }
        else {
            mDiscoverability.setText("Discoverable: No");
        }
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
        case R.id.menu_settings:
            break;
        case R.id.menu_rename_device:
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("Title");
            alert.setMessage("Message");

            // Set an EditText view to get user input 
            final EditText input = new EditText(this);
            input.setText(mBluetoothAdapter.getName());
            alert.setView(input);

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
              mBluetoothAdapter.setName(input.getText().toString());
            }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
              }
            });

            alert.show();
            break;
        }
        return true;
    }
	
	private void doDiscovery() {
	    
        // If we're already discovering, stop it
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        
        // Request discover from BluetoothAdapter
        mBluetoothAdapter.startDiscovery();
        
    }
	
	public void onClick(View v) {
	    switch (v.getId()) {
        case R.id.button_start_scan:
            mStart.setVisibility(View.GONE);
            mStop.setVisibility(View.VISIBLE);
            mScanningTextView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
            mNewDevicesArrayAdapter.clear();
            doDiscovery();
            break;
            
        case R.id.button_stop_scan:
            mStop.setVisibility(View.GONE);
            mScanningTextView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.GONE);
            mStart.setVisibility(View.VISIBLE);
            mBluetoothAdapter.cancelDiscovery();
            break;
            
        case R.id.button_enable_discovery:
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 120);
            startActivity(discoverableIntent);
            break;
	    }
	    
	}
	
    // The on-click listener for all devices in the ListViews
    private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            
            if (mBluetoothAdapter.isDiscovering()) {
             // Cancel discovery because it's costly and we're about to connect
                mBluetoothAdapter.cancelDiscovery();
            }
            
            // Get the device MAC address, which is the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            
            if (info == getResources().getText(R.string.none_found).toString() ||
                info == getResources().getText(R.string.none_found).toString()) 
                return;
            
            String address = info.substring(info.length() - 17);
        }
    };
    
    // The BroadcastReceiver that listens for discovered devices and
    // changes the title when discovery is finished
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }
            // When discovery is finished, change the Activity title
            } 
            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                mStop.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.GONE);
                mScanningTextView.setVisibility(View.GONE);
                mStart.setVisibility(View.VISIBLE);
                
                if (mNewDevicesArrayAdapter.getCount() == 0) {
                    String noDevices = getResources().getText(R.string.none_found).toString();
                    mNewDevicesArrayAdapter.add(noDevices);
                }
            }
            // When device becomes discoverable or undiscoverable
            else if (BluetoothAdapter.ACTION_SCAN_MODE_CHANGED.equals(action)) {
                int mode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR);
              
                switch(mode){
                case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                    mDiscoverability.setText("Discoverable: Yes");
                    break;
                default:
                    mDiscoverability.setText("Discoverable: No");
                    break;
              }
            }
            // When the device name is changed
            else if (BluetoothAdapter.ACTION_LOCAL_NAME_CHANGED.equals(action)) {
                mDeviceName.setText("Device Name: " + mBluetoothAdapter.getName());
            }
        }
    };
}
