package com.cen3031.blast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;

//final class GifViewUtil extends WebView {
//
//    public GifViewUtil(Context context, String path) {
//        super(context);        
//        loadUrl(path);
//    }
//}
//"file:///res/drawable/explosion.gif"
public class MainMenuActivity extends Activity {
	Animation a;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        a = AnimationUtils.loadAnimation(this, R.anim.alpha);
        a.reset();
        setContentView(R.layout.activity_main_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }
    
    public void btn_play(View v){
    	Button btn = (Button) findViewById(R.id.tv_play);
    	btn.startAnimation(a);
    	Intent intent = new Intent(this, PlayMenuActivity.class);
    	startActivity(intent);
    }
    
    public void btn_settings(View v){
    	Button btn = (Button) findViewById(R.id.tv_settings);
    	btn.startAnimation(a);
    	Intent intent = new Intent(this, SettingsMenuActivity.class);
    	startActivity(intent);
    }
    
    public void btn_help(View v){
    	Button btn = (Button) findViewById(R.id.tv_help);
    	btn.startAnimation(a);
    	Intent intent = new Intent(this, HelpMenuActivity.class);
    	startActivity(intent);
    }
    
    @Override
    protected void onStop() {
        super.onStop();
    }
    
//    public void onClick(View v) {
//        Intent intent;
//        
//        switch (v.getId()) {
//        case R.id.tv_play:
//            intent = new Intent(this, PlayMenuActivity.class);
//            startActivity(intent);
//            break;
//        case R.id.tv_settings:
//            intent = new Intent(this, SettingsMenuActivity.class);
//            startActivity(intent);
//            break;
//        case R.id.tv_help:
//            intent = new Intent(this, HelpMenuActivity.class);
//            startActivity(intent);
//            break;
//        }
//        
//    }
}