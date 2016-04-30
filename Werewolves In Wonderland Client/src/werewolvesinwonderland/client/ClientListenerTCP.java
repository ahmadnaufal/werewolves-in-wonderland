/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.client;

import java.io.BufferedReader;
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
    private BufferedReader br;
    private boolean running = true;
    private String lastSentMethod = "";
    private ArrayList<Observer> observerList = new ArrayList<>();
    
    public ClientListenerTCP(Socket socket) { //TODO: add observer to params
        this.socket = socket;
        thread = new Thread(this);
        thread.start();
    }
    
    /**
     * set the last sent method
     * @param method
     */
    public void setLastSentMethod(String method) {
        lastSentMethod = method;
    }
    
    public void handleResponse(String response) {
        
        try {
            JSONObject responseObj = new JSONObject(response);
            notifyObservers(responseObj);
            setChanged();
        } catch (JSONException ex) {
            Logger.getLogger(ClientListenerTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response;
            while (running) {
                response = br.readLine();
                handleResponse(response);
            }
            br.close();
            socket.close();
        } catch (IOException ex) {
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
