/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import werewolvesinwonderland.protocol.Identification;
import werewolvesinwonderland.protocol.model.Player;

/**
 *
 * @author Ahmad Naufal Farhan
 */
public class ServerController {

    public ArrayList<ClientHandler> clientsList = new ArrayList<>();
    public HashMap<Player,ClientHandler> playerClientMap = new HashMap<>();
    public ServerSocket mServerSocket;
    public int mPort;
    private Game mGame;

    /**
     * Constructor for controllers
     * @param port the port for socket connections
     */
    public ServerController(int port) {
        mPort = port;
        mGame = new Game(this);
    }

    /**
     * Initialize socket connection of the server
     * to receive connections from its client
     */
    public void initializeServer() {
        try {
            mServerSocket = new ServerSocket(mPort, 0, InetAddress.getLocalHost());

            while (true) {
                Socket socket = mServerSocket.accept();
                System.out.println(socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
                ClientHandler temp = new ClientHandler(socket, this);
                clientsList.add(temp);
            }

        } catch (IOException ex) {
            System.out.println(ex);
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Get the game object
     * @return
     */
    public Game getGame() {
        return mGame;
    }

    public void mapPlayerClient(ClientHandler client) {
      playerClientMap.put(mGame.getPlayer(client.getPlayerId()),client);
    }

    public void sendStartGame() {
      String time = Identification.TIME_DAY;
      ArrayList<String> werewolves = mGame.getWerewolvesUsernames();
      ArrayList<String> friends;
      for (Player player : mGame.getPlayersList()) {
        if (player.getRole().equals(Identification.ROLE_WEREWOLF)) {
          friends = new ArrayList<String>(werewolves);
          friends.remove(player.getUsername());
        } else {
          friends = new ArrayList<String>();
        }
        ServerSender.sendRequestStartGame(time,player.getRole(),friends,playerClientMap.get(player).getOutputStream());
      }
    }

    public void sendChangePhase() {
      String time = mGame.getTime();
      int days = mGame.getDays();
      for (Player player : mGame.getPlayersList()) {
        ServerSender.sendRequestChangePhase(time,days,playerClientMap.get(player).getOutputStream());
      }
    }

    public void sendGameOver(String winner) {
      for (Player player : mGame.getPlayersList()) {
        ServerSender.sendRequestGameOver(winner,playerClientMap.get(player).getOutputStream());
      }
    }

    public void sendVoteNight() {
      for (Player player : mGame.getAliveWerewolves()) {
        ServerSender.sendRequestVote(Identification.TIME_NIGHT,playerClientMap.get(player).getOutputStream());
      }
    }

    public void sendVoteDay() {
      for (Player player : mGame.getAlivePlayers()) {
        ServerSender.sendRequestVote(Identification.TIME_DAY,playerClientMap.get(player).getOutputStream());
      }
    }

    public void sendKpuSelected(int kpu) {
      for (Player player : mGame.getPlayersList()) {
        ServerSender.sendRequestKpuSelected(kpu,playerClientMap.get(player).getOutputStream());
      }
    }
}
