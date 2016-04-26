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
public class ServerMessageBuilder {
    
    /**
     * OK Responses
     */
    public static String createResponseOK() throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_STATUS, Identification.STATUS_OK)
                .toString();
    }
    
    public static String createResponseOK(String description) throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_STATUS, Identification.STATUS_OK)
                .put(Identification.PRM_DESCRIPTION, description)
                .toString();
    }
    
    /**
     * Fail Responses
     */
    public static String createResponseFail(String description) throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_STATUS, Identification.STATUS_FAIL)
                .put(Identification.PRM_DESCRIPTION, description)
                .toString();
    }
    
    /**
     * Error Responses
     */
    public static String createResponseError() throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_STATUS, Identification.STATUS_ERROR)
                .put(Identification.PRM_DESCRIPTION, Identification.DESC_WRONGREQUEST)
                .toString();
    }
    
    public static String createResponseError(String description) throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_STATUS, Identification.STATUS_ERROR)
                .put(Identification.PRM_DESCRIPTION, description)
                .toString();
    }
    
    /**
     * Join Game Responses
     */
    public static String createResponseJoinGameOK(int playerId) throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_STATUS, Identification.STATUS_OK)
                .put(Identification.PRM_PLAYERID, playerId)
                .toString();
    }
    
    public static String createResponseJoinGameFailUserExist() throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_STATUS, Identification.STATUS_FAIL)
                .put(Identification.PRM_DESCRIPTION, Identification.DESC_USEREXISTS)
                .toString();
    }
    
    public static String createResponseJoinGameFailGameRunning() throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_STATUS, Identification.STATUS_FAIL)
                .put(Identification.PRM_DESCRIPTION, Identification.DESC_GAMERUNNING)
                .toString();
    }
    
    /**
     * Leave Game Responses
     */
    
    /**
     * Ready Up Responses
     */
    public static String createResponseReadyUpOK() throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_STATUS, Identification.STATUS_OK)
                .put(Identification.PRM_DESCRIPTION, Identification.DESC_WAITINGOTHERS)
                .toString();
    }
    
    /**
     * List Client Responses
     */
    public static String createResponseClientList() {
        // TODO: Belum dikerjakan
        return null;
    }
    
    
}
