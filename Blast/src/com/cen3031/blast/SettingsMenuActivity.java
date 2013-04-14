package com.cen3031.blast;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.cen3031.blast.R;

public class SettingsMenuActivity extends Activity {
    public static final String PREFS_NAME = "BlastPrefs";
    
    private CheckBox mMusic;
    private CheckBox mSFX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_menu);
        
        mMusic = (CheckBox)findViewById(R.id.cb_music);
        mSFX = (CheckBox)findViewById(R.id.cb_sfx);
        
        //load the saved settings checkbox states
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean music = settings.getBoolean("music", false);
        boolean SFX = settings.getBoolean("sfx", false);
        mMusic.setChecked(music);
        mSFX.setChecked(SFX);
        setMusic(music);
        setSFX(SFX);
    }
    
    @Override
    protected void onPause(){
    	super.onPause();
    	
    	if(MainMenuActivity.music){
    		MainMenuActivity.mp.pause();
    	}
    }
    
    @Override
    protected void onResume(){
    	super.onResume();
    	
    	if (MainMenuActivity.music) {
    		MainMenuActivity.mp.start();
    	}
    }
    
    @Override
    protected void onStop() {
        super.onStop();

       //save the current settings checkbox states
       SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
       SharedPreferences.Editor editor = settings.edit();
       editor.putBoolean("music", mMusic.isChecked());
       editor.putBoolean("sfx", mSFX.isChecked());

       editor.commit();
     }
    
    //turn music on or off
    private void setMusic(boolean music) {
        if (!music) {
        	if (MainMenuActivity.mp.isPlaying()) {
        		MainMenuActivity.mp.pause();
        		MainMenuActivity.music = false;
        	}
        }
        else {
        	if (!MainMenuActivity.mp.isPlaying()) {
        		MainMenuActivity.mp.start();
        		MainMenuActivity.music = true;
        	}
        }
    }
    
    //turn SFX on or off
    private void setSFX(boolean onOff) {
        //TODO turn SFX on or off
    }
    
    public void onClick(View v) {
        boolean checked = ((CheckBox) v).isChecked();
        
//        switch (v.getId()) {
//        case R.id.cb_music:
//            mMusic.setChecked(checked);
//            setMusic(checked);
//            break;
//        case R.id.cb_sfx:
//            mSFX.setChecked(checked);
//            setSFX(checked);
//            break;
//        }
    }
}