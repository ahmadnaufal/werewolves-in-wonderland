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
            if (werewolfFriends.contains(player.getUsername()))
                player.setRole(Identification.ROLE_WEREWOLF);
            playersMap.put(player.getPlayerId(), player);
        }
        mGame.setListPlayers(playersMap);
    }

    public void startGame(String time, String role) {
        mGame.setTime(time);
        mGame.getCurrentPlayer().setRole(role);
        frame.setPlayerInfo(mGame.getCurrentPlayer());
    }

    public void startGame(String time, String role, ArrayList<String> werewolfFriends) {
        mGame.setTime(time);
        mGame.getCurrentPlayer().setRole(role);
        this.werewolfFriends = werewolfFriends;
        frame.setPlayerInfo(mGame.getCurrentPlayer());
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

    public void startVote() {
        if (mGame.getCurrentPlayer().getPlayerId()==selectedKpu) proposerController.startVote();
        if (mGame.getCurrentPlayer().getRole().equals(Identification.ROLE_WEREWOLF) && mGame.getTime().equals(Identification.TIME_NIGHT)) {
            //enable cells berisi civilians u/ divoting
        } else if (mGame.getTime().equals(Identification.TIME_DAY)) {
            //enable cells berisi semua player yg alive u/ divoting
        }
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
    }

    public void setKpu(int kpuId) {
        this.selectedKpu = kpuId;
    }

    public void gameOver(String winner) {
        //alert game over & winner
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
        Set<Integer> playerIds = new HashSet<>(mGame.getListPlayers().keySet());
        int proposer1 = Collections.max(playerIds);
        playerIds.remove(proposer1);
        int proposer2 = Collections.max(playerIds);

        if (mGame.getCurrentPlayer().getPlayerId() == proposer1
          || mGame.getCurrentPlayer().getPlayerId() == proposer2) {
            System.out.println("CONSENSUS: I am a proposer!");
            Map<Integer, Player> acceptors = new HashMap<>(mGame.getListPlayers());
            acceptors.remove(proposer1);
            acceptors.remove(proposer2);
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
