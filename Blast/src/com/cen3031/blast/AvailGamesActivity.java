package com.cen3031.blast;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class AvailGamesActivity extends Activity {
	
	Button backButton;
	String phoneID2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_avail_games);
		backButton = (Button) findViewById(R.id.backButton);
		backButton.setOnClickListener(buttonHandler);
		
		//GET phone ID for player2
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			phoneID2 = extras.getString("phoneID");
			if (phoneID2 != null) {
				System.out.println(phoneID2);
			} 
		}
	}
	
	View.OnClickListener buttonHandler = new View.OnClickListener() {
		  public void onClick(View v) {
		      if( backButton.getId() == ((Button)v).getId() ){
		    	  //go back to active games
		    	  finish();
		      }
		  }
	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.avail_games, menu);
		return true;
	}

}
