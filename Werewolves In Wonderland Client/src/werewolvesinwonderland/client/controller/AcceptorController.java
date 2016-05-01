/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.client.controller;

/**
 *
 * @author Ahmad Naufal Farhan
 */
public class AcceptorController {
    
    private GameController gameHandle;
    private int acceptedProposalNumber = 0;
    private int acceptedProposerId = 0;
    
    public AcceptorController(GameController gameController) {
        gameHandle = gameController;
    }
    
    /**
     * 
     * @param proposalNumber
     * @param proposerId
     * @return 1 if the acceptor hasn't seen any proposal before
     * (proposerId + 1) if the acceptor has seen another proposal, and decided to accept this one
     * 0 if no change (equal)
     * -1 if rejected
     */
    public int promiseProposal(int proposalNumber, int proposerId) {
        if (proposalNumber > acceptedProposalNumber ||
                (proposalNumber == acceptedProposalNumber &&
                proposerId > acceptedProposerId)) {
            
            int previousAccepted = acceptedProposalNumber;
            acceptedProposalNumber = proposalNumber;
            acceptedProposerId = proposerId;
            if (previousAccepted == 0)
                return 1;
            else
                return previousAccepted + 1;
            
        } else if (proposalNumber < acceptedProposalNumber ||
                (proposalNumber == acceptedProposalNumber &&
                proposerId < acceptedProposerId)) {
            
            return -1;
        }
        // else: equal
        return 0;
    }
}
