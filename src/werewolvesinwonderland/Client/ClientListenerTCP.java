/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Tifani
 */
public class ClientListenerTCP extends Observable implements Runnable {
    private Socket socket = null;
    private Thread thread = null;
    private BufferedReader br;
    private boolean running = true;
    private ArrayList<Observer> observerList = new ArrayList<>();
    
    public ClientListenerTCP(Socket socket) { //TODO: add observer
        this.socket = socket;
        thread = new Thread(this);
        thread.start();
    }
    
    public void handleResponse(String response){
        JSONParser parser = new JSONParser();
        JSONObject jsonResponse = null;
        try {
            jsonResponse = (JSONObject) parser.parse(response);
        } catch (ParseException ex) {
            Logger.getLogger(ClientListenerTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
        notifyObservers(jsonResponse);
        setChanged();
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
