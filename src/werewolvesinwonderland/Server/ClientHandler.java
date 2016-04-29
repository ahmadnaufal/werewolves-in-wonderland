/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.Server;

import werewolvesinwonderland.Message.Identification;
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
    
    private int playerId = 0;
    private boolean isConnected = false;
    
    /**
     * The main constructor for client handlers
     * @param newSocket the connected client socket
     * @param handle
     */
    public ClientHandler(Socket newSocket, ServerController handle) {
        mSocket = newSocket;
        mServerHandle = handle;
        
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
                
                try {
                    JSONObject requestObj = new JSONObject(request);
                    String requestMethod = requestObj.getString(Identification.PRM_METHOD);
                    switch (requestMethod) {
                        case Identification.METHOD_JOIN :
                            String username = requestObj.getString(Identification.PRM_USERNAME);
                            int _playerId = mServerHandle.getGame().newPlayer(username);
                            switch (_playerId) {
                                case -1:    // existing user
                                    ServerSender.sendJoinGameResponseFailUserExist(os);
                                    break;
                                case -2:    // game is running: join game is unable
                                    ServerSender.sendJoinGameResponseFailGameRunning(os);
                                    break;
                                default:
                                    playerId = _playerId;
                                    // TODO: get udp_address and udp_port from client
                                    ServerSender.sendJoinGameResponseOK(playerId, os);
                                    break;
                            }
                            break;

                        case Identification.METHOD_LEAVE :
                            isConnected = false;
                            // TODO ini remove player return sesuatu aja ya, biar ada fail response nya
                            mServerHandle.getGame().removePlayer(playerId);
                            if (true) // stub
                                ServerSender.sendResponseOK(os);
                            else
                                ServerSender.sendResponseFail(os);
                            break;

                        case Identification.METHOD_READY :
                            // TODO: Increment ready (validate first)
                            ServerSender.sendResponseReadyUpOK(os);
                            break;
                        
                        case Identification.METHOD_CLIENTADDR :
                            // TODO: Get list of connected clients (player_id, alive status, address, port, and role)
                            // TODO: Send response
                            break;
                            
                        case Identification.METHOD_PREPAREPROPOSAL :
                            int kpuId = requestObj.getInt(Identification.PRM_KPUID);
                            // TODO: Identify which player is the leader (KPU) and flag them as leader
                            if (true) {
                                // TODO: Send OK response
                            } else {
                                // TODO: Send Fail response
                            }
                            break;
                            
                        case Identification.METHOD_VOTERESULT_WEREWOLF_KILLED :
                            // TODO: add vote results
                            break;
                            
                        case Identification.METHOD_VOTERESULT :
                            break;
                            
                        case Identification.METHOD_VOTERESULT_CIVILIAN_KILLED :
                            break;
                        
                        default:
                            // No valid actions: send error response: invalid request
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
            if (os != null)
                os.close();
            if (is != null)
                is.close();
            if (mSocket != null)
                mSocket.close();
            
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
