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
    private String time;
    private boolean started;
    private int days;
    private int readyCount;
    private HashMap<Integer,Integer> kpuProposals = new HashMap<>();
    private int selectedKpu = -1;
    private int kpuProposalCount = 0;
    // private boolean voted = false;

    public Game() {
        time = "night";
        started = false;
        days = 0;
        readyCount = 0;
    }

    public void restartGame() {
        time = "night";
        started = false;
        days = 0;
        readyCount = 0;
        players.clear();
        werewolves.clear();
        aliveWerewolves.clear();
        aliveCivilians.clear();
    }

    private void addPlayer(Player player) {
        players.put(player.getId(),player);
    }

    private void removePlayer(int player_id) {
        players.remove(player_id);
    }

    public int newPlayer(String username) {
        if (!started) {
            int id = players.size();
            Player player = new Player(id, username);
            addPlayer(player);
            return id;
        } else return -1;
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

    private void assignRoles() {
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

    private void setWerewolf(int id) {
        players.get(id).setRole("werewolf");
        werewolves.add(players.get(id));
        aliveWerewolves.add(players.get(id));
    }

    private void setCivilian(int id) {
        players.get(id).setRole("civilian");
        aliveCivilians.add(players.get(id));
    }


    private void checkEndGame() {
        if (aliveWerewolves.isEmpty()) {
            //kirim pesan ke semua bahwa civilian menang
            restartGame();
        } else if (aliveWerewolves.size()>aliveCivilians.size()) {
            //kirim pesan ke semua bahwa civilian menang
            restartGame();
        }
    }

    public void addKpuProposal(int id) {
      kpuProposals.put(id,kpuProposals.get(id)+1);
      proposalCount++;
      if (proposalCount==aliveWerewolves.size()+aliveCivilians.size())
        countKpuProposals();
    }

    private void countKpuProposals() {
      Integer max = Collections.max(votes.values());
      Integer maxId;

      for(Entry<Integer, Integer> entry : votes.entrySet()) {
        Integer value = entry.getValue();

          if(max == value) {
            maxId = entry.getId();
          }
      }
      selectedKpu = maxId;
    }

    public void getSelectedKpu() {
      return selectedKpu;
    }


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

    private void changePhase() {
        if (time.equals("day")) {
            time = "night";
        } else {
            time = "day";
            days++;
        }
        //kirim pesan change phase
    }


}
