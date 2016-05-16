/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import werewolvesinwonderland.protocol.Identification;

/**
 *
 * @author Tifani
 */
public class ClientListenerUDP extends Observable implements Runnable {

    private DatagramSocket datagramSocket;
    private Thread thread;

    private boolean running = true;
    private ArrayList<Observer> observerList = new ArrayList<>();

    byte[] receiveData = new byte[1024];

    private ClientController clientHandle;

    /**
     * Default constructor for ClientListenerUDP
     * @param socket the Datagram Socket attached to this listener
     * @param handler
     */
    public ClientListenerUDP(DatagramSocket socket, ClientController handler) {
        this.datagramSocket = socket;
        this.clientHandle = handler;

        thread = new Thread(this);
        start();
    }

    /**
     * Method to start the handler
     */
    private void start() {
        if (thread == null)
            thread = new Thread(this);

        thread.start();
    }

    /**
     * Handling given response from server (TCP)
     * @param messageObj
     * @param requestAddress
     * @param requestPort
     */
    public void handleRequest(JSONObject messageObj, String requestAddress, int requestPort) {
        try {
            String messageMethod = messageObj.getString(Identification.PRM_METHOD);
            //handler.showDialog(description);
            switch (messageMethod) {
                case Identification.METHOD_PREPAREPROPOSAL:
                    JSONArray proposal = messageObj.getJSONArray(Identification.PRM_PROPOSALID);
                    int proposalNumber = proposal.getInt(0);
                    int proposerId = proposal.getInt(1);
                    int result = clientHandle.getGameHandler().getAcceptorController().promiseProposal(proposalNumber, proposerId);
                    switch (result) {
                        case 1: // has not seen other proposal before
                            ClientSender.sendResponsePaxosPrepareProposalOK(datagramSocket, requestAddress, requestPort);
                            break;
                        case 0:
                            break;
                        case -1:
                            ClientSender.sendResponsePaxosPrepareProposalFail(datagramSocket, requestAddress, requestPort);
                            break;
                        default:
                            if (result > 1) {
                                int previousAcceptedId = result - 1;
                                ClientSender.sendResponsePaxosPrepareProposalOK(previousAcceptedId, datagramSocket, requestAddress, requestPort);
                            } else {
                                // No action needed: ignored
                            }
                    }
                    break;

                case Identification.METHOD_ACCEPTPROPOSAL:
                    proposal = messageObj.getJSONArray(Identification.PRM_PROPOSALID);
                    proposalNumber = proposal.getInt(0);
                    proposerId = proposal.getInt(1);
                    int kpuId = messageObj.getInt(Identification.PRM_KPUID);
                    result = clientHandle.getGameHandler().getAcceptorController().acceptProposal(proposalNumber, proposerId, kpuId);
                    switch (result) {
                        case 1: // accept proposal id >= promised
                            ClientSender.sendInfoAcceptedProposal(kpuId,clientHandle.getOutputStream());
                            break;
                        case -1:
                            break;
                        default:
                            break;
                    }
                    break;
                case Identification.METHOD_VOTEWEREWOLF:
                case Identification.METHOD_VOTECIVILIAN:
                    int playerId = messageObj.getInt(Identification.PRM_PLAYERID);
                    clientHandle.getGameHandler().getProposerController().addKillVote(playerId);
                    ClientSender.sendResponseVoteOK(datagramSocket, requestAddress, requestPort);
                    break;
            }
        } catch (JSONException ex) {
            System.err.println(ex);
            Logger.getLogger(ClientListenerTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handling given response from server (TCP)
     * @param messageObj
     */
    public void handleResponse(JSONObject messageObj) {
        String description = "";
        try {
            String status = messageObj.getString(Identification.PRM_STATUS);
            switch (status) {
                case Identification.STATUS_OK:
                    if (messageObj.has(Identification.PRM_DESCRIPTION)) {
                        description = messageObj.getString(Identification.PRM_DESCRIPTION);
                    }
                    String lastMethod = ClientController.lastSentUdpMethod;
                    switch (lastMethod) {
                        case Identification.METHOD_PREPAREPROPOSAL:
                            System.out.println("CONSENSUS: Receving a proposal accept promise!");
                            int previousAccepted = -1;
                            if (messageObj.has(Identification.PRM_PREVACC)) {
                                previousAccepted = messageObj.getInt(Identification.PRM_PREVACC);
                                System.out.println("CONSENSUS: Receiving promise with a previously accepted number: "+ messageObj.getInt(Identification.PRM_PREVACC));
                            }
                            clientHandle.getGameHandler().getProposerController().receiveOK(previousAccepted);
                            break;
                        case Identification.METHOD_ACCEPTPROPOSAL:
                            //TODO: get player objects from json array

                            //handle.updatePlayers(players);
                            break;
                        case Identification.METHOD_VOTEWEREWOLF:
                        case Identification.METHOD_VOTECIVILIAN:
                            //TODO: notify views that the client cannot vote again
                            break;
                        default:
                            // Unknown
                            break;
                    }
                    break;
                case Identification.STATUS_FAIL:
                    if (messageObj.has(Identification.PRM_DESCRIPTION)) {
                        description = messageObj.getString(Identification.PRM_DESCRIPTION);
                        clientHandle.getGameHandler().showInformationDialog(description);
                    }
                    switch (ClientController.lastSentMethod) {
                        case Identification.METHOD_PREPAREPROPOSAL:
                            //TODO: get player objects from json array

                            //handle.updatePlayers(players);
                            break;
                        case Identification.METHOD_ACCEPTPROPOSAL:
                            //TODO: get player objects from json array

                            //handle.updatePlayers(players);
                            break;
                        case Identification.METHOD_VOTEWEREWOLF:
                        case Identification.METHOD_VOTECIVILIAN:
                            //TODO: get player objects from json array

                            //handle.updatePlayers(players);
                            break;
                        default:
                            // Unknown
                            break;
                    }
                    break;
                case Identification.STATUS_ERROR:
                    if (messageObj.has(Identification.PRM_DESCRIPTION)) {
                        description = messageObj.getString(Identification.PRM_DESCRIPTION);
                        // clientHandle.getGameHandler().showInformationDialog(description);
                    }
                    switch (ClientController.lastSentMethod) {
                        case Identification.METHOD_PREPAREPROPOSAL:
                            //TODO: get player objects from json array

                            //handle.updatePlayers(players);
                            break;
                        case Identification.METHOD_ACCEPTPROPOSAL:
                            //TODO: get player objects from json array

                            //handle.updatePlayers(players);
                            break;
                        case Identification.METHOD_VOTEWEREWOLF:
                        case Identification.METHOD_VOTECIVILIAN:
                            //TODO: get player objects from json array

                            //handle.updatePlayers(players);
                            break;
                        default:
                            // Unknown
                            break;
                    }
            }
        } catch (JSONException ex) {
            System.err.println(ex);
            Logger.getLogger(ClientListenerTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
        ClientController.setResponseHasArrived();
    }

    @Override
    public void run() {
        try {
            // Process only when active
            while (running) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                datagramSocket.receive(receivePacket);

                String receiveStr = new String(receivePacket.getData());

                try {
                    JSONObject messageObj = new JSONObject(receiveStr);
                    if (messageObj.has(Identification.PRM_METHOD)) {
                        handleRequest(messageObj, receivePacket.getAddress().getHostName(), receivePacket.getPort());
                    } else {
                        handleResponse(messageObj);
                    }
                } catch (JSONException ex) {
                    System.err.println(ex);
                    Logger.getLogger(ClientListenerTCP.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            datagramSocket.close();
        } catch (IOException ex) {
            System.err.println(ex);
            Logger.getLogger(ClientListenerUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * get the socket from our listener
     * @return the socket
     */
    public DatagramSocket getSocket() {
        return datagramSocket;
    }

    @Override
    public void notifyObservers(Object arg) {
        // Check if not null
        for (Observer obj : observerList) {
            obj.update(this, arg);
        }
        clearChanged();
    }

}
