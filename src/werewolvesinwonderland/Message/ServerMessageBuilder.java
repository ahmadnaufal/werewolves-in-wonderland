/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.Message;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import werewolvesinwonderland.Message.model.ClientInfo;

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
    public static String createResponseFail() throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_STATUS, Identification.STATUS_FAIL)
                .toString();
    }
    
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
     * #1 Join Game
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
     * #3 Ready Up
     */
    public static String createResponseReadyUpOK() throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_STATUS, Identification.STATUS_OK)
                .put(Identification.PRM_DESCRIPTION, Identification.DESC_WAITINGOTHERS)
                .toString();
    }
    
    /**
     * #4 List Client
     */
    public static String createResponseClientList(ArrayList<ClientInfo> clientList) throws JSONException {
        JSONArray clientListArray = new JSONArray();
        for (ClientInfo client : clientList) {
            JSONObject clientObject = new JSONObject()
                    .put(Identification.PRM_PLAYERID, client.getPlayerId())
                    .put(Identification.PRM_ISALIVE, client.getIsAlive())
                    .put(Identification.PRM_ADDR, client.getAddress())
                    .put(Identification.PRM_PORT, client.getPort())
                    .put(Identification.PRM_USERNAME, client.getUsername());
            
            if (client.getRole() != null)
                clientObject.put(Identification.PRM_ROLE, client.getRole());
            
            clientListArray.put(client);
        }
        
        return new JSONObject()
                .put(Identification.PRM_STATUS, Identification.STATUS_OK)
                .put(Identification.PRM_CLIENTS, clientListArray.toString())
                .put(Identification.PRM_DESCRIPTION, Identification.DESC_LISTCLIENT)
                .toString();
    }
    
    /**
     * #12 Start Game
     */
    public static String createRequestStartGame(String time, String role, ArrayList<String> friend) throws JSONException {
        JSONArray friends = new JSONArray();
        for (String f : friend) {
            friends.put(f);
        }
        return new JSONObject()
                .put(Identification.PRM_METHOD, Identification.METHOD_STARTGAME)
                .put(Identification.PRM_TIME, time)
                .put(Identification.PRM_ROLE, role)
                .put(Identification.PRM_FRIEND, friends.toString()) //TODO: Array friend
                .put(Identification.PRM_DESCRIPTION, Identification.DESC_GAMESTARTED)
                .toString();
    }
    
    /**
     * #13 Change Phase
     */
    public static String createRequestChangePhase(String time, int days) throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_METHOD, Identification.METHOD_CHANGEPHASE)
                .put(Identification.PRM_TIME, time)
                .put(Identification.PRM_DAYS, days)
                .put(Identification.PRM_DESCRIPTION, "")
                .toString();
    }
    
    /**
     * #14 Vote
     */
    public static String createRequestVote(String time) throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_METHOD, Identification.METHOD_VOTENOW)
                .put(Identification.PRM_TIME, time)
                .toString();
    }
    
    /**
     * #15 Game Over
     */
    public static String createRequestGameOver(String winner) throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_METHOD, Identification.METHOD_GAMEOVER)
                .put(Identification.PRM_WINNER, winner)
                .put(Identification.PRM_DESCRIPTION, "")
                .toString();
    }
    
}
