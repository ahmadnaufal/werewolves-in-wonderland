/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import werewolvesinwonderland.protocol.Identification;

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
     *
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
        if (thread == null) {
            thread = new Thread(this);
        }

        thread.start();
    }

    private void handleRequest(JSONObject messageObj) throws IOException {
        try {
            String messageMethod = messageObj.getString(Identification.PRM_METHOD);
            String messageDescription = messageObj.getString(Identification.PRM_DESCRIPTION);
            DataOutputStream os = new DataOutputStream(socket.getOutputStream());
            //TODO: alert description
            switch (messageMethod) {
                case Identification.METHOD_STARTGAME:
                    String time = messageObj.getString(Identification.PRM_TIME);
                    String role = messageObj.getString(Identification.PRM_ROLE);
                    if (role.equals(Identification.ROLE_WEREWOLF)) {
                        //TODO: get array list of werewolf friends from json array if role
                    }
                    //TODO: set game variables
                    ClientSender.requestListClients(os);
                    break;
                case Identification.METHOD_VOTENOW:
                    String phase = messageObj.getString(Identification.PRM_PHASE);
                    ClientSender.requestListClients(os);
                    //TODO: ask user to vote next victim then send to kpu
                    break;
                case Identification.METHOD_CHANGEPHASE:
                    time = messageObj.getString(Identification.PRM_TIME);
                    int days = messageObj.getInt(Identification.PRM_DAYS);
                    //TODO: set game variables
                    ClientSender.requestListClients(os);
                    break;
                case Identification.METHOD_KPUSELECTED:
                    int selectedKpu = messageObj.getInt(Identification.PRM_KPUID);
                    //TODO: set kpu variable (in controller?)
                    break;
                case Identification.METHOD_GAMEOVER:
                    String winner = messageObj.getString(Identification.PRM_WINNER);
                    ClientSender.requestListClients(os);
                    //TODO: alert game over, show winner, ask to play again
                    break;
                default:
                    // No valid actions: send error response: invalid request
            }
        } catch (JSONException ex) {
              System.err.println(ex);
              Logger.getLogger(ClientListenerTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleResponse(JSONObject messageObj) {
        try {
            String status = messageObj.getString(Identification.PRM_STATUS);
            String description = "";
            if (messageObj.has(Identification.PRM_DESCRIPTION))
                description = messageObj.getString(Identification.PRM_DESCRIPTION);
                
            //TODO: alert description
            switch (status) {
                case Identification.STATUS_OK:
                    switch (ClientController.lastSentMethod) {
                        case Identification.METHOD_CLIENTADDR:
                            //TODO: get player objects from json array, pass to controller
                            break;
                        case Identification.METHOD_JOIN:
                            int playerId = messageObj.getInt(Identification.PRM_PLAYERID);
                            //TODO: set player id of client
                            System.out.println(playerId);
                            break;
                        case Identification.METHOD_LEAVE:
                            //TODO: quit game
                            break;
                        default:
                            break;
                    }
                    break;
                case Identification.STATUS_FAIL:
                    switch (ClientController.lastSentMethod) {
                        case Identification.METHOD_JOIN:
                            if (description.equals(Identification.DESC_USEREXISTS)) {
                                //TODO: minta username lagi
                            } else {
                                //ga bisa main
                            }
                            break;
                    }
                    break;
                case Identification.STATUS_ERROR:
                    break;
            }
        } catch (JSONException ex) {
            System.err.println(ex);
            Logger.getLogger(ClientListenerTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            DataInputStream is = new DataInputStream(socket.getInputStream());
            while (running) {
                String message = "";
                while (is.available() <= 0);
                message += is.readUTF();
                
                try {
                    JSONObject messageObj = new JSONObject(message);
                    if (!messageObj.has(Identification.PRM_METHOD)) { //response
                        handleResponse(messageObj);
                    } else { //request
                        handleRequest(messageObj);
                    }
                } catch (JSONException ex) {
                    System.err.println(ex);
                    Logger.getLogger(ClientListenerTCP.class.getName()).log(Level.SEVERE, null, ex);
                }
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
