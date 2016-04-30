/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import werewolvesinwonderland.protocol.ClientMessageBuilder;
import werewolvesinwonderland.protocol.Identification;

/**
 *
 * @author Ahmad Naufal Farhan
 */
public class ClientSender {
    
    /**
     * 
     * @param username
     * @param address
     * @param port
     * @param os
     * @return 
     */
    public static int requestJoinGame(String username, String address, int port, DataOutputStream os) {
        try {
            String requestStr = ClientMessageBuilder.createRequestJoinGame(username, address, port);
            os.writeUTF(requestStr);
            
            ClientController.lastSentMethod = Identification.METHOD_JOIN;
            return 1;
        } catch (JSONException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    /**
     * 
     * @param os
     * @return 
     */
    public static int requestLeaveGame(DataOutputStream os) {
        try {
            String requestStr = ClientMessageBuilder.createRequestLeaveGame();
            os.writeUTF(requestStr);
            
            ClientController.lastSentMethod = Identification.METHOD_LEAVE;
            return 1;
        } catch (JSONException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    /**
     * 
     * @param os
     * @return 
     */
    public static int requestReadyUp(DataOutputStream os) {
        try {
            String requestStr = ClientMessageBuilder.createRequestReadyUp();
            os.writeUTF(requestStr);
            
            ClientController.lastSentMethod = Identification.METHOD_READY;
            return 1;
        } catch (JSONException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    /**
     * 
     * @param os
     * @return 
     */
    public static int requestListClients(DataOutputStream os) {
        try {
            String requestStr = ClientMessageBuilder.createRequestListClient();
            os.writeUTF(requestStr);
            
            ClientController.lastSentMethod = Identification.METHOD_CLIENTADDR;
            return 1;
        } catch (JSONException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    /**
     * Precondition: player calling this method is a KPU
     * @param playerKilled
     * @param voteResult
     * @param os
     * @return 
     */
    public static int sendInfoWerewolfKilled(int playerKilled, String voteResult, DataOutputStream os) {
        try {
            String requestStr = ClientMessageBuilder.createRequestInfoWerewolfKilled(playerKilled, voteResult);
            os.writeUTF(requestStr);
            
            ClientController.lastSentMethod = Identification.METHOD_CLIENTADDR;
            return 1;
        } catch (JSONException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    /**
     * Precondition: player calling this method is a KPU
     * @param voteResult
     * @param os
     * @return 
     */
    public static int sendInfoWerewolfNotKilled(String voteResult, DataOutputStream os) {
        try {
            String requestStr = ClientMessageBuilder.createRequestInfoWerewolfNotKilled(voteResult);
            os.writeUTF(requestStr);
            
            ClientController.lastSentMethod = Identification.METHOD_CLIENTADDR;
            return 1;
        } catch (JSONException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    /**
     * Precondition: player calling this method is a KPU
     * @param playerKilled
     * @param voteResult
     * @param os
     * @return 
     */
    public static int sendInfoCivilianKilled(int playerKilled, String voteResult, DataOutputStream os) {
        try {
            String requestStr = ClientMessageBuilder.createRequestInfoCivilianKilled(playerKilled, voteResult);
            os.writeUTF(requestStr);
            
            ClientController.lastSentMethod = Identification.METHOD_CLIENTADDR;
            return 1;
        } catch (JSONException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    /**
     * Precondition: player calling this method is a KPU
     * @param voteResult
     * @param os
     * @return 
     */
    public static int sendInfoCivilianNotKilled(String voteResult, DataOutputStream os) {
        try {
            String requestStr = ClientMessageBuilder.createRequestInfoCivilianNotKilled(voteResult);
            os.writeUTF(requestStr);
            
            ClientController.lastSentMethod = Identification.METHOD_CLIENTADDR;
            return 1;
        } catch (JSONException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
}
