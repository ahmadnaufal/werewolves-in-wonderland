/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Tifani
 */
public class ClientListenerUDP implements Runnable {
    
    private DatagramSocket mSocket;
    private Thread mThread;
    
    private boolean isActive = false;
    byte[] receiveData = new byte[1024];
    
    /**
     * Default constructor for ClientListenerUDP
     * @param socket the Datagram Socket attached to this listener
     */
    public ClientListenerUDP(DatagramSocket socket) {
        mSocket = socket;
        isActive = true;
        
        mThread = new Thread(this);
        start();
    }
    
    /**
     * Method to start the handler
     */
    private void start() {
        if (mThread == null)
            mThread = new Thread(this);
        
        mThread.start();
    }

    @Override
    public void run() {
        try {
            // Process only when active
            while (isActive) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                mSocket.receive(receivePacket);

                String receiveStr = new String(receivePacket.getData());
                JSONObject receiveObj = new JSONObject(receiveStr);
                
                // TODO: Process the json object from the server
            }
        } catch (IOException ex) {
            System.err.println(ex);
            Logger.getLogger(ClientListenerUDP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            System.err.println(ex);
            Logger.getLogger(ClientListenerUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * get the socket from our listener
     * @return the socket
     */
    public DatagramSocket getSocket() {
        return mSocket;
    }
    
}
