package com.cen3031.blast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ActiveGameMenuActivity extends Activity {
	 static final int SERVER_PORT = 8000;
	 String SERVER_IP;
	 Handler handler = new Handler();
	 static Socket socket = null;
     PrintWriter printWriter;
     ObjectInputStream in; 
	 public ListView listView;
	
	/** Called when the activity is first created. */

	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.activity_active_game_menu);
	    Intent intent = getIntent();
		SERVER_IP = intent.getStringExtra("ipAddr");
	   // Toast.makeText(getApplicationContext(), SERVER_IP, Toast.LENGTH_LONG).show();
	    
	    
	    
		//Dynamic ListView
	    List<GameState> tv_games =  new ArrayList<GameState>();
	    //Test Cases
	    GameState game1 = new GameState("Patrick","Pedro");
	    GameState game2 = new GameState("Patrick","Park");
	    GameState game3 = new GameState("Pedro","Patrick");
	    GameState game4 = new GameState("Patrick","Jeff");
	    GameState game5 = new GameState("Patrick","Michael");
	    tv_games.add(game1);
	    tv_games.add(game2);
	    tv_games.add(game3);
	    tv_games.add(game4);
	    tv_games.add(game5);
	    GameStateArrayAdaptor adapter = new GameStateArrayAdaptor(this,tv_games );
 
	    listView = (ListView)findViewById(R.id.yourLayout);
	    listView.setAdapter(adapter);
	    listView.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View view,
	            int position, long id) {
	            
	        		Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_LONG).show();
	         
	        	  }
	      });
	    
	}
	View.OnClickListener myhandler = new View.OnClickListener() {
	    public void onClick(View v) {
	    	
	    }
	};
	 public void onClick(View v) {
		 //enterIP();
	 }
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
	                final TextView activeLabel = (TextView)findViewById(R.id.activeLabel);
//	                handler.post(new Runnable() {
//	                    @Override
//	                    public void run() {
//	                    	//Toast.makeText(getApplicationContext(), "Connecting to the Sever", Toast.LENGTH_LONG).show();
//	                    }
//	                });

	                in = new ObjectInputStream(socket.getInputStream());
	                socket = new Socket(serverAddr, SERVER_PORT);
	                Log.d("socket", "IP addr : ");  
                    final String strReceived = (String)in.readObject();
                    Log.d("test2", "IP addr : " + strReceived);
	                try {
//	                    printWriter = new PrintWriter(new BufferedWriter(
//	                            new OutputStreamWriter(socket.getOutputStream())),
//	                            true);

	                    //---get an InputStream object to read from the server---
	                    
	                    
	                    Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();
	                    try {
	                    	Log.d("socket2", "IP addr : "); 
	                            
	                            ActiveGameMenuActivity.this.runOnUiThread(new Runnable() {
		                    	    public void run() {
		                    	    	Toast.makeText(ActiveGameMenuActivity.this, strReceived, Toast.LENGTH_SHORT).show();
		                    	    }
		                    	});
	                        //}

	                        //---disconnected from the server---
//	                        handler.post(new Runnable() {
//	                            @Override
//	                            public void run() {
//	                            	//Toast.makeText(getApplicationContext(), SERVER_IP, Toast.LENGTH_LONG).show();
//	                            }
//	                        });

	                    } catch (Exception e) {
	                        final String error = e.getLocalizedMessage();
//	                        handler.post(new Runnable() {
//	                            @Override
//	                            public void run() {
//	                            	Log.d("Blast", "IP addr : " + error.toString());
//	                            	Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
//	                               // textView1.setText(textView1.getText() + "\n" + error);
//	                            }
//	                        });
	                    }

	                } catch (Exception e) {
	                    final String error = e.getLocalizedMessage();
//	                    handler.post(new Runnable() {
//	                        @Override
//	                        public void run() {
//	                        	Log.d("test2", "IP addr : " + error);
//	                        }
//	                    });
	                }

//	                handler.post(new Runnable() {
//	                    @Override
//	                    public void run() {
//	                      //  textView1.setText(textView1.getText() + "\n" + "Connection closed.");
//	                    	Log.d("test2", "IP addr : " );
//	                    }
//	                });

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