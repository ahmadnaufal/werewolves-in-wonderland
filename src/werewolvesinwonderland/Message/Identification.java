/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.Message;

/**
 *
 * @author Ahmad Naufal Farhan
 */
public class Identification {
    
    /**
     * General method parameter names
     */
    public static final String PRM_METHOD = "method";
    public static final String PRM_STATUS = "status";
    public static final String PRM_DESCRIPTION = "description";
    
    /**
     * Game-Specific parameter names
     */
    // Join Game
    public static final String PRM_USERNAME = "username";
    public static final String PRM_UDPADDR = "udp_address";
    public static final String PRM_UDPPORT = "udp_port";
    public static final String PRM_PLAYERID = "player_id";
    // Leave Game
    public static final String PRM_TIME = "time";
    public static final String PRM_DAYS = "days";
    public static final String PRM_ROLE = "role";
    // List Client
    public static final String PRM_CLIENTS = "clients";
    // Clients Object
    public static final String PRM_ISALIVE = "is_alive";
    public static final String PRM_ADDR = "address";
    public static final String PRM_PORT = "port";
    // Paxos Prepare/Accept Proposal
    public static final String PRM_PROPOSALID = "proposal_id";
    public static final String PRM_PREVACC = "previous_accepted";
    public static final String PRM_KPUID = "kpu_id";
    // Info Werewolf
    public static final String PRM_VOTESTATUS = "vote_status";
    public static final String PRM_PLAYERKILLED = "player_killed";
    public static final String PRM_VOTERESULT = "vote_result";
    // Game Over
    public static final String PRM_WINNER = "winner";
    
    /**
     * Method Parameter possible values
     */
    public static final String METHOD_JOIN = "join";
    public static final String METHOD_LEAVE = "leave";
    public static final String METHOD_READY = "ready";
    public static final String METHOD_CLIENTADDR = "client_address";
    public static final String METHOD_PREPAREPROPOSAL = "prepare_proposal";
    public static final String METHOD_ACCEPTPROPOSAL = "accept_proposal";
    public static final String METHOD_VOTEWEREWOLF = "vote_werewolf";
    public static final String METHOD_VOTERESULT_WEREWOLF_KILLED = "vote_result_werewolf";
    public static final String METHOD_VOTECIVILIAN = "vote_civilian";
    public static final String METHOD_VOTERESULT_CIVILIAN_KILLED = "vote_result_civilian";
    public static final String METHOD_VOTERESULT = "vote_result";
    public static final String METHOD_STARTGAME = "start";
    public static final String METHOD_CHANGEPHASE = "change_phase";
    public static final String METHOD_GAMEOVER = "game_over";
    
    /**
     * Status Parameter possible values
     */
    public static final String STATUS_OK = "ok";
    public static final String STATUS_ERROR = "error";
    public static final String STATUS_FAIL = "fail";
    
    /**
     * Description Parameter possible values
     */
    public static final String DESC_USEREXISTS = "user exists";
    public static final String DESC_GAMESTARTED = "game is started";
    public static final String DESC_GAMERUNNING = "please wait, game is currently running";
    public static final String DESC_WAITINGOTHERS = "waiting for other player to start";
    public static final String DESC_WRONGREQUEST = "wrong request";
    public static final String DESC_LISTCLIENT = "list of clients retrieved";
    public static final String DESC_ACCEPTED = "accepted";
    public static final String DESC_REJECTED = "rejected";
    public static final String DESC_KPUSELECTED = "Kpu is selected";
    
    /**
     * Time Parameter possible values
     */
    public static final String TIME_DAY = "day";
    public static final String TIME_NIGHT = "night";
    
    /**
     * Status Player Parameter possible values
     */
    public final static int STATPLAYER_KILLED = 1;
    public final static int STATPLAYER_NOTKILLED = -1;
}
