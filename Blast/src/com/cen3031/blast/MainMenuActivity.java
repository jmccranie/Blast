package com.cen3031.blast;
//this is new comment
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;


public class MainMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }
    
    public void onClick(View v) {
        Intent intent;
        
        switch (v.getId()) {
        case R.id.play :
            intent = new Intent(this, PlayMenuActivity.class);
            startActivity(intent);
            break;
        case R.id.settings :
            intent = new Intent(this, SettingsMenuActivity.class);
            startActivity(intent);
            break;
        case R.id.help :
            intent = new Intent(this, HelpMenuActivity.class);
            startActivity(intent);
            break;
        }
        
    }
}
