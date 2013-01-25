package com.cen3031.blast;

import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class BluetoothActivity extends Activity {
	public static final int REQUEST_ENABLE_BT = 1;
	private BluetoothAdapter mBluetoothAdapter;
	private ArrayAdapter<String> mArrayAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
		    // Device does not support Bluetooth
			Toast.makeText(getApplicationContext(), "This device does not support bluetooth", Toast.LENGTH_SHORT).show();
			finish();
		}
		else {
			setContentView(R.layout.activity_bluetooth);
			
			if (!mBluetoothAdapter.isEnabled()) {
			    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_bluetooth, menu);
		return true;
	}
	
	@Override
	  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_ENABLE_BT) {
		      if (resultCode == RESULT_OK) {
		    	  Toast.makeText(getApplicationContext(), "Bluetooth enabled", Toast.LENGTH_SHORT).show();
		    	  startBluetoothServices();
		      } 
		      else if (resultCode == RESULT_CANCELED) {
		    	  Toast.makeText(getApplicationContext(), "This mode is not available if bluetooth is not enabled", Toast.LENGTH_SHORT).show();
		    	  finish();
		      }
		}
	}
	
	private void startBluetoothServices() {
		checkForPairedDevices();
	}
	
	private void checkForPairedDevices() {
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		// If there are paired devices
		if (pairedDevices.size() > 0) {
		    // Loop through paired devices
		    for (BluetoothDevice device : pairedDevices) {
		        // Add the name and address to an array adapter to show in a ListView
		        mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
		    }
		}
	}
}
