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
import java.util.HashMap;
import werewolvesinwonderland.Message.ServerMessageBuilder;

/**
 *
 * @author Ahmad Naufal Farhan
 */
public class ServerSender {

    /**
     *
     * @param os
     * @return
     */
    public static int sendResponseOK(DataOutputStream os) {
        try {
            String packet = ServerMessageBuilder.createResponseOK();
            os.writeUTF(packet);

            return 1;
        } catch (JSONException ex) {
            System.err.println(ex);
            Logger.getLogger(ServerSender.class.getName()).log(Level.SEVERE, null, ex);

            return 0;
        } catch (IOException ex) {
            System.err.println(ex);
            Logger.getLogger(ServerSender.class.getName()).log(Level.SEVERE, null, ex);

            return -1;
        }
    }

    /**
     *
     * @param playerId
     * @param os
     * @return
     */
    public static int sendJoinGameResponseOK(int playerId, DataOutputStream os) {
        try {
            String packet = ServerMessageBuilder.createResponseJoinGameOK(playerId);
            os.writeUTF(packet);

            return 1;
        } catch (JSONException ex) {
            System.err.println(ex);
            Logger.getLogger(ServerSender.class.getName()).log(Level.SEVERE, null, ex);

            return 0;
        } catch (IOException ex) {
            System.err.println(ex);
            Logger.getLogger(ServerSender.class.getName()).log(Level.SEVERE, null, ex);

            return -1;
        }
    }

    /**
     *
     * @param os the output stream handler
     * @return
     */
    public static int sendJoinGameResponseFailUserExist(DataOutputStream os) {
        try {
            String packet = ServerMessageBuilder.createResponseJoinGameFailUserExist();
            os.writeUTF(packet);

            return 1;
        } catch (JSONException ex) {
            System.err.println(ex);
            Logger.getLogger(ServerSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException ex) {
            System.err.println(ex);
            Logger.getLogger(ServerSender.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    /**
     *
     * @param os the output stream handler
     * @return
     */
    public static int sendJoinGameResponseFailGameRunning(DataOutputStream os) {
        try {
            String packet = ServerMessageBuilder.createResponseJoinGameFailGameRunning();
            os.writeUTF(packet);
            return 1;
        } catch (JSONException ex) {
            System.err.println(ex);
            Logger.getLogger(ServerSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException ex) {
            System.err.println(ex);
            Logger.getLogger(ServerSender.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    /**
     * 
     * @param os the output stream handler
     * @return
     */
    public static int sendResponseLeaveGameOK(DataOutputStream os) {
        try {
            String packet = ServerMessageBuilder.createResponseOK();
            os.writeUTF(packet);
            return 1;
        } catch (JSONException ex) {
            System.err.println(ex);
            Logger.getLogger(ServerSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException ex) {
            System.err.println(ex);
            Logger.getLogger(ServerSender.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    /**
     *
     * @param os the output stream handler
     * @return
     */
    public static int sendResponseReadyUpOK(DataOutputStream os) {
        try {
            String packet = ServerMessageBuilder.createResponseReadyUpOK();
            os.writeUTF(packet);
            return 1;
        } catch (JSONException ex) {
            System.err.println(ex);
            Logger.getLogger(ServerSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException ex) {
            System.err.println(ex);
            Logger.getLogger(ServerSender.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    /**
     *
     * @param os
     * @return
     */
    public static int sendResponseError(DataOutputStream os) {
        try {
            String packet = ServerMessageBuilder.createResponseError();
            os.writeUTF(packet);
            return 1;
        } catch (JSONException ex) {
            System.err.println(ex);
            Logger.getLogger(ServerSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException ex) {
            System.err.println(ex);
            Logger.getLogger(ServerSender.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    /**
     *
     * @param os
     * @return
     */
    public static int sendResponseFail(DataOutputStream os) {
        try {
            String packet = ServerMessageBuilder.createResponseFail();
            os.writeUTF(packet);
            return 1;
        } catch (JSONException ex) {
            System.err.println(ex);
            Logger.getLogger(ServerSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException ex) {
            System.err.println(ex);
            Logger.getLogger(ServerSender.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

}
