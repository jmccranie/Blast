package com.cen3031.blast;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import com.cen3031.blast.R;

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

}
