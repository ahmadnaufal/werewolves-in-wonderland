/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.server;

import werewolvesinwonderland.protocol.Identification;
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

    private ServerController mServerHandle;

    // descriptors to socket input and output
    private DataInputStream is;
    private DataOutputStream os;

    private int playerId = -1;
    private boolean running = false;

    /**
     * The main constructor for client handlers
     *
     * @param newSocket the connected client socket
     * @param handle
     */
    public ClientHandler(Socket newSocket, ServerController handle) {
        mSocket = newSocket;
        mServerHandle = handle;

        try {

            is = new DataInputStream(mSocket.getInputStream());
            os = new DataOutputStream(mSocket.getOutputStream());

            running = true;

            mThread = new Thread(this);
            start();
        } catch (IOException ex) {
            System.out.println(ex);
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DataOutputStream getOutputStream() {
        return os;
    }

    public int getPlayerId() {
        return playerId;
    }

    /**
     * Method to start the handler
     */
    private void start() {
        if (mThread == null) {
            mThread = new Thread(this);
        }

        mThread.start();
    }

    /**
     * Implementing Run method from Runnable
     */
    @Override
    public void run() {
        try {
            while (running) {
                String request = "";
                while (is.available() <= 0);
                request += is.readUTF();
                try {
                    System.out.println(request);
                    JSONObject requestObj = new JSONObject(request);
                    String requestMethod = requestObj.getString(Identification.PRM_METHOD);

                    switch (requestMethod) {
                        case Identification.METHOD_JOIN:
                            String username = requestObj.getString(Identification.PRM_USERNAME);
                            String udpAddress = requestObj.getString(Identification.PRM_UDPADDR);
                            int udpPort = requestObj.getInt(Identification.PRM_UDPPORT);

                            System.out.println("REQUEST: A new player attempting to join game. Username: "
                                    + username + ", Address: "
                                    + udpAddress + ":" + udpPort);

                            int _playerId = mServerHandle.getGame().addPlayer(username, udpAddress, udpPort);

                            switch (_playerId) {
                                case -1:    // existing user
                                    System.out.println("FAILED: Client username already exists. Sending fail response...");
                                    ServerSender.sendJoinGameResponseFailUserExist(os);
                                    break;
                                case -2:    // game is running: join game is unable
                                    System.out.println("FAILED: Game is running. Sending fail response...");
                                    ServerSender.sendJoinGameResponseFailGameRunning(os);
                                    break;
                                default:
                                    System.out.println("SUCCESS: Client successfully join the game with Player ID: " + _playerId);
                                    playerId = _playerId;
                                    mServerHandle.mapPlayerClient(this);
                                    ServerSender.sendJoinGameResponseOK(playerId, os);
                                    break;
                            }
                            break;

                        case Identification.METHOD_LEAVE:
                            System.out.println("REQUEST: Player with ID: " + playerId + " attempting to leave the game.");
                            // TODO ini remove player return sesuatu aja ya, biar ada fail response nya
                            mServerHandle.getGame().removePlayer(playerId);
                            // stub
                            if (true) {
                                running = false;
                                ServerSender.sendResponseOK(os);
                            } else {
                                ServerSender.sendResponseFail(os);
                            }
                            break;

                        case Identification.METHOD_READY:
                            System.out.println("REQUEST: Player with ID: " + playerId + " sending ready.");
                            ServerSender.sendResponseReadyUpOK(os);
                            mServerHandle.getGame().increaseReady();
                            break;

                        case Identification.METHOD_CLIENTADDR:
                            System.out.println("REQUEST: Player with ID: " + playerId + " asking for client list.");
                            ServerSender.sendResponseClientList(mServerHandle.getGame().getPlayersList(), os);
                            break;

                        case Identification.METHOD_ACCEPTPROPOSAL:
                            int kpuId = requestObj.getInt(Identification.PRM_KPUID);
                            System.out.println("REQUEST: Player with ID: " + playerId + " accepting proposal from user with ID: " + kpuId);
                            mServerHandle.getGame().addKpuProposal(kpuId);
                            break;

                        case Identification.METHOD_VOTERESULT_WEREWOLF_KILLED:
                            System.out.println("GAME INFO: Civilians are about to capture a player...");
                            // Ini emang sengaja gaada break ya

                        case Identification.METHOD_VOTERESULT_CIVILIAN_KILLED:
                            System.out.println("GAME INFO: A civilian is about to get captured...");
                            // Ini sengaja emang gaada break ya

                        case Identification.METHOD_VOTERESULT:
                            if (playerId == mServerHandle.getGame().getSelectedKpu()) {
                                int status = requestObj.getInt(Identification.PRM_STATUS);
                                switch (status) {
                                    case 1:
                                        int killedId = requestObj.getInt(Identification.PRM_PLAYERKILLED);
                                        mServerHandle.getGame().killPlayer(killedId);
                                        System.out.println("VOTE RESULT: Player " + killedId + " has been brought to its doom...");
                                        break;
                                    case -1:
                                        mServerHandle.getGame().tieVote();
                                        System.out.println("VOTE RESULT: Tie! Nobody is gonna be killed this time.");
                                        break;
                                    default:
                                        ServerSender.sendResponseError(os);
                                        break;
                                }
                            } else {
                                System.out.println("ERROR: Sending Vote Error Result...");
                                ServerSender.sendResponseError(os);
                            }
                            break;

                        default:
                            // No valid actions: send error response: invalid request
                            System.out.println("ERROR: Invalid Request Detected. Sending Error Result...");
                            ServerSender.sendResponseError(os);
                    }
                } catch (JSONException ex) {
                    System.err.println(ex);
                    System.err.println("Sending error response to responsible client...");
                    ServerSender.sendResponseError(os);
                    Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            // Closing all the sockets and streams
            if (os != null) {
                os.close();
            }
            if (is != null) {
                is.close();
            }
            if (mSocket != null) {
                mSocket.close();
            }

        } catch (IOException ex) {
            running = false;
            System.err.println(ex);
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
