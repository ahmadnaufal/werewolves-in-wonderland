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
    private int promisedProposalNumber = 0;
    private int promisedProposerId = -1;
    private int kpuId = -1;

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
    
    public void startRound() {
        kpuId = -1;
    }
    public int promiseProposal(int proposalNumber, int proposerId) {
        if (proposalNumber > promisedProposalNumber ||
                (proposalNumber == promisedProposalNumber &&
                proposerId > promisedProposerId)) {

            int previousAccepted = promisedProposalNumber;
            promisedProposalNumber = proposalNumber;
            promisedProposerId = proposerId;
            if (previousAccepted == 0)
                return 1;
            else
                return previousAccepted + 1;

        } else if (proposalNumber < promisedProposalNumber ||
                (proposalNumber == promisedProposalNumber &&
                proposerId < promisedProposerId)) {

            return -1;
        }
        // else: equal
        return 0;
    }

    public int acceptProposal(int proposalNumber, int proposerId, int kpuId) {
        if (this.kpuId == -1) {
            if (proposalNumber >= promisedProposalNumber && proposerId >= promisedProposerId) {
                this.kpuId = kpuId;

            } else if (proposalNumber < promisedProposalNumber ||
                    (proposalNumber == promisedProposalNumber &&
                    proposerId < promisedProposerId)) {

                return -1;
            }
        } else {
          return -1;
        }
        System.out.println("ACCEPT PROPOSAL: Proposal ID: (" + proposalNumber + ", " + proposerId + "), Kpu ID: "+kpuId);
        return 1;
    }

}
