package werewolvesinwonderland.client.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import werewolvesinwonderland.client.ClientController;
import werewolvesinwonderland.client.ClientSender;
import werewolvesinwonderland.client.model.Game;
import werewolvesinwonderland.client.view.GameFrame;
import werewolvesinwonderland.protocol.Identification;
import werewolvesinwonderland.protocol.model.Player;

public class GameController {

    private Game mGame;
    private GameFrame frame;
    private ClientController clientHandle;
    private String username;
    private int selectedKpu;
    private ArrayList<String> werewolfFriends = new ArrayList<>();

    public boolean voteKpu = false;
    public boolean voteNow = false;

    private AcceptorController acceptorController;
    private ProposerController proposerController;

    public GameController(GameFrame frame) {
        this.mGame = new Game();
        this.frame = frame;
        acceptorController = new AcceptorController(this);
        proposerController = new ProposerController(this);
    }

    public ClientController getClientHandle() {
        return clientHandle;
    }

    public GameFrame getGameFrame() {
        return frame;
    }

    public void setClientHandle(ClientController clientHandle) {
        this.clientHandle = clientHandle;
    }

    public void updatePlayers(ArrayList<Player> players) {
        HashMap<Integer, Player> playersMap = new HashMap<>();
        for (Player player : players) {
            if (player.getPlayerId()==mGame.getCurrentPlayer().getPlayerId()) mGame.getCurrentPlayer().setAlive(player.isAlive());
            if (werewolfFriends.contains(player.getUsername()))
                player.setRole(Identification.ROLE_WEREWOLF);
            playersMap.put(player.getPlayerId(), player);
        }
        mGame.setListPlayers(playersMap);
        frame.setPlayerInfo(mGame.getCurrentPlayer());
        frame.updateBoard(voteNow);
        frame.changeNumberOfPlayersInfo(mGame.getAlivePlayers().size(),mGame.getDeadPlayers().size());
        if (voteNow && mGame.getCurrentPlayer().getPlayerId()==selectedKpu) proposerController.startVote();
    }

    public void startGame(String time, String role) {
        mGame.setTime(time);
        mGame.getCurrentPlayer().setRole(role);
        frame.setPlayerInfo(mGame.getCurrentPlayer());
        frame.changePhaseInfo(Identification.TIME_DAY, 0, mGame.getListPlayers().size(), 0);
    }

    public void startGame(String time, String role, ArrayList<String> werewolfFriends) {
        mGame.setTime(time);
        mGame.getCurrentPlayer().setRole(role);
        this.werewolfFriends = werewolfFriends;
        frame.setPlayerInfo(mGame.getCurrentPlayer());
        frame.changePhaseInfo(Identification.TIME_DAY, 0, mGame.getListPlayers().size(), 0);
    }

    public void createPlayer(int id) {
        mGame.setCurrentPlayer(new Player(id, username, clientHandle.getClientHostName(), clientHandle.getListenPort()));
        proposerController.setPlayerId();
        frame.joinGameSuccess(mGame.getCurrentPlayer());
    }

    public int joinGame(String username, String serverAddress, int serverPort, int clientPort) {
        clientHandle = new ClientController(serverAddress, serverPort, clientPort, this);
        int ret = clientHandle.initClientConnection();
        if (ret == 1) {
            if (ClientSender.requestJoinGame(username,
                clientHandle.getClientHostName(),
                clientHandle.getListenPort(),
                clientHandle.getOutputStream()) != 1) {
                clientHandle.setResponseHasArrived();
            } else {
                this.username = username;
            }
        }
        return ret;
    }

    public void readyUp() {
        ClientSender.requestReadyUp(clientHandle.getOutputStream());
    }

    public void startVote(String phase) {
        /*if (mGame.getCurrentPlayer().getRole().equals(Identification.ROLE_WEREWOLF) && phase.equals(Identification.TIME_NIGHT)) {
            //TODO: enable semua cells civilian u/ divoting
        } else if (phase.equals(Identification.TIME_DAY)) {
            //TODO: enable cells berisi semua player yg alive u/ divoting
        }*/
        voteNow = true;
        frame.updateBoard(voteNow);
    }

    public void voteVictim(int playerId) {
        Player kpu = mGame.getPlayer(selectedKpu);
        String time = mGame.getTime();
        if (time.equals(Identification.TIME_DAY)) {
            ClientSender.sendVoteKillWerewolf(playerId, clientHandle.getUdpSocket(), kpu.getUdpAddress(), kpu.getUdpPort());
        } else {
            ClientSender.sendVoteKillCivilian(playerId, clientHandle.getUdpSocket(), kpu.getUdpAddress(), kpu.getUdpPort());
        }
    }

    public void changePhase(String time, int days) {
        mGame.setTime(time);
        mGame.setDays(days);
        //update view
        frame.changePhaseInfo(time, days);
        if (time.equals(Identification.TIME_NIGHT) && mGame.getCurrentPlayer().getPlayerId()==selectedKpu) voteNow=true;
    }

    public void setKpu(int kpuId) {
        this.selectedKpu = kpuId;
    }

    public void gameOver(String winner) {
        //alert game over & winner
        frame.showInformationMessage("Game over! The winner is " + winner);
    }

    public void leaveGame() {
        //TODO: Masih bingung
        ClientSender.requestLeaveGame(clientHandle.getOutputStream());
    }

    public void showInformationDialog(String message) {
        frame.showInformationMessage(message);
    }

    public void showErrorDialog(String message) {
        //alert message
        frame.showErrorMessage(message);
    }

    public void startPaxos() {
        System.out.println("CONSENSUS: Paxos is started.");
        Set<Integer> playerIds = new HashSet<>();
        for (Integer id : mGame.getListPlayers().keySet()) {
          playerIds.add(id);
        }
        int proposer1 = Collections.max(playerIds);
        playerIds.remove(proposer1);
        int proposer2 = Collections.max(playerIds);

        if (mGame.getCurrentPlayer().getPlayerId() == proposer1
          || mGame.getCurrentPlayer().getPlayerId() == proposer2) {
            System.out.println("CONSENSUS: I am a proposer!");
            Map<Integer, Player> acceptors = new HashMap<>();
            for (Map.Entry<Integer,Player> entry : mGame.getListPlayers().entrySet()) {
              if (entry.getValue().getPlayerId()!=proposer1 && entry.getValue().getPlayerId()!=proposer2)
                acceptors.put(entry.getValue().getPlayerId(),entry.getValue());
            }
            getProposerController().startRound(acceptors);
        } else {
            // This is an acceptor
            System.out.println("CONSENSUS: I am an acceptor!");
        }
    }

    /**
     * @return the acceptorController
     */
    public AcceptorController getAcceptorController() {
        return acceptorController;
    }

    /**
     * @param acceptorController the acceptorController to set
     */
    public void setAcceptorController(AcceptorController acceptorController) {
        this.acceptorController = acceptorController;
    }

    /**
     * @return the proposerController
     */
    public ProposerController getProposerController() {
        return proposerController;
    }

    /**
     * @param proposerController the proposerController to set
     */
    public void setProposerController(ProposerController proposerController) {
        this.proposerController = proposerController;
    }

    public Game getGame() {
      return mGame;
    }

}
