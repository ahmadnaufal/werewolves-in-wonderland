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

    private void handleRequest(JSONObject messageObj) {
      /* try {
        String messageMethod = messageObj.getString(Identification.PRM_METHOD);
        String messageDescription = messageObj.getString(Identification.PRM_DESCRIPTION);
        //handler.showDialog(description);
        switch (messageMethod) {
          case Identification.METHOD_STARTGAME:
            String time = messageObj.getString(Identification.PRM_TIME);
            String role = messageObj.getString(Identification.PRM_ROLE);
            if (role.equals(Identification.ROLE_WEREWOLF)) {
              //TODO: get array list of werewolf friends from json array
              handler.startGame(time,role,werewolfFriends);
            }
             else {
             handle.startGame(time,role);
           }
            ClientSender.requestListClients(os);
            break;
          case Identification.METHOD_VOTENOW:
            String phase = messageObj.getString(Identification.PRM_PHASE);
            ClientSender.requestListClients(os);
            //handler.startVote();
            break;
          case Identification.METHOD_CHANGEPHASE:
            time = messageObj.getString(Identification.PRM_TIME);
            int days = messageObj.getInt(Identification.PRM_DAYS);
            //handler.changePhase(time,days);
            ClientSender.requestListClients(os);
            break;
          case Identification.METHOD_KPUSELECTED:
            int selectedKpu = messageObj.getInt(Identification.PRM_KPUID);
            //handler.setKpu(selectedKpu);
            break;
          case Identification.METHOD_GAMEOVER:
            String winner = messageObj.getString(Identification.PRM_WINNER);
            ClientSender.requestListClients(os);
            //handler.gameOver(winner);
            break;
          default:
            // No valid actions: send error response: invalid request
            ClientSender.sendResponseError(os);
          }
        }*/
      }

    private void handleResponse(JSONObject messageObj) {
      /* try {
        String status = messageObj.getString(Identification.PRM_STATUS);
        if (messageObj.has(Identification.PRM_DESCRIPTION)) {
            String description = messageObj.getString(Identification.PRM_DESCRIPTION);
            handler.showDialog(description);
        }
        switch (status) {
          case Identification.STATUS_OK :
            switch (ClientController.lastSentMethod) {
              case Identification.METHOD_CLIENTADDR:
                //TODO: get player objects from json array
                //handle.updatePlayers(players);
                break;
              case Identification.METHOD_JOIN:
                int playerId = messageObj.getInt(Identification.PRM_PLAYERID);
                //handle.createPlayer(playerId);
                break;
              case Identification.METHOD_LEAVE:
                //handle.leaveGame();
                break;
              default:
                break;
            }
            break;
          case Identification.STATUS_FAIL :
            switch (ClientController.lastSentMethod) {
              case Identification.METHOD_JOIN:
                if (description.equals(Identification.DESC_USEREXISTS)) {
                  //handle.userExists();
                } else {
                  //ga bisa main
                }
                break;
            }
          }
        } catch (JSONException ex) {
            System.err.println(ex);
            Logger.getLogger(ClientListenerTCP.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }

    @Override
    public void run() {
        try {
            DataInputStream is = new DataInputStream(socket.getInputStream());
            while (running) {
                String message = "";
                while (is.available() > 0)
                    message += is.readUTF();
                    try {
                        JSONObject messageObj = new JSONObject(message);
                        if (messageObj.has(Identification.PRM_METHOD)) {
                            handleRequest(messageObj);
                        } else {
                            handleResponse(messageObj);
                        }
                    } catch (JSONException ex) {
                        System.err.println(ex);
                        Logger.getLogger(ClientListenerTCP.class.getName()).log(Level.SEVERE, null, ex);
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
