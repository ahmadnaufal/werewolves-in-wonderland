/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.Server;

/**
 *
 * @author Ahmad Naufal Farhan
 */
public class Identification {
    
    /**
     * General method parameter names
     */
    public static final String PRM_METHOD = "method";
    public static final String PRM_USERNAME = "username";
    public static final String PRM_STATUS = "status";
    public static final String PRM_DESCRIPTION = "description";
    
    /**
     * Game-Specific parameter names
     */
    public static final String PRM_TIME = "time";
    public static final String PRM_DAYS = "days";
    public static final String PRM_ROLE = "role";
    
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
    public static final String METHOD_VOTERESULT_WEREWOLF = "vote_result";
    public static final String METHOD_VOTECIVILIAN = "vote_civilian";
    public static final String METHOD_VOTERESULT_CIVILIAN_KILLED = "vote_result_civilian";
    public static final String METHOD_VOTERESULT_CIVILIAN = "vote_result";
    public static final String METHOD_STARTGAME = "start";
    public static final String METHOD_CHANGEPHASE = "change_phase";
    public static final String METHOD_GAMEOVER = "game_over";
    
    /**
     * Status Parameter possible values
     */
    public static final String STATUS_OK = "ok";
    public static final String STATUS_ERROR = "error";
    public static final String STATUS_FAIL = "fail";
}
