/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.client.controller;

import java.util.Map;
import java.util.Map.Entry;
import werewolvesinwonderland.client.ClientSender;
import werewolvesinwonderland.protocol.model.Player;

/**
 *
 * @author Ahmad Naufal Farhan
 */
public class ProposerController {
    
    private GameController gameHandle;
    private int proposalNumber = 1;
    private int quorum = 0;
    private int totalPlayer;
    
    public ProposerController(GameController gameController) {
        gameHandle = gameController;
    }
    
    public void startRound() {
        
    }
    
    public void prepareProposal(int proposerId, Map<Integer, Player> acceptorList) {
        int playerId = proposerId;
        for (Entry<Integer, Player> e : acceptorList.entrySet()) {
            ClientSender.sendPaxosPrepareProposal(proposalNumber, playerId,
                    gameHandle.getClientHandle().getUdpSocket(),
                    e.getValue().getUdpAddress(), e.getValue().getUdpPort());
        }
        
        proposalNumber++;
    }
    
}
