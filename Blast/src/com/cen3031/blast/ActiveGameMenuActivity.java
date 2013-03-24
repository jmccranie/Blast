package com.cen3031.blast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ActiveGameMenuActivity extends Activity{
	 static final int SERVER_PORT = 8000;
	 String SERVER_IP;
	 Handler handler = new Handler();
	 static Socket socket = null;
     PrintWriter printWriter;
     ObjectInputStream in; 
	 public ListView listView;
	 public List<GameState> tv_games =  new ArrayList<GameState>();
	 GameStateArrayAdaptor adapter;
	/** Called when the activity is first created. */

	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.activity_active_game_menu);
	    Intent intent = getIntent();
		SERVER_IP = intent.getStringExtra("ipAddr");
	   // Toast.makeText(getApplicationContext(), SERVER_IP, Toast.LENGTH_LONG).show();
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
	        createButton.setOnClickListener(new View.OnClickListener() {
	        	public void onClick(View v) {
	        		startGame();
	        		
	        	}
	        });
	        
	     final TextView joinButton = (TextView) findViewById(R.id.joinView);
		    
	        joinButton.setOnClickListener(new View.OnClickListener() {
	        	public void onClick(View v) {
	        		
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
  	         GameState game = new GameState(username,"Waiting for Challenger");
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
	    Thread clientThread = new Thread(new ClientThread());
	    clientThread.start();
	}
		
	 @Override
	    protected void onStop() {
	        super.onStop();
	        try {
	        	Log.d("test1", "Stop Connection");
	        	socket.shutdownInput();
	        	socket.close();
	        } catch (SocketException e) {
	        	Log.d("test2", e.toString());
	        	
	        } catch (Exception e) {
				// TODO Auto-generated catch block
	        	Log.d("test3", e.toString()); 
				e.printStackTrace();
				finish();
			}
	        return;
	    } 	 

	 class ClientThread implements Runnable {
	        public void run() {
	            try {
	                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

	                handler.post(new Runnable() {
	                    @Override
	                    public void run() {
	                    	//Toast.makeText(getApplicationContext(), "Connecting to the Sever", Toast.LENGTH_LONG).show();
	                    }
	                });

	                socket = new Socket(serverAddr, SERVER_PORT);
	                
	                try {
	                    printWriter = new PrintWriter(new BufferedWriter(
	                            new OutputStreamWriter(socket.getOutputStream())),
	                            true);

	                    //---get an InputStream object to read from the server---
	                    in = new ObjectInputStream(socket.getInputStream());
	                    final String strReceived = (String)in.readObject();
	                    try {

	                    	 ActiveGameMenuActivity.this.runOnUiThread(new Runnable() {
		                    	    public void run() {
		                    	    	Toast.makeText(ActiveGameMenuActivity.this, strReceived, Toast.LENGTH_SHORT).show();
		                    	    }
		                    	});
	                        //}

	                        //---disconnected from the server---
	                        handler.post(new Runnable() {
	                            @Override
	                            public void run() {
	                            	//Toast.makeText(getApplicationContext(), SERVER_IP, Toast.LENGTH_LONG).show();
	                            }
	                        });

	                    } catch (Exception e) {
	                        final String error = e.getLocalizedMessage();
	                        handler.post(new Runnable() {
	                            @Override
	                            public void run() {
	                            	Log.d("test2", "IP addr : " + error);
	                            	Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
	                               // textView1.setText(textView1.getText() + "\n" + error);
	                            }
	                        });
	                    }

	                } catch (Exception e) {
	                    final String error = e.getLocalizedMessage();
	                    handler.post(new Runnable() {
	                        @Override
	                        public void run() {
	                        	Log.d("test2", "IP addr : " + error);
	                        }
	                    });
	                }

	                handler.post(new Runnable() {
	                    @Override
	                    public void run() {
	                      //  textView1.setText(textView1.getText() + "\n" + "Connection closed.");
	                    	Log.d("test2", "IP addr : " );
	                    }
	                });

	                
	            } catch (Exception e) {
	                final String error = e.getLocalizedMessage();
	                handler.post(new Runnable() {
	                    @Override
	                    public void run() {
	                    	Log.d("test2", "IP addr : " + error);
	                    	Toast.makeText(getApplicationContext(), "Sorry, Could not connect to Server",
		        	    			Toast.LENGTH_LONG).show();
	                    	finish();
	                    }

	                });

	            }
	        }
	    }
}