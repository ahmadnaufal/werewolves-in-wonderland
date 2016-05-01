/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import werewolvesinwonderland.protocol.ServerMessageBuilder;
import werewolvesinwonderland.protocol.model.Player;
import java.util.ArrayList;

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

    public static int sendResponseClientList(ArrayList<Player> clientList, DataOutputStream os) {
        try {
            String packet = ServerMessageBuilder.createResponseClientList(clientList);
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

    public static int sendRequestStartGame(String time, String role, ArrayList<String> friend, DataOutputStream os) {
        try {
            String packet = ServerMessageBuilder.createRequestStartGame(time, role, friend);
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

    public static int sendRequestChangePhase(String time, int days, DataOutputStream os) {
        try {
            String packet = ServerMessageBuilder.createRequestChangePhase(time, days);
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

    public static int sendRequestGameOver(String winner, DataOutputStream os) {
        try {
            String packet = ServerMessageBuilder.createRequestGameOver(winner);
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

    public static int sendRequestVote(String phase, DataOutputStream os) {
        try {
            String packet = ServerMessageBuilder.createRequestVote(phase);
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

    public static int sendRequestKpuSelected(int kpuId, DataOutputStream os) {
        try {
            String packet = ServerMessageBuilder.createRequestKpuSelected(kpuId);
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
