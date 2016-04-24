/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.Server;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author ASUS X202E
 */
public class Game {
  private HashMap<Integer,Player> players = new HashMap<>();
  private ArrayList<Player> werewolves = new ArrayList<>();
  private ArrayList<Player> aliveWerewolves = new ArrayList<>();
  private ArrayList<Player> aliveCivilians = new ArrayList<>();
  // private HashMap<Integer,Integer> votes = new HashMap<>();
  private String time = "night";
  private boolean started = false;
  private int days = 0;
  private int readyCount = 0;
  // private int voteCount = 0;
  // private boolean voted = false;

  public Game() {

  }
  public void restartGame() {
    time = "night";
    started = false;
    days = 0;
    readyCount = 0;
    // voteCount = 0;
    players.clear();
    werewolves.clear();
    aliveWerewolves.clear();
    aliveCivilians.clear();
  }
  public void addPlayer(Player player) {
    players.put(player.getId(),player);
  }

  public void removePlayer(int player_id) {
    players.remove(player_id);
  }

  public Player newPlayer(String username) {
    if (!started) {
      int id = players.size();
      Player player = new Player(id,username);
      addPlayer(player);
      return player;
    } else return null;
  }

  public void increaseReady() {
    readyCount++;
    if (readyCount==players.size()) {
      if (!started) {
        assignRoles();
        started = true;
        //kirim info start game ke semua
      } else {
        changePhase();
        //kirim info change phase ke semua
      }
      readyCount=0;
    }
  }

  public void assignRoles() {
    int werewolfCount = players.size()/3;

    ArrayList<Integer> list = new ArrayList<Integer>();
    for (Integer id : players.keySet()) {
      list.add(id);
    }
    Collections.shuffle(list);
    for (int i=0;i<werewolfCount;i++) {
      setWerewolf(list.get(i));
    }
    for (int i=werewolfCount;i<list.size();i++) {
      setCivilian(list.get(i));
    }
  }

  public void setWerewolf(int id) {
    players.get(id).setRole("werewolf");
    werewolves.add(players.get(id));
    aliveWerewolves.add(players.get(id));
  }

  public void setCivilian(int id) {
    players.get(id).setRole("civilian");
    aliveCivilians.add(players.get(id));
  }


  public void checkEndGame() {
    if (aliveWerewolves.isEmpty()) {
      //kirim pesan ke semua bahwa civilian menang
      restartGame();
    } else if (aliveWerewolves.size()>aliveCivilians.size()) {
      //kirim pesan ke semua bahwa civilian menang
      restartGame();
    }
  }

  // public void addVote(int id) {
  //   votes.put(id,votes.get(id)+1);
  //   voteCount++;
  //   if ((time.equals("day") && voteCount == aliveWerewolves.size()+aliveCivilians.size())
  //   || (time.equals("night") && voteCount == aliveWerewolves.size())) {
  //     voted = true;
  //     voteCount = 0;
  //     countVotes();
  //   }
  // }
  //
  // public void countVotes() {
  //   Integer max = Collections.max(votes.values());
  //   Integer maxId;
  //   boolean tie = false;
  //
  //   for(Entry<Integer, Integer> entry : votes.entrySet()) {
  //     Integer value = entry.getValue();
  //
  //       if(max == value) {
  //           if (maxId==null) {
  //             maxId = entry.getId();
  //           } else {
  //             tie = true;
  //             break;
  //           }
  //       }
  //   }
  //   if (tie) tieVote();
  //   else {
  //     killPlayer(players.get(maxId));
  //   }
  // }
  //
  //
  // public void tieVote() {
  //   if (time.equals("night")) {
  //     if (voted) {
  //       changePhase();
  //       //kirim pesan ke semua bahwa tidak ada yg dibunuh
  //     } else {
  //       //kirim pesan ke werewolves u/ vote ulang
  //     }
  //   } else {
  //     //kirim pesan ke semua untuk vote ulang
  //   }
  // }

  public void killPlayer(int id) {
    Player player = players.get(id);
    player.setAlive(false);
    if ((player.getRole()).equals("werewolf")) {
      aliveWerewolves.remove(player);
    } else {
      aliveCivilians.remove(player);
    }
    //kirim info siapa yg dibunuh
    changePhase();
    checkEndGame();
  }

  public void changePhase() {
    if (time.equals("day")) {
      time = "night";
    } else {
      time = "day";
      days++;
    }
    //kirim pesan change phase
  }


}
