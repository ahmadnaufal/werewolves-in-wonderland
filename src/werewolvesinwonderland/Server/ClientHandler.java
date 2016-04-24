/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Ahmad Naufal Farhan
 */
public class ClientHandler implements Runnable {
    
    private final Socket mSocket;
    private Thread mThread;
    
    // descriptors to socket input and output
    private DataInputStream is;
    private DataOutputStream os;
    
    private String name = "";
    private boolean isConnected = false;
    
    /**
     * The main constructor for client handlers
     * @param newSocket the connected client socket
     */
    public ClientHandler(Socket newSocket) {
        mSocket = newSocket;
        
        try {
          
            is = new DataInputStream(mSocket.getInputStream());
            os = new DataOutputStream(mSocket.getOutputStream());
            
            isConnected = true;
            
            mThread = new Thread(this);
            start();
        } catch (IOException ex) {
            System.out.println(ex);
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Method to start the handler
     */
    private void start() {
        if (mThread == null)
            mThread = new Thread(this);
        
        mThread.start();
    }
    
    /**
     * Implementing Run method from Runnable
     */
    @Override
    public void run() {
        try {
            while (isConnected) {
                String request = "";
                while (is.available() > 0)
                    request += is.readUTF();
                
                JSONObject requestObj = new JSONObject(request);
                String requestMethod = requestObj.getString(Identification.PRM_METHOD);
                switch (requestMethod) {
                    case Identification.METHOD_JOIN :
                        name = requestObj.getString(Identification.PRM_USERNAME);
                        break;
                    case Identification.METHOD_LEAVE :
                        isConnected = false;
                        break;
                }
            }
            
            // Closing all the sockets and streams
            if (os != null)
                os.close();
            if (is != null)
                is.close();
            if (mSocket != null)
                mSocket.close();
            
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
