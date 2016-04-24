/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.Message;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Tifani
 */
public class ClientMessageBuilder {
    
    public static String createRequestJoinGame(String username) throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_METHOD, Identification.METHOD_JOIN)
                .put(Identification.PRM_USERNAME, username)
                .toString();
    }
    
    public static String createRequestLeaveGame() throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_METHOD, Identification.METHOD_LEAVE)
                .toString();
    }
    
    public static String createRequestReadyUp() throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_METHOD, Identification.METHOD_READY)
                .toString();
    }
    
    public static String createRequestListClient() throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_METHOD, Identification.METHOD_CLIENTADDR)
                .toString();
    }
    
    public static String createRequestPaxosPrepareProposal(String proposalId) throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_METHOD, Identification.METHOD_PREPAREPROPOSAL)
                .put(Identification.PRM_PROPOSALID, proposalId)
                .toString();
    }
}
