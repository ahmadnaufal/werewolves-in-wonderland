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
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import werewolvesinwonderland.protocol.Identification;
import werewolvesinwonderland.protocol.model.Player;

/**
 *
 * @author Tifani
 */
public class ClientListenerTCP extends Observable implements Runnable {

    private Socket socket = null;
    private Thread thread = null;

    private boolean running = true;
    private ArrayList<Observer> observerList = new ArrayList<>();

    private ClientController clientHandle;
    private DataOutputStream os;

    /**
     * Constructors for ClientListenerTCP
     * @param socket
     * @param clientHandle
     * @param os
     */
    public ClientListenerTCP(Socket socket, ClientController clientHandle, DataOutputStream os) {
        this.socket = socket;
        this.clientHandle = clientHandle;
        this.os = os;

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
        try {
            String messageMethod = messageObj.getString(Identification.PRM_METHOD);
            String messageDescription = "";
            if (messageObj.has(Identification.PRM_DESCRIPTION))
                messageDescription = messageObj.getString(Identification.PRM_DESCRIPTION);
            //handler.showDialog(description);
            switch (messageMethod) {
                case Identification.METHOD_STARTGAME:
                    String time = messageObj.getString(Identification.PRM_TIME);
                    String role = messageObj.getString(Identification.PRM_ROLE);
                    if (role.equals(Identification.ROLE_WEREWOLF)) {
                        ArrayList<String> werewolfFriends = new ArrayList<>();
                        JSONArray werewolfArray = (JSONArray) messageObj.getJSONArray(Identification.PRM_FRIEND);
                        if (werewolfArray != null) {
                            int len = werewolfArray.length();
                            for(int i=0; i<len; i++)
                                werewolfFriends.add(werewolfArray.getString(i));
                        }
                        clientHandle.getGameHandler().startGame(time, role, werewolfFriends);
                    } else {
                        clientHandle.getGameHandler().startGame(time, role);
                    }
                    clientHandle.getGameHandler().voteKpu = true;
                    ClientSender.requestListClients(os);
                    break;
                case Identification.METHOD_VOTENOW:
                    String phase = messageObj.getString(Identification.PRM_PHASE);
                    ClientSender.requestListClients(os);
                    clientHandle.getGameHandler().startVote(phase);
                    break;
                case Identification.METHOD_CHANGEPHASE:
                    time = messageObj.getString(Identification.PRM_TIME);
                    int days = messageObj.getInt(Identification.PRM_DAYS);
                    clientHandle.getGameHandler().changePhase(time,days);
                    clientHandle.getGameHandler().voteKpu = true;
                    ClientSender.requestListClients(os);
                    break;
                case Identification.METHOD_KPUSELECTED:
                    int selectedKpu = messageObj.getInt(Identification.PRM_KPUID);
                    clientHandle.getGameHandler().setKpu(selectedKpu);
                    break;
                case Identification.METHOD_GAMEOVER:
                    String winner = messageObj.getString(Identification.PRM_WINNER);
                    ClientSender.requestListClients(os);
                    clientHandle.getGameHandler().gameOver(winner);
                    break;
                default:
                    // No valid actions: send error response: invalid request
                    ClientSender.sendResponseError(os);
            }
        } catch (JSONException ex) {
            System.err.println(ex);
            Logger.getLogger(ClientListenerTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleResponse(JSONObject messageObj) {
        System.out.println(ClientListenerTCP.class.getSimpleName() + ": " + messageObj);
        String description = "";
        boolean needToDismissDialog = true;
        try {
            String status = messageObj.getString(Identification.PRM_STATUS);
            switch (status) {
                case Identification.STATUS_OK:
                    if (messageObj.has(Identification.PRM_DESCRIPTION)) {
                        description = messageObj.getString(Identification.PRM_DESCRIPTION);
                    }
                    switch (ClientController.lastSentMethod) {
                        case Identification.METHOD_CLIENTADDR:
                            JSONArray clientArray = messageObj.getJSONArray(Identification.PRM_CLIENTS);
                            ArrayList<Player> playerList = new ArrayList<>();
                            for(int i=0; i<clientArray.length(); i++) {
                                JSONObject clientObject = clientArray.getJSONObject(i);
                                String role = null;
                                if (clientObject.has(Identification.PRM_ROLE))
                                    role = clientObject.getString(Identification.PRM_ROLE);
                                playerList.add(new Player(
                                        clientObject.getInt(Identification.PRM_PLAYERID),
                                        clientObject.getBoolean(Identification.PRM_ISALIVE),
                                        clientObject.getString(Identification.PRM_ADDR),
                                        clientObject.getInt(Identification.PRM_PORT),
                                        clientObject.getString(Identification.PRM_USERNAME),
                                        role));
                            }
                            clientHandle.getGameHandler().updatePlayers(playerList);
                            clientHandle.getGameHandler().getGameFrame().updateBoard();
                            clientHandle.getGameHandler().getGameFrame().changeNumberOfPlayersInfo(
                                    clientHandle.getGameHandler().getGame().getAlivePlayers().size(),
                                    clientHandle.getGameHandler().getGame().getDeadPlayers().size());
                            if (clientHandle.getGameHandler().voteKpu) {
                                clientHandle.getGameHandler().startPaxos();
                                clientHandle.getGameHandler().voteKpu = false;
                            }
                            break;
                        case Identification.METHOD_JOIN:
                            int playerId = messageObj.getInt(Identification.PRM_PLAYERID);
                            clientHandle.getGameHandler().createPlayer(playerId);
                            System.out.println("Successfully Connected to Server." + 
                                                " My player id is: " + playerId);
                            break;
                        case Identification.METHOD_READY:
                            needToDismissDialog = false;
                            break;
                        case Identification.METHOD_LEAVE:
                            clientHandle.getGameHandler().getGameFrame().leaveGameSuccess();
                            running = false;
                            break;
                        default:
                            break;
                    }
                    break;
                case Identification.STATUS_FAIL:
                    if (messageObj.has(Identification.PRM_DESCRIPTION)) {
                        description = messageObj.getString(Identification.PRM_DESCRIPTION);
                        clientHandle.getGameHandler().showErrorDialog(description);
                    }
                    switch (ClientController.lastSentMethod) {
                        case Identification.METHOD_JOIN:
                            break;
                    }
                    break;
                case Identification.STATUS_ERROR:
                    if (messageObj.has(Identification.PRM_DESCRIPTION)) {
                        description = messageObj.getString(Identification.PRM_DESCRIPTION);
                        clientHandle.getGameHandler().showErrorDialog(description);
                    }
                    break;
            }
        } catch (JSONException ex) {
            System.err.println(ex);
            Logger.getLogger(ClientListenerTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
        ClientController.setResponseHasArrived();
        if (needToDismissDialog)
            clientHandle.getGameHandler().getGameFrame().dismissProgressDialog();
    }

    @Override
    public void run() {
        try {
            DataInputStream is = new DataInputStream(socket.getInputStream());
            while (running) {
                String message = "";
                while (is.available() <= 0);
                message += is.readUTF();
                System.out.println(message);
                
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
            }
            if (os != null)
                os.close();
            if (socket != null)
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
