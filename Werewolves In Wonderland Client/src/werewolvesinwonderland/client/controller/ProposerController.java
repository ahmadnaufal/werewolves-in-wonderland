/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.client.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import werewolvesinwonderland.client.ClientSender;
import werewolvesinwonderland.protocol.Identification;
import werewolvesinwonderland.protocol.model.Player;

/**
 *
 * @author Ahmad Naufal Farhan
 */
public class ProposerController {

    private GameController gameHandle;
    private int playerId;
    private int proposalNumber = 1;
    private int quorum = 0;
    private int kpuId;
    private Map<Integer, Player> acceptorList;
    private Map<Integer, Integer> killVotes = new HashMap<>();
    private int voteCount = 0;
    private int aliveWerewolvesCount;
    private int alivePlayersCount;

    public ProposerController(GameController gameController) {
        gameHandle = gameController;
    }
    
    public void setPlayerId() {
        playerId = gameHandle.getGame().getCurrentPlayer().getPlayerId();
    }

    public void startRound(Map<Integer, Player> acceptorList) {
      this.acceptorList = acceptorList;
      quorum = 0;
      kpuId = playerId;
      prepareProposal();
    }

    public void prepareProposal() {
        for (Entry<Integer, Player> e : acceptorList.entrySet()) {
            System.out.println("CONSENSUS PROPOSAL: To " + e.getValue().getPlayerId() + ", Value: (" + proposalNumber + ", " + playerId + ")");
            ClientSender.sendPaxosPrepareProposal(proposalNumber, playerId,
                    gameHandle.getClientHandle().getUdpSocket(),
                    e.getValue().getUdpAddress(), e.getValue().getUdpPort());
        }
        proposalNumber++;
    }

    public void receiveOK(int previousAccepted) {
        quorum++;
        if (previousAccepted != -1 && previousAccepted > kpuId) {
            kpuId = previousAccepted;
        }
        if (quorum >= acceptorList.size() / 2) {
            requestAcceptProposal();
        }
    }

    public void requestAcceptProposal() {
        for (Entry<Integer, Player> e : acceptorList.entrySet()) {
            ClientSender.sendPaxosAcceptProposal(proposalNumber, playerId, kpuId,
                    gameHandle.getClientHandle().getUdpSocket(),
                    e.getValue().getUdpAddress(), e.getValue().getUdpPort());
        }
    }

    public void addKillVote(int id) {
        if (killVotes.containsKey(id)) {
            killVotes.put(id, killVotes.get(id) + 1);
        } else {
            killVotes.put(id, 1);
        }
        voteCount++;
        if (gameHandle.getGame().getTime().equals(Identification.TIME_DAY) && voteCount == alivePlayersCount
                || gameHandle.getGame().getTime().equals(Identification.TIME_NIGHT) && voteCount == aliveWerewolvesCount) {
            voteCount = 0;
            killVotes.clear();
            countKillVotes();
        }
    }

    private void countKillVotes() {
        Integer max = Collections.max(killVotes.values());
        Integer maxId = null;
        boolean tie = true;

        for (Entry<Integer, Integer> entry : killVotes.entrySet()) {
            Integer value = entry.getValue();

            if (max == value) {
                if (maxId == null) {
                    maxId = entry.getKey();
                } else {
                    tie = true;
                    break;
                }
            }
        }
        if (!tie) {
            sendInfoKilled(maxId);
        } else {
            sendInfoKilled(-1);
        }
    }

    public void startVote() {
        if (gameHandle.getGame().getTime().equals(Identification.TIME_DAY)) {
            alivePlayersCount = gameHandle.getGame().getAlivePlayers().size();
        } else {
            countAliveWerewolves();
        }
    }

    private void countAliveWerewolves() {
        aliveWerewolvesCount = (acceptorList.size() + 2) / 3;
        for (Entry<Integer, Player> e : gameHandle.getGame().getListPlayers().entrySet()) {
            if (e.getValue().getRole().equals(Identification.ROLE_WEREWOLF)) {
                aliveWerewolvesCount--;
            }
        }
    }

    private void sendInfoKilled(int playerKilled) {
        ArrayList<ArrayList<Integer>> killVotesArrayList = new ArrayList<ArrayList<Integer>>();
        for (Entry<Integer, Integer> entry : killVotes.entrySet()) {
            ArrayList<Integer> temp = new ArrayList<Integer>();
            temp.add(entry.getKey());
            temp.add(entry.getValue());
            killVotesArrayList.add(temp);
        }
        if (gameHandle.getGame().getTime().equals(Identification.TIME_DAY)) {
            if (playerKilled == -1) {
                ClientSender.sendInfoWerewolfNotKilled(killVotesArrayList, gameHandle.getClientHandle().getOutputStream());
            } else {
                ClientSender.sendInfoWerewolfKilled(playerKilled, killVotesArrayList, gameHandle.getClientHandle().getOutputStream());
            }
        } else if (playerKilled == -1) {
            ClientSender.sendInfoCivilianNotKilled(killVotesArrayList, gameHandle.getClientHandle().getOutputStream());
        } else {
            ClientSender.sendInfoCivilianKilled(playerKilled, killVotesArrayList, gameHandle.getClientHandle().getOutputStream());
        }
    }




}
