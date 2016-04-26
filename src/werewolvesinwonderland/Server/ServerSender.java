/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import werewolvesinwonderland.Message.ServerMessageBuilder;

/**
 *
 * @author Ahmad Naufal Farhan
 */
public class ServerSender {
    
    /**
     * 
     * @param os 
     */
    public static void sendResponseOK(DataOutputStream os) {
        try {
            String packet = ServerMessageBuilder.createResponseOK();
            os.writeUTF(packet);
        } catch (JSONException ex) {
            System.err.println(ex);
            Logger.getLogger(ServerSender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.err.println(ex);
            Logger.getLogger(ServerSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * @param playerId
     * @param os 
     */
    public static void sendJoinGameResponseOK(int playerId, DataOutputStream os) {
        try {
            String packet = ServerMessageBuilder.createResponseJoinGameOK(playerId);
            os.writeUTF(packet);
        } catch (JSONException ex) {
            System.err.println(ex);
            Logger.getLogger(ServerSender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.err.println(ex);
            Logger.getLogger(ServerSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * @param os the output stream handler
     */
    public static void sendJoinGameResponseFailUserExist(DataOutputStream os) {
        try {
            String packet = ServerMessageBuilder.createResponseJoinGameFailUserExist();
            os.writeUTF(packet);
        } catch (JSONException ex) {
            System.err.println(ex);
            Logger.getLogger(ServerSender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.err.println(ex);
            Logger.getLogger(ServerSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * @param os the output stream handler
     */
    public static void sendJoinGameResponseFailGameRunning(DataOutputStream os) {
        try {
            String packet = ServerMessageBuilder.createResponseJoinGameFailGameRunning();
            os.writeUTF(packet);
        } catch (JSONException ex) {
            System.err.println(ex);
            Logger.getLogger(ServerSender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.err.println(ex);
            Logger.getLogger(ServerSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * @param os the output stream handler
     */
    public static void sendResponseLeaveGameOK(DataOutputStream os) {
        try {
            String packet = ServerMessageBuilder.createResponseOK();
            os.writeUTF(packet);
        } catch (JSONException ex) {
            System.err.println(ex);
            Logger.getLogger(ServerSender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.err.println(ex);
            Logger.getLogger(ServerSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
