/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import werewolvesinwonderland.protocol.ClientMessageBuilder;
import werewolvesinwonderland.protocol.Identification;

/**
 *
 * @author Ahmad Naufal Farhan
 */
public class ClientSender {
    
    /**
     * 
     * @param os
     * @return 
     */
    public static int sendResponseError(DataOutputStream os) {
        try {
            String requestStr = ClientMessageBuilder.createResponseError();
            os.writeUTF(requestStr);
            
            ClientController.lastSentMethod = Identification.METHOD_LEAVE;
            return 1;
        } catch (JSONException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    /**
     * 
     * @param username
     * @param address
     * @param port
     * @param os
     * @return 
     */
    public static int requestJoinGame(String username, String address, int port, DataOutputStream os) {
        System.out.println("Request join game");
        try {
            String requestStr = ClientMessageBuilder.createRequestJoinGame(username, address, port);
            os.writeUTF(requestStr);
            
            ClientController.lastSentMethod = Identification.METHOD_JOIN;
            return 1;
        } catch (JSONException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    /**
     * 
     * @param os
     * @return 
     */
    public static int requestLeaveGame(DataOutputStream os) {
        try {
            String requestStr = ClientMessageBuilder.createRequestLeaveGame();
            os.writeUTF(requestStr);
            
            ClientController.lastSentMethod = Identification.METHOD_LEAVE;
            return 1;
        } catch (JSONException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    /**
     * 
     * @param os
     * @return 
     */
    public static int requestReadyUp(DataOutputStream os) {
        try {
            String requestStr = ClientMessageBuilder.createRequestReadyUp();
            os.writeUTF(requestStr);
            
            ClientController.lastSentMethod = Identification.METHOD_READY;
            return 1;
        } catch (JSONException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    /**
     * 
     * @param os
     * @return 
     */
    public static int requestListClients(DataOutputStream os) {
        try {
            String requestStr = ClientMessageBuilder.createRequestListClient();
            os.writeUTF(requestStr);
            
            ClientController.lastSentMethod = Identification.METHOD_CLIENTADDR;
            return 1;
        } catch (JSONException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    /**
     * Precondition: player calling this method is an acceptor
     * @param kpuId
     * @param os
     * @return 
     */
    public static int sendInfoAcceptedProposal(int kpuId, DataOutputStream os) {
        try {
            String requestStr = ClientMessageBuilder.createRequestClientAcceptProposal(kpuId);
            os.writeUTF(requestStr);
            
            ClientController.lastSentMethod = Identification.METHOD_CLIENTADDR;
            return 1;
        } catch (JSONException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    /**
     * Precondition: player calling this method is a KPU
     * @param playerKilled
     * @param voteResult
     * @param os
     * @return 
     */
    public static int sendInfoWerewolfKilled(int playerKilled, ArrayList<ArrayList<Integer> > voteResult, DataOutputStream os) {
        try {
            String requestStr = ClientMessageBuilder.createRequestInfoWerewolfKilled(playerKilled, voteResult);
            os.writeUTF(requestStr);
            
            ClientController.lastSentMethod = Identification.METHOD_CLIENTADDR;
            return 1;
        } catch (JSONException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    /**
     * Precondition: player calling this method is a KPU
     * @param voteResult
     * @param os
     * @return 
     */
    public static int sendInfoWerewolfNotKilled(ArrayList<ArrayList<Integer> > voteResult, DataOutputStream os) {
        try {
            String requestStr = ClientMessageBuilder.createRequestInfoWerewolfNotKilled(voteResult);
            os.writeUTF(requestStr);
            
            ClientController.lastSentMethod = Identification.METHOD_CLIENTADDR;
            return 1;
        } catch (JSONException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    /**
     * Precondition: player calling this method is a KPU
     * @param playerKilled
     * @param voteResult
     * @param os
     * @return 
     */
    public static int sendInfoCivilianKilled(int playerKilled, ArrayList<ArrayList<Integer> > voteResult, DataOutputStream os) {
        try {
            String requestStr = ClientMessageBuilder.createRequestInfoCivilianKilled(playerKilled, voteResult);
            os.writeUTF(requestStr);
            
            ClientController.lastSentMethod = Identification.METHOD_CLIENTADDR;
            return 1;
        } catch (JSONException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    /**
     * Precondition: player calling this method is a KPU
     * @param voteResult
     * @param os
     * @return 
     */
    public static int sendInfoCivilianNotKilled(ArrayList<ArrayList<Integer> > voteResult, DataOutputStream os) {
        try {
            String requestStr = ClientMessageBuilder.createRequestInfoCivilianNotKilled(voteResult);
            os.writeUTF(requestStr);
            
            ClientController.lastSentMethod = Identification.METHOD_CLIENTADDR;
            return 1;
        } catch (JSONException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException ex) {
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    
    /**** CLIENT-TO-CLIENT COMMUNICATIONS ****/
    
    private static DatagramPacket buildDatagramPacket(String content, InetAddress destAddress, int destPort) {
        return new DatagramPacket(content.getBytes(), content.getBytes().length, destAddress, destPort);
    }
    
    /**
     * 
     * @param playerId
     * @param datagramSocket
     * @param destAddr
     * @param destPort
     * @return 
     */
    public static int sendResponsePaxosPrepareProposalOK(int playerId, DatagramSocket datagramSocket, String destAddr, int destPort) {
        try {
            String requestStr = ClientMessageBuilder.createResponsePaxosPrepareProposalOK(playerId);
            DatagramPacket packet = buildDatagramPacket(requestStr, InetAddress.getByName(destAddr), destPort);
            //UnreliableSender sender = new UnreliableSender(datagramSocket);
            //sender.send(packet);
            datagramSocket.send(packet);
            
            return 1;
            
        } catch (JSONException ex) {
            System.err.println(ex);
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException ex) {
            System.err.println(ex);
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    /**
     * 
     * @param datagramSocket
     * @param destAddr
     * @param destPort
     * @return 
     */
    public static int sendResponsePaxosPrepareProposalOK(DatagramSocket datagramSocket, String destAddr, int destPort) {
        try {
            String requestStr = ClientMessageBuilder.createResponsePaxosPrepareProposalOK();
            DatagramPacket packet = buildDatagramPacket(requestStr, InetAddress.getByName(destAddr), destPort);
            //UnreliableSender sender = new UnreliableSender(datagramSocket);
            //sender.send(packet);
            datagramSocket.send(packet);
            
            return 1;
            
        } catch (JSONException ex) {
            System.err.println(ex);
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException ex) {
            System.err.println(ex);
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    /**
     * 
     * @param datagramSocket
     * @param destAddr
     * @param destPort
     * @return 
     */
    public static int sendResponsePaxosPrepareProposalFail(DatagramSocket datagramSocket, String destAddr, int destPort) {
        try {
            String requestStr = ClientMessageBuilder.createResponseFail(Identification.DESC_REJECTED);
            DatagramPacket packet = buildDatagramPacket(requestStr, InetAddress.getByName(destAddr), destPort);
            //UnreliableSender sender = new UnreliableSender(datagramSocket);
            //sender.send(packet);
            datagramSocket.send(packet);
            
            return 1;
            
        } catch (JSONException ex) {
            System.err.println(ex);
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException ex) {
            System.err.println(ex);
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    /**
     * 
     * @param datagramSocket
     * @param destAddr
     * @param destPort
     * @return 
     */
    public static int sendResponseVoteOK(DatagramSocket datagramSocket, String destAddr, int destPort) {
        try {
            String requestStr = ClientMessageBuilder.createResponseOK("");
            DatagramPacket packet = buildDatagramPacket(requestStr, InetAddress.getByName(destAddr), destPort);
            //UnreliableSender sender = new UnreliableSender(datagramSocket);
            //sender.send(packet);
            datagramSocket.send(packet);
            
            return 1;
            
        } catch (JSONException ex) {
            System.err.println(ex);
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException ex) {
            System.err.println(ex);
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    /**
     * 
     * @param datagramSocket
     * @param destAddr
     * @param destPort
     * @return 
     */
    public static int sendResponseVoteFail(DatagramSocket datagramSocket, String destAddr, int destPort) {
        try {
            String requestStr = ClientMessageBuilder.createResponseFail();
            DatagramPacket packet = buildDatagramPacket(requestStr, InetAddress.getByName(destAddr), destPort);
            //UnreliableSender sender = new UnreliableSender(datagramSocket);
            //sender.send(packet);
            datagramSocket.send(packet);
            
            return 1;
            
        } catch (JSONException ex) {
            System.err.println(ex);
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException ex) {
            System.err.println(ex);
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    /**
     * 
     * @param proposalNumber
     * @param playerId
     * @param datagramSocket
     * @param destAddr
     * @param destPort
     * @return 
     */
    public static int sendPaxosPrepareProposal(int proposalNumber, int playerId, DatagramSocket datagramSocket, String destAddr, int destPort) {
        try {
            String requestStr = ClientMessageBuilder.createRequestPaxosPrepareProposal(proposalNumber, playerId);
            DatagramPacket packet = buildDatagramPacket(requestStr, InetAddress.getByName(destAddr), destPort);
            //UnreliableSender sender = new UnreliableSender(datagramSocket);
            //sender.send(packet);
            datagramSocket.send(packet);
            
            ClientController.lastSentUdpMethod = Identification.METHOD_PREPAREPROPOSAL;
            return 1;
            
        } catch (JSONException ex) {
            System.err.println(ex);
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException ex) {
            System.err.println(ex);
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    /**
     * 
     * @param proposalNumber
     * @param playerId
     * @param kpuId
     * @param datagramSocket
     * @param destAddr
     * @param destPort
     * @return 
     */
    public static int sendPaxosAcceptProposal(int proposalNumber, int playerId, int kpuId, DatagramSocket datagramSocket, String destAddr, int destPort) {
        try {
            String requestStr = ClientMessageBuilder.createRequestPaxosAcceptProposal(proposalNumber, playerId, kpuId);
            DatagramPacket packet = buildDatagramPacket(requestStr, InetAddress.getByName(destAddr), destPort);
            //UnreliableSender sender = new UnreliableSender(datagramSocket);
            //sender.send(packet);
            datagramSocket.send(packet);
            
            ClientController.lastSentUdpMethod = Identification.METHOD_ACCEPTPROPOSAL;
            return 1;
            
        } catch (JSONException ex) {
            System.err.println(ex);
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException ex) {
            System.err.println(ex);
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    /**
     * 
     * @param playerId
     * @param datagramSocket
     * @param destAddr
     * @param destPort
     * @return 
     */
    public static int sendVoteKillWerewolf(int playerId, DatagramSocket datagramSocket, String destAddr, int destPort) {
        try {
            String requestStr = ClientMessageBuilder.createRequestKillWerewolfVote(playerId);
            DatagramPacket packet = buildDatagramPacket(requestStr, InetAddress.getByName(destAddr), destPort);
            datagramSocket.send(packet);
            
            ClientController.lastSentUdpMethod = Identification.METHOD_ACCEPTPROPOSAL;
            return 1;
            
        } catch (JSONException ex) {
            System.err.println(ex);
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException ex) {
            System.err.println(ex);
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    /**
     * 
     * @param playerId
     * @param datagramSocket
     * @param destAddr
     * @param destPort
     * @return 
     */
    public static int sendVoteKillCivilian(int playerId, DatagramSocket datagramSocket, String destAddr, int destPort) {
        try {
            String requestStr = ClientMessageBuilder.createRequestKillCivilianVote(playerId);
            DatagramPacket packet = buildDatagramPacket(requestStr, InetAddress.getByName(destAddr), destPort);
            datagramSocket.send(packet);
            
            ClientController.lastSentUdpMethod = Identification.METHOD_ACCEPTPROPOSAL;
            return 1;
            
        } catch (JSONException ex) {
            System.err.println(ex);
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (IOException ex) {
            System.err.println(ex);
            Logger.getLogger(ClientSender.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
}
