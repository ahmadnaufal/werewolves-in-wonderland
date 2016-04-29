/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Tifani
 */
public class ClientMessageBuilder {
    private final static int STATUS_PLAYER_KILLED = 1;
    private final static int STATUS_PLAYER_NOT_KILLED = -1;
    
    /* Client to Server */
    public static String createRequestJoinGame(String username) throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_METHOD, Identification.METHOD_JOIN)
                .put(Identification.PRM_USERNAME, username)
                .toString();
    }
    
    /* Client to Server */
    public static String createRequestLeaveGame() throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_METHOD, Identification.METHOD_LEAVE)
                .toString();
    }
    
    /* Client to Server */
    public static String createRequestReadyUp() throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_METHOD, Identification.METHOD_READY)
                .toString();
    }
    
    /* Client to Server */
    public static String createRequestListClient() throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_METHOD, Identification.METHOD_CLIENTADDR)
                .toString();
    }
    
    /* Client (Proposer) to Client (Acceptor) */
    public static String createRequestPaxosPrepareProposal(int proposalNumber, int playerId) throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_METHOD, Identification.METHOD_PREPAREPROPOSAL)
                .put(Identification.PRM_PROPOSALID,
                        new JSONArray()
                                .put(proposalNumber)
                                .put(playerId))
                .toString();
    }
    
    /* Client (Proposer) to Client (Acceptor) */
    public static String createRequestPaxosAcceptProposal(int proposalNumber, int playerId, int kpuId) throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_METHOD, Identification.METHOD_ACCEPTPROPOSAL)
                .put(Identification.PRM_PROPOSALID, 
                        new JSONArray()
                                .put(proposalNumber)
                                .put(playerId))
                .put(Identification.PRM_KPUID, kpuId)
                .toString();
    }
    
    /* Client (Acceptor) to Server (Learner) */
    public static String createRequestClientAcceptProposal(int kpuId) throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_METHOD, Identification.METHOD_PREPAREPROPOSAL)
                .put(Identification.PRM_KPUID, kpuId)
                .put(Identification.PRM_DESCRIPTION, Identification.DESC_KPUSELECTED)
                .toString();
    }
 
    /* Client to KPU */
    public static String createRequestKillWerewolfVote(int playerId) throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_METHOD, Identification.METHOD_VOTEWEREWOLF)
                .put(Identification.PRM_PLAYERID, playerId)
                .toString();
    }
    
    /* KPU to Server */
    public static String createRequestInfoWerewolfKilled(int playerKilled, String voteResult) throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_METHOD, Identification.METHOD_VOTERESULT_WEREWOLF_KILLED)
                .put(Identification.PRM_VOTESTATUS, STATUS_PLAYER_KILLED)
                .put(Identification.PRM_PLAYERKILLED, playerKilled)
                .put(Identification.PRM_VOTERESULT, voteResult) // TODO: bisa dijadiin array etc tergantung representasi vote result
                .toString();
    }
    
    /* KPU to Server */
    public static String createRequestInfoWerewolfNotKilled(String voteResult) throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_METHOD, Identification.METHOD_VOTERESULT) //TODO: Vote result or vote result werewolf?
                .put(Identification.PRM_VOTESTATUS, STATUS_PLAYER_NOT_KILLED)
                .put(Identification.PRM_VOTERESULT, voteResult) // TODO: bisa dijadiin array etc tergantung representasi vote result
                .toString();
    }
    
    /* Client to KPU */
    public static String createRequestKillCivilianVote(int playerId) throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_METHOD, Identification.METHOD_VOTECIVILIAN)
                .put(Identification.PRM_METHOD, playerId)
                .toString();
    }
    
    /* KPU to Server */
    public static String createRequestInfoCivilianKilled(int playerKilled, String voteResult) throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_METHOD, Identification.METHOD_VOTERESULT_CIVILIAN_KILLED)
                .put(Identification.PRM_VOTESTATUS, STATUS_PLAYER_KILLED)
                .put(Identification.PRM_PLAYERKILLED, playerKilled)
                .put(Identification.PRM_VOTERESULT, voteResult) // TODO: bisa dijadiin array etc tergantung representasi vote result
                .toString();
    }
    
    /* KPU to Server */
    public static String createRequestInfoCivilianNotKilled(String voteResult) throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_METHOD, Identification.METHOD_VOTERESULT)
                .put(Identification.PRM_VOTESTATUS, STATUS_PLAYER_NOT_KILLED)
                .put(Identification.PRM_VOTERESULT, voteResult)
                .toString();           
    }
}
