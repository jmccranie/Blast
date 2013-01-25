package com.cen3031.blast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class PlayMenuActivity extends Activity {

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
    
    public void onClick(View v) {
        Intent intent;
        
        switch (v.getId()) {
        case R.id.pass_n_play :
            //intent = new Intent(this, PlayMenuActivity.class);
            //startActivity(intent);
            break;
        case R.id.bluetooth :
            intent = new Intent(this, BluetoothActivity.class);
            startActivity(intent);
            break;
        case R.id.online :
            //intent = new Intent(this, HelpMenuActivity.class);
            //startActivity(intent);
            break;
        }
    }

}
