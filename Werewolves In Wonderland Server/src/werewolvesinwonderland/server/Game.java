/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.server;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map.Entry;
import werewolvesinwonderland.protocol.Identification;

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
        this.mServerHandle = mServerHandle;
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

    private boolean userExists(String username) {
      for(Entry<Integer, Player> entry : players.entrySet()) {
        if (entry.getValue().getUsername().equals(username)) return true;
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

        ArrayList<Integer> list = new ArrayList<>();
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
        if (kpuProposalCount==aliveWerewolves.size()+aliveCivilians.size()) {
            kpuProposalCount = 0;
            countKpuProposals();
        }
    }

    private void countKpuProposals() {
      Integer max = Collections.max(kpuProposals.values());
      int maxId = -1;

      for(Entry<Integer, Integer> entry : kpuProposals.entrySet()) {
        Integer value = entry.getValue();

          if(max == value) {
            maxId = entry.getKey();
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
        changePhase();
        checkEndGame();
    }

    private void changePhase() {
        if (time.equals(Identification.TIME_DAY)) {
            time = Identification.TIME_NIGHT;
        } else {
            time = Identification.TIME_DAY;
            days++;
            selectedKpu = -1;
        }
        mServerHandle.sendChangePhase();
    }

    public int getDays() {
        return days;
    }

    public String getTime() {
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
