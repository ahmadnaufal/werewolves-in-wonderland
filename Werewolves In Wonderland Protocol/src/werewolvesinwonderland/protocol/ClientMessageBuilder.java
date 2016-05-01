/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.protocol;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Tifani
 */
public class ClientMessageBuilder {
    
    /**
     * OK Responses
     * @return 
     * @throws org.json.JSONException
     */
    public static String createResponseOK() throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_STATUS, Identification.STATUS_OK)
                .toString();
    }
    
    public static String createResponseOKAccepted() throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_STATUS, Identification.STATUS_OK)
                .put(Identification.PRM_DESCRIPTION, Identification.DESC_ACCEPTED)
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
    public static String createResponseFailRejected() throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_STATUS, Identification.STATUS_FAIL)
                .put(Identification.PRM_DESCRIPTION, Identification.DESC_REJECTED)
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
     * Client to Server
     */
    public static String createRequestJoinGame(String username, String address, int port) throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_METHOD, Identification.METHOD_JOIN)
                .put(Identification.PRM_USERNAME, username)
                .put(Identification.PRM_UDPADDR, address)
                .put(Identification.PRM_UDPPORT, port)
                .toString();
    }
    
    /**
     * #2 Leave Game
     * Client to Server
     */
    public static String createRequestLeaveGame() throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_METHOD, Identification.METHOD_LEAVE)
                .toString();
    }
    
    /**
     * #3 Ready Up
     * Client to Server
     */
    public static String createRequestReadyUp() throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_METHOD, Identification.METHOD_READY)
                .toString();
    }
    
    /**
     * #4 List Client
     * Client to Server
     */
    public static String createRequestListClient() throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_METHOD, Identification.METHOD_CLIENTADDR)
                .toString();
    }
    
    /**
     * #5 Paxos Prepare Proposal
     * Client (Proposer) to Client (Acceptor)
     */
    public static String createRequestPaxosPrepareProposal(int proposalNumber, int playerId) throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_METHOD, Identification.METHOD_PREPAREPROPOSAL)
                .put(Identification.PRM_PROPOSALID,
                        new JSONArray()
                                .put(proposalNumber)
                                .put(playerId))
                .toString();
    }
    
    /**
     * #5 Paxos Prepare Proposal
     * Client (Acceptor) to Client (Proposer)
     */
    public static String createResponsePaxosPrepareProposalOK(int previousKpuId) throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_STATUS, Identification.STATUS_OK)
                .put(Identification.PRM_DESCRIPTION, Identification.DESC_ACCEPTED)
                .put(Identification.PRM_PREVACC, previousKpuId)
                .toString();
    }
    
    /**
     * #5 Paxos Prepare Proposal
     * Client (Acceptor) to Client (Proposer)
     */
    public static String createResponsePaxosPrepareProposalOK() throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_STATUS, Identification.STATUS_OK)
                .put(Identification.PRM_DESCRIPTION, Identification.DESC_ACCEPTED)
                .toString();
    }
    
    /**
     * #6 Paxos Accept Proposal
     * Client (Proposer) to Client (Acceptor)
     */
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
    
    /**
     * #7 Client Accepted Proposal
     * Client (Acceptor) to Server (Learner)
     */
    public static String createRequestClientAcceptProposal(int kpuId) throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_METHOD, Identification.METHOD_PREPAREPROPOSAL)
                .put(Identification.PRM_KPUID, kpuId)
                .put(Identification.PRM_DESCRIPTION, Identification.DESC_KPUSELECTED)
                .toString();
    }
 
    /**
     * #8 Kill Werewolf Vote
     * Client to KPU
     */
    public static String createRequestKillWerewolfVote(int playerId) throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_METHOD, Identification.METHOD_VOTEWEREWOLF)
                .put(Identification.PRM_PLAYERID, playerId)
                .toString();
    }
    
    /**
     * #9 Info Werewolf Killed
     * KPU to Server
     */
    public static String createRequestInfoWerewolfKilled(int playerKilled, ArrayList<ArrayList<Integer> > voteResult) throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_METHOD, Identification.METHOD_VOTERESULT_WEREWOLF_KILLED)
                .put(Identification.PRM_VOTESTATUS, Identification.STATPLAYER_KILLED)
                .put(Identification.PRM_PLAYERKILLED, playerKilled)
                .put(Identification.PRM_VOTERESULT, new JSONArray(voteResult)) // TODO: bisa dijadiin array etc tergantung representasi vote result
                .toString();
    }
    
    /**
     * #9 Info Werewolf Killed
     * KPU to Server
     */
    public static String createRequestInfoWerewolfNotKilled(ArrayList<ArrayList<Integer> > voteResult) throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_METHOD, Identification.METHOD_VOTERESULT) //TODO: Vote result or vote result werewolf?
                .put(Identification.PRM_VOTESTATUS, Identification.STATPLAYER_NOTKILLED)
                .put(Identification.PRM_VOTERESULT, new JSONArray(voteResult)) // TODO: bisa dijadiin array etc tergantung representasi vote result
                .toString();
    }
    
    /**
     * #10 Kill Civilian Vote
     * Client to KPU
     */
    public static String createRequestKillCivilianVote(int playerId) throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_METHOD, Identification.METHOD_VOTECIVILIAN)
                .put(Identification.PRM_METHOD, playerId)
                .toString();
    }
    
    /**
     * #11 Info Civilian Killed
     * KPU to Server
     */
    public static String createRequestInfoCivilianKilled(int playerKilled, ArrayList<ArrayList<Integer> > voteResult) throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_METHOD, Identification.METHOD_VOTERESULT_CIVILIAN_KILLED)
                .put(Identification.PRM_VOTESTATUS, Identification.STATPLAYER_KILLED)
                .put(Identification.PRM_PLAYERKILLED, playerKilled)
                .put(Identification.PRM_VOTERESULT, new JSONArray(voteResult)) // TODO: bisa dijadiin array etc tergantung representasi vote result
                .toString();
    }
    
    /**
     * #11 Info Civilian Killed
     * KPU to Server
     */
    public static String createRequestInfoCivilianNotKilled(ArrayList<ArrayList<Integer> > voteResult) throws JSONException {
        return new JSONObject()
                .put(Identification.PRM_METHOD, Identification.METHOD_VOTERESULT)
                .put(Identification.PRM_VOTESTATUS, Identification.STATPLAYER_NOTKILLED)
                .put(Identification.PRM_VOTERESULT, new JSONArray(voteResult)) // TODO: bisa dijadiin array etc tergantung representasi vote result
                .toString();           
    }
}
