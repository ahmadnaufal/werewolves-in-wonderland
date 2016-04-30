/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
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
public class ClientListenerTCP extends Observable implements Runnable {
    private Socket socket = null;
    private Thread thread = null;
    
    private boolean running = true;
    private ArrayList<Observer> observerList = new ArrayList<>();
    
    private ClientController handler;
    
    /**
     * Constructors for ClientListenerTCP
     * @param socket
     * @param handler 
     */
    public ClientListenerTCP(Socket socket, ClientController handler) { //TODO: add observer to params
        this.socket = socket;
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
            DataInputStream is = (DataInputStream) socket.getInputStream();
            while (running) {
                String response = "";
                while (is.available() > 0)
                    response += is.readUTF();
                
                handleResponse(response);
            }
            
            socket.close();
        } catch (IOException ex) {
            System.err.println(ex);
            Logger.getLogger(ClientListenerTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
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
