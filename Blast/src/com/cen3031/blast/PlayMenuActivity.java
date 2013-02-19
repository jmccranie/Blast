package com.cen3031.blast;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class PlayMenuActivity extends Activity {
    private static final int REQUEST_ENABLE_BT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_play_menu, menu);
        return true;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Bluetooth enabled", Toast.LENGTH_SHORT).show();
                Intent btIntent = new Intent(this, DeviceListActivity.class);
                startActivity(btIntent);
            } 
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Bluetooth is required for this mode", Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.tv_pass_n_play:
            //intent = new Intent(this, PlayMenuActivity.class);
            //startActivity(intent);
            break;
        case R.id.tv_bluetooth:
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            
            if (mBluetoothAdapter == null) {
                // Device does not support Bluetooth
                Toast.makeText(getApplicationContext(), "This device does not support bluetooth", Toast.LENGTH_SHORT).show();
            }
            else {
                if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }
                else {
                    Intent btIntent = new Intent(this, DeviceListActivity.class);
                    startActivity(btIntent);
                }
            }
            break;
        case R.id.tv_online:
            Intent intent = new Intent(this, ActiveGameMenuActivity.class);
            startActivity(intent);
            break;
        }
    }

}
