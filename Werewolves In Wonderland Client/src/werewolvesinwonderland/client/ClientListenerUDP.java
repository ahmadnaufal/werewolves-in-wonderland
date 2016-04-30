/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Tifani
 */
public class ClientListenerUDP extends Observable implements Runnable {
    
    private DatagramSocket datagramSocket;
    private Thread thread;
    
    private boolean running = true;
    private ArrayList<Observer> observerList = new ArrayList<>();

    byte[] receiveData = new byte[1024];
    
    private ClientController handler;
    
    /**
     * Default constructor for ClientListenerUDP
     * @param socket the Datagram Socket attached to this listener
     * @param handler
     */
    public ClientListenerUDP(DatagramSocket socket, ClientController handler) {
        this.datagramSocket = socket;
        this.handler = handler;
        
        thread = new Thread(this);
        start();
    }
    
    /**
     * Method to start the handler
     */
    private void start() {
        if (thread == null)
            thread = new Thread(this);
        
        thread.start();
    }
    
    /**
     * Handling given response from server (TCP)
     * @param response 
     */
    public void handleResponse(String response) {
        try {
            JSONObject responseObj = new JSONObject(response);
            notifyObservers(responseObj);
            setChanged();
        } catch (JSONException ex) {
            System.err.println(ex);
            Logger.getLogger(ClientListenerTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            // Process only when active
            while (running) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                datagramSocket.receive(receivePacket);

                String receiveStr = new String(receivePacket.getData());
                handleResponse(receiveStr);
            }
            datagramSocket.close();
        } catch (IOException ex) {
            System.err.println(ex);
            Logger.getLogger(ClientListenerUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * get the socket from our listener
     * @return the socket
     */
    public DatagramSocket getSocket() {
        return datagramSocket;
    }
    
    @Override
    public void notifyObservers(Object arg) {
        // Check if not null
        for (Observer obj : observerList) {
            obj.update(this, arg);
        }
        clearChanged();
    }
    
}
