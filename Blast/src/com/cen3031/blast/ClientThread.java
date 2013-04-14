package com.cen3031.blast;

import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

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
	ObjectOutputStream out=null;
	public ClientThread(UnitAllocationActivity unitAct, GameState gs){
		this.unitAct=unitAct;
		this.gs=gs;
		this.SERVER_IP=SERVER_IP;
	}
	public ClientThread(ActiveGameMenuActivity menuAct,String SERVER_IP){
		this.menuAct=menuAct;
		this.SERVER_IP=SERVER_IP;
	}
    public void run() {
        try {
            InetAddress serverAddr = InetAddress.getByName("192.168.1.110");
            this.socket = new Socket(serverAddr, 8000);
                	    
            try {
                //---get an InputStream object to read from the server---
//            	System.out.println("before same shit");
//            	str=(new BufferedReader(new InputStreamReader(socket.getInputStream()))).readLine();
//            	System.out.println(str);
//            	ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
//            	gs = (GameState)in.readObject();
//            	System.out.println((in.readObject()).toString());
//            	System.out.println("hell yeeeeah");
//                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //System.out.println(in==null);
                //str = (in.readLine()).toString();
            	System.out.println("test sendData5");
            	out = new ObjectOutputStream(socket.getOutputStream());
            	out.writeObject(gs);
            	System.out.println("test sendData6");
            	out.flush();
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