package com.cen3031.blast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
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
import android.widget.Toast;

public class ActiveGameMenuActivity extends Activity {
	 static final int SERVER_PORT = 8000;
	 String SERVER_IP;
	 Handler handler = new Handler();
	 static Socket socket = null;
     PrintWriter printWriter;
     ObjectInputStream in = null; 
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
	    try{
	    	in = new ObjectInputStream(socket.getInputStream());
	    	final String strReceived = (String)in.readObject();
	    	if(strReceived == null)
	    		Log.d("test1", "IP addr : " + strReceived);
	    	else
	    		Log.d("test1", "Not working");
        	
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                    	Log.d("test2", "IP addr : " + strReceived);
                    	Toast.makeText(getApplicationContext(), strReceived, Toast.LENGTH_LONG).show();
                    }
                });
        } catch (Exception e) { Log.d("test2", "IP addr : FUCK");}
	}
		
	 @Override
	    protected void onStop() {
	        super.onStop();
	        try {
	            socket.shutdownInput();
	            socket.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
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
	                    if(strReceived == null)
	                    	Log.d("test1", "IP addr : " + strReceived);
	                    else
	                    	Log.d("test1", "Not working");
	                    try {

	                            handler.post(new Runnable() {
	                                @Override
	                                public void run() {
	                                	Log.d("test2", "IP addr : " + strReceived);
	                                	Toast.makeText(getApplicationContext(), strReceived, Toast.LENGTH_LONG).show();
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
	                    	Toast.makeText(getApplicationContext(), "last error", Toast.LENGTH_LONG).show();
	                    }
	                });
	            }
	        }
	    }
}