package com.cen3031.blast;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class HelpMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_help_menu, menu);
        return true;
    }

}
