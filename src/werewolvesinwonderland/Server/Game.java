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
    private HashMap<Integer,Player> werewolves = new HashMap<>();
    private HashMap<Integer,Player> aliveWerewolves = new HashMap<>();
    private HashMap<Integer,Player> aliveCivilians = new HashMap<>();
    private String time = Identification.TIME_DAY;
    private boolean started = false;
    private int days = 0;
    private int readyCount = 0;
    private HashMap<Integer,Integer> kpuProposals = new HashMap<>();
    private int selectedKpu = -1;
    private int kpuProposalCount = 0;
    private ServerController mServerHandle;

    public Game(ServerController mServerHandle) {
        this.serverHandle = mServerHandle;
    }

    public void restartGame() {
        time = Identification.TIME_DAY;
        started = false;
        days = 0;
        readyCount = 0;
        selectedKpu = -1;
        kpuProposalCount = 0;
        players.clear();
        werewolves.clear();
        aliveWerewolves.clear();
        aliveCivilians.clear();
    }

    private void addPlayer(Player player) {
        players.put(player.getId(),player);
    }

    public void removePlayer(int id) {
        if ((players.get(id).getRole()).equals(Identification.ROLE_WEREWOLF)) {
            aliveWerewolves.remove(id);
        } else {
            aliveCivilians.remove(id);
        }
        players.remove(id);
        checkEndGame();
    }

    private void userExists(String username) {
      for(Entry<Integer, Integer> entry : players.entrySet()) {
        if (entry.getUsername().equals(username)) return true;
      }
      return false;
    }

    public int newPlayer(String username) {
        if (started) {
          return -2;
        } else if (userExists(username)) {
          return -1;
        } else {
            int id = players.size();
            Player player = new Player(id, username);
            addPlayer(player);
            return id;
        }
    }

    public void increaseReady() {
        readyCount++;
        if (readyCount==players.size()) {
            startGame();
        }
    }

    private void startGame() {
      assignRoles();
      started = true;
      mServerHandle.sendStartGame();
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
        players.get(id).setRole(Identification.ROLE_WEREWOLF);
        werewolves.put(id,players.get(id));
        aliveWerewolves.put(id,players.get(id));
    }

    private void setCivilian(int id) {
        players.get(id).setRole(Identification.ROLE_CIVILIAN);
        aliveCivilians.put(id,players.get(id));
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
      kpuProposalCount++;
      if (proposalCount==aliveWerewolves.size()+aliveCivilians.size())
        kpuProposalCount = 0;
        countKpuProposals();
    }

    private void countKpuProposals() {
      Integer max = Collections.max(kpuProposals.values());
      int maxId;

      for(Entry<Integer, Integer> entry : kpuProposals.entrySet()) {
        Integer value = entry.getValue();

          if(max == value) {
            maxId = entry.getId();
          }
      }
      selectedKpu = maxId;
    }

    public int getSelectedKpu() {
      return selectedKpu;
    }


    public void killPlayer(int id) {
        Player player = players.get(id);
        player.setAlive(false);
        if ((player.getRole()).equals(Identification.ROLE_WEREWOLF)) {
            aliveWerewolves.remove(id);
        } else {
            aliveCivilians.remove(id);
        }
        changetime();
        checkEndGame();
    }

    private void changetime() {
        if (time.equals(Identification.TIME_DAY)) {
            time = Identification.TIME_NIGHT;
        } else {
            time = Identification.TIME_DAY;
            days++;
            selectedKpu = -1;
        }
        mServerHandle.sendChangetime();
    }

    public int getDays() {
      return days;
    }

    public String gettime() {
      return time;
    }

    public HashMap<Integer,Player> getPlayers() {
      return players;
    }
    public HashMap<Integer,Player> getWerewolves() {
      return werewolves;
    }

    public HashMap<Integer,Player> getAliveWerewolves() {
      return aliveWerewolves;
    }

    public HashMap<Integer,Player> getAlivePlayers() {
      HashMap<Integer,Player> alive = new HashMap<>(aliveWerewolves);
      alive.putAll(aliveCivilians);
      return alive;
    }
}
