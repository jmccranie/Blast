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
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ActiveGameMenuActivity extends Activity {
	 static final int SERVER_PORT = 8000;
	 String SERVER_IP;
	 Handler handler = new Handler();
	 public ListView listView;
	 public List<GameState> tv_games =  new ArrayList<GameState>();
	 GameStateArrayAdaptor adapter;
	 public ArrayList<GameState> availableGames=null;
	 String phoneID;
	 TelephonyManager telephonyManager;
	/** Called when the activity is first created. */

	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.activity_active_game_menu);
	    telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
	    phoneID = telephonyManager.getDeviceId(); 
	    Intent intent = getIntent();
		SERVER_IP = intent.getStringExtra("ipAddr");
		 final TextView createButton = (TextView) findViewById(R.id.createView);
		    
		 	adapter = new GameStateArrayAdaptor(this,tv_games );
		    listView = (ListView)findViewById(R.id.theirLayout);
		    listView.setAdapter(adapter);
		    listView.setOnItemClickListener(new OnItemClickListener() {
		        public void onItemClick(AdapterView<?> parent, View view,
		            int position, long id) {
		            
		        		Toast.makeText(getApplicationContext(), "Waiting for Challenger...", Toast.LENGTH_LONG).show();
		         
		        	  }
		      });
		    //Creating a game
	        createButton.setOnClickListener(new View.OnClickListener() {
	        	public void onClick(View v) {
	        		startGame();
	        		
	        	}
	        });
	     
	     //Go to Availible games
	     final TextView joinButton = (TextView) findViewById(R.id.joinView);
	        joinButton.setOnClickListener(new View.OnClickListener() {
	        	public void onClick(View v) {
	        		Intent intent = new Intent(getBaseContext(), AvailGamesActivity.class);
			    	  intent.putExtra("phoneID", phoneID);
			    	  startActivity(intent);
	        	}
	        });
	    
	    
		//Dynamic ListView
	    
	    //Test Cases
//	    GameState game1 = new GameState("Patrick","Pedro");
//	    GameState game2 = new GameState("Patrick","Park");
//	    GameState game3 = new GameState("Pedro","Patrick");
//	    GameState game4 = new GameState("Patrick","Jeff");
//	    GameState game5 = new GameState("Patrick","Michael");
//	    tv_games.add(game1);
//	    tv_games.add(game2);
//	    tv_games.add(game3);
//	    tv_games.add(game4);
//	    tv_games.add(game5);
	   
		
	}
	
	public void startGame(){
		LayoutInflater factory = LayoutInflater.from(this);
  	     final View textEntryView = factory.inflate(R.layout.create_game_dialog, null);
  	     AlertDialog.Builder alert = new AlertDialog.Builder(this);                 
  	     alert.setTitle("Create a Game");  
  	     alert.setMessage("Enter Username:");                
  	     alert.setView(textEntryView);
  	  
  	     alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {  
  	     public void onClick(DialogInterface dialog, int whichButton) {  
  	    	 EditText createText;
	         createText = (EditText) textEntryView.findViewById(R.id.user_name);
	         String username = createText.getText().toString();
  	         GameState game = new GameState(username,"Waiting for Challenger",1);
  	         tv_games.add(game);
  	         adapter.notifyDataSetChanged();
  	         return;                  
  	        }  
  	      });  

  	     alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

  	         public void onClick(DialogInterface dialog, int which) {
  	             // TODO Auto-generated method stub
  	        	 
  	             return;   
  	         }
  	     });
  	             alert.show();   }
   	


	@Override
	protected void onStart() {
		super.onStart();
	    Intent intent = getIntent();
	    SERVER_IP = intent.getStringExtra("ipAddr");
	    Thread client = new Thread(new ClientThread(this,SERVER_IP));
	    client.start();
	    try{
	    	client.join();
	    	System.out.println(availableGames.get(0).user1ID);
        }
	    catch (Exception e) {
    		Toast.makeText(getApplicationContext(), "Sorry, Could not connect to Server", Toast.LENGTH_LONG).show();
	    	finish();
        	
    	}
	}
	
		
	 @Override
	    protected void onStop() {
	        super.onStop();
	        return;
	    } 	 


	        
	    }
