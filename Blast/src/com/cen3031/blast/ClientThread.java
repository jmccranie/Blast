package com.cen3031.blast;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.Statement;

import oracle.jdbc.rowset.OracleCachedRowSet;
import android.content.Context;

class ClientThread extends Thread {
	
	public interface GameReceiver{
		public void getActiveGames(OracleCachedRowSet cset);
	}
	public interface GameUpdateReceiver{
		public void updateGame(OracleCachedRowSet cset);
	}
	ActiveGameMenuActivity menuAct=null;
	UnitAllocationActivity unitAct=null;
	Socket socket=null;
	Context context=null;
	String SERVER_IP=null;
	GameState gs=null;
	ObjectInputStream in=null;
	ObjectOutputStream out=null;
	int option;
	
	public ClientThread(UnitAllocationActivity unitAct, GameState gs){
		this.unitAct=unitAct;
		this.gs=gs;
		this.option=1;
	}
	public ClientThread(ActiveGameMenuActivity menuAct,String SERVER_IP){
		this.menuAct=menuAct;
		this.SERVER_IP=SERVER_IP;
		this.option=4;
	}
    public void run() {
        try {
            InetAddress serverAddr = InetAddress.getByName("10.228.11.237");
            this.socket = new Socket(serverAddr, 8000);
            System.out.println("test Friday1");
                	    
            try {
                //---get an InputStream object to read from the server---
            	in = new ObjectInputStream(socket.getInputStream());
//            	gs = (GameState)in.readObject();
//            	System.out.println((in.readObject()).toString());
//            	System.out.println("hell yeeeeah");
//                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //System.out.println(in==null);
                //str = (in.readLine()).toString();
            	
            	System.out.println("test sendData5");
            	out = new ObjectOutputStream(socket.getOutputStream());
            	out.writeObject(Integer.toString(option));
            	System.out.println("test sendData6");
            	out.flush();
            	
            	switch(option){
                case 1:                     //create new game
                    try{
                    	out.writeObject(gs);
                    	out.flush();
                    }
                    catch(Exception e){

                    }
                    break;
                    
                case 2:         //update active game
                    try{
                    	out.writeObject(gs);
                    	out.flush();
                    }
                    catch(Exception e){

                    }
                    break;
                    
                case 3:             //retrieve available games
                    try{
                    	OracleCachedRowSet cset = (OracleCachedRowSet)in.readObject();
                    	System.out.println(cset.getString(1));
                    }
                    catch(Exception e){
                        
                    }
                    break;
                    
                case 4:             //retrieve active games
                    try{
                        
                        //the players turn
                    	OracleCachedRowSet myturn = (OracleCachedRowSet)in.readObject();
                        
                        //not the player's turn
                    	OracleCachedRowSet notmyturn = (OracleCachedRowSet)in.readObject();
                    	
                        //finished games
                    	OracleCachedRowSet finished = (OracleCachedRowSet)in.readObject();
                    }
                    catch(Exception e){
                        
                    }
                    break;
            	}
            	
            }
            catch(Exception e){
            	System.out.println(e.toString());
            }
//            try{
//
//                osw.write("IP addr : "+gs.getUser1());
//                osw.flush();
//                osw.close();
//                fos.close();
//            } 
//            catch (Exception e) {
//                final String error = e.getLocalizedMessage();
//                Log.d("test2", "IP addr : " + error);
//            }

        } 
        catch (Exception e) {
            final String error = e.getLocalizedMessage();
            System.out.println(error);
        }
        finally{
            //Close connections
            try{
                    //in.close();
                    out.close();
                    socket.close();
            }
            catch(Exception ioException){
                    ioException.printStackTrace();
            }
    }
    }
}