package werewolvesinwonderland.client.controller;
import werewolvesinwonderland.client.model;

public class GameController {
  private Game mGame;
  private GameFrame frame;
  private ClientController clientHandle;
  private String username;
  private int kpuId;
  private ArrayList<String> werewolfFriends;

  public GameController() {
    mGame = new Game();
  }

  public void updatePlayers(ArrayList<Player> players) {
    HashMap<Integer,Player> playersMap = new HashMap<Integer,Player>();
    for (Player player : players) {
      playersMap.put(player.getPlayerId(),player);
    }
    mGame.setListPlayers(playersMap);
  }

  public void startGame(String time, String role) {
    mGame.setTime(time);
    mGame.getCurrentPlayer().setRole(role);
  }

  public void startGame(String time, String role, ArrayList<String> werewolfFriends) {
    mGame.setTime(time);
    mGame.getCurrentPlayer().setRole(role);
    this.werewolfFriends = werewolfFriends;
  }

  public void createPlayer(int id) {
    mGame.setCurrentPlayer(new Player(id,username,clientHandle.getClientHostName(),clientHandle.getListenPort()));
  }

  public void joinGame(String username) {
    ClientSender.requestJoinGame(username,clientHandle.getClientHostName(),clientHandle.getListenPort(),clientHandle.getOutputStream());
  }

  public void showDialog(String message) {
    //alert message
  }

  public void startVote() {
    if (mGame.getCurrentPlayer().getRole().equals(Identification.ROLE_WEREWOLF) && mGame.getTime().equals(Identification.TIME_NIGHT)) {
      //enable cells berisi civilians u/ divoting
    } else if (mGame.getTime().equals(Identification.TIME_DAY)) {
      //enable cells berisi semua player yg alive u/ divoting
    }
  }

  public void voteVictim(int playerId) {
    Player kpu = mGamemGame.getTime()getPlayer(kpuId);
    if (time.equals(Identification.TIME_DAY))
    ClientSender.voteKillWerewolf(playerId,clientHandle.getUdpSocket(),kpu.getUdpAddress(),kpu.getUdpPort());
    else
    ClientSender.voteKillCivilian(playerId,clientHandle.getUdpSocket(),kpu.getUdpAddress(),kpu.getUdpPort());
  }

  public void changePhase(String time, int days) {
    mGame.setTime(time);
    mGame.setDays(days);
    //update view
  }

  public void setKpu(int kpuId) {
    this.kpuId = kpuId;
  }

  public void gameOver(String winner) {
    //alert game over & winner
  }

  public void leaveGame() {

  }

  public void userExists() {

  }





}
