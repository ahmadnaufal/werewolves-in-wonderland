/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import werewolvesinwonderland.Message.ClientMessageBuilder;

/**
 *
 * @author Ahmad Naufal Farhan
 */
public class ClientSender {
    
    public static int requestJoinGame(String username, DataOutputStream os) {
        try {
            String requestStr = ClientMessageBuilder.createRequestJoinGame(username);
            os.writeUTF(requestStr);
            
            // TODO: Change method sent state to joingame (validate first)
            
        } catch (JSONException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 1;
    }
    
    public static int requestLeaveGame(DataOutputStream os) {
        try {
            String requestStr = ClientMessageBuilder.createRequestLeaveGame();
            os.writeUTF(requestStr);
            
            // TODO: Change method sent state to leavegame (validate first)
            
        } catch (JSONException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 1;
    }
    
    public static int requestReadyUp(DataOutputStream os) {
        try {
            String requestStr = ClientMessageBuilder.createRequestReadyUp();
            os.writeUTF(requestStr);
            
            // TODO: Change method sent state to readyup (validate first)
            
        } catch (JSONException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 1;
    }
    
}
