package com.cen3031.blast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

import android.content.Context;
import android.util.Log;

class ClientThread extends Thread {
	Socket socket=null;
	Context context=null;
	String SERVER_IP=null;
	GameState gs=null;
	ObjectOutputStream out=null;
	public ClientThread(Context context, String SERVER_IP){
		this.context=context;
		this.SERVER_IP=SERVER_IP;
	}
	public ClientThread(Context context, GameState gs){
		this.context=context;
		this.gs=gs;
	}
    public void run() {
        try {
            InetAddress serverAddr = InetAddress.getByName("10.137.99.19");
            System.out.println("test sendData7");
            this.socket = new Socket(serverAddr, 8000);
            
//            File file2 = context.getFileStreamPath("tcpaftertest.txt");
//    	    if(file2.exists()){
//    	    	file2.delete();
//    	    }
    	    
//            String str=null;
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
//            	FileOutputStream fos = context.openFileOutput("tcpaftertest.txt",0);
//                OutputStreamWriter osw = new OutputStreamWriter(fos);
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