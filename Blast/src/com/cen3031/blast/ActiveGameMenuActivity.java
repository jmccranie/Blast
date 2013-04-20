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
	 boolean isStartGame = false;
	/** Called when the activity is first created. */

	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    //get phone id
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
	        		isStartGame = true;
	        		startGame();
	        		
	        	}
	        });
	     
	     //Go to Availible games
	     final TextView joinButton = (TextView) findViewById(R.id.joinView);
	        joinButton.setOnClickListener(new View.OnClickListener() {
	        	public void onClick(View v) {
			    	isStartGame = false;
	        		startGame();
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
  	     
  	     //GO TO UNIT ALLOC when starting new game
  	     alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {  
  	    	 public void onClick(DialogInterface dialog, int whichButton) {  
  	    		EditText createText;
  		        createText = (EditText) textEntryView.findViewById(R.id.user_name);
  		        String username = createText.getText().toString();
  		        if(!username.equals("")){
	 	    		if(isStartGame){
	 	    			isStartGame = false;
	  		        	Intent intent = new Intent(getBaseContext(), UnitAllocationActivity.class);
				    	intent.putExtra("phoneID", phoneID);
				    	intent.putExtra("user",username);
				    	intent.putExtra("activity", "NewGame");
				    	startActivity(intent);
	 	    		}else{
	 	    			Intent intent = new Intent(getBaseContext(), AvailGamesActivity.class);
				    	intent.putExtra("phoneID2", phoneID);
				    	intent.putExtra("user2",username);
				    	startActivity(intent);
	 	    		}
  		        }else{
	        		Toast.makeText(getApplicationContext(), "Please Enter a Username", Toast.LENGTH_LONG).show();
	        		return;
  		        }
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
	    	 popGamesList();
        }
	    catch (Exception e) {
    		Toast.makeText(getApplicationContext(), "Sorry, Could not connect to Server", Toast.LENGTH_LONG).show();
	    	//finish();
        	
    	}
	    //populate active games list
	   
	}
	
		
	 @Override
	    protected void onStop() {
	        super.onStop();
	        return;
	    } 	 
	 
	 void popGamesList(){
		 for(int i = 0; i < availableGames.size(); i++){
			 String username = availableGames.get(i).user1name;
  	         GameState game = new GameState(username,"Waiting for Challenger");
			 tv_games.add(game);
			 adapter.notifyDataSetChanged();
		 }
	 }

	        
}
