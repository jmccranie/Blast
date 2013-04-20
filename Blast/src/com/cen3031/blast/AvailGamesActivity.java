package com.cen3031.blast;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AvailGamesActivity extends Activity {
	
	 Button backButton;
	 String phoneID2;
	 String user2;
	 static final int SERVER_PORT = 8000;
	 String SERVER_IP;
	 Handler handler = new Handler();
	 public ListView listView;
	 public List<GameState> tv_games =  new ArrayList<GameState>();
	 GameStateArrayAdaptor adapter;
	 public ArrayList<GameState> availableGames=null;
	 TelephonyManager telephonyManager;
	/** Called when the activity is first created. */

	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    //get phone id
	    setContentView(R.layout.activity_avail_games);
		backButton = (Button) findViewById(R.id.backButton);
		backButton.setOnClickListener(buttonHandler);
		
		//GET phone ID for player2
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			phoneID2 = extras.getString("phoneID");
			user2 = extras.getString("user2");
			if (phoneID2 != null) {
				System.out.println(phoneID2);
			} 
		}
	    Intent intent = getIntent();
		SERVER_IP = intent.getStringExtra("ipAddr");
		 final TextView createButton = (TextView) findViewById(R.id.createView);
		    
		 	adapter = new GameStateArrayAdaptor(this,tv_games );
		    listView = (ListView)findViewById(R.id.gamesList);
		    listView.setAdapter(adapter);
		    listView.setOnItemClickListener(new OnItemClickListener() {
		        public void onItemClick(AdapterView<?> parent, View view,
		            int position, long id) {
		            
		        		Toast.makeText(getApplicationContext(), "Waiting for Challenger...", Toast.LENGTH_LONG).show();
		         
		        	  }
		      });
	}

	@Override
	//FETCH active games from server
	protected void onStart() {
		super.onStart();
	    Intent intent = getIntent();
	    SERVER_IP = intent.getStringExtra("ipAddr");
	    Thread client = new Thread(new ClientThread(this,SERVER_IP));
	    client.start();
	    try{
	    	client.join();
	    	System.out.println(availableGames.get(0).user1ID);
		    //populate active games list 
	    	popGamesList();
        }
	    catch (Exception e) {
    		Toast.makeText(getApplicationContext(), "Sorry, Could not connect to Server", Toast.LENGTH_LONG).show();
	    	//finish();
        	
    	}
	   
	}
	
		
	 @Override
	    protected void onStop() {
	        super.onStop();
	        return;
	    } 	 
	 
	 //populate active games list
	 void popGamesList(){
		 for(int i = 0; i < availableGames.size(); i++){
			 String username = availableGames.get(i).user1name;
  	         GameState game = new GameState(username,"Waiting for Challenger");
			 tv_games.add(game);
			 adapter.notifyDataSetChanged();
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
