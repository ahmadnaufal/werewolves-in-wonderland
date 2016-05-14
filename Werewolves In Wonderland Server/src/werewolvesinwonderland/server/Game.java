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
import werewolvesinwonderland.protocol.model.Player;

/**
 *
 * @author ASUS X202E
 */
public class Game {

    private HashMap<Integer, Player> players = new HashMap<>();
    private ArrayList<Player> aliveWerewolves = new ArrayList<>();
    private ArrayList<Player> aliveCivilians = new ArrayList<>();
    private String time = Identification.TIME_DAY;
    private boolean started = false;
    private int days = 0;
    private int readyCount = 0;
    private HashMap<Integer, Integer> kpuProposals = new HashMap<>();
    private boolean dayVoted = false;
    private int selectedKpu = -1;
    private ServerController mServerHandle;
    private int kpuProposalCount;

    public Game(ServerController mServerHandle) {
        this.mServerHandle = mServerHandle;
    }

    public void restartGame() {
        time = Identification.TIME_DAY;
        started = false;
        days = 0;
        readyCount = 0;
        kpuProposalCount = 0;
        dayVoted = false;
        selectedKpu = -1;
        players.clear();
        aliveWerewolves.clear();
        aliveCivilians.clear();
    }

    public void removePlayer(int id) {
        Player player = getPlayer(id);
        if (player.getRole() != null) {
            if ((player.getRole()).equals(Identification.ROLE_WEREWOLF)) {
                aliveWerewolves.remove(player);
            } else {
                aliveCivilians.remove(player);
            }
        }
        players.remove(id);
        if (checkWinner() != null) {
            mServerHandle.sendGameOver(checkWinner());
            restartGame();
        }
    }

    private boolean userExists(String username) {
        for (Entry<Integer, Player> entry : players.entrySet()) {
            if (entry.getValue().getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public int addPlayer(String username, String udpAddress, int udpPort) {
        if (started) {
            return -2;
        } else if (userExists(username)) {
            return -1;
        } else {
            int id = players.size();
            Player player = new Player(id, username, udpAddress, udpPort);
            players.put(id, player);
            return id;
        }
    }

    public void increaseReady() {
        readyCount++;
        if (readyCount == players.size() && players.size() >= 6) {
            startGame();
        }
    }

    private void startGame() {
        assignRoles();
        started = true;
        mServerHandle.sendStartGame();
    }

    private void assignRoles() {
        int werewolfCount = players.size() / 3;

        ArrayList<Integer> list = new ArrayList<>();
        for (Integer id : players.keySet()) {
            list.add(id);
        }
        Collections.shuffle(list);
        for (int i = 0; i < werewolfCount; i++) {
            setWerewolf(list.get(i));
        }
        for (int i = werewolfCount; i < list.size(); i++) {
            setCivilian(list.get(i));
        }
    }

    private void setWerewolf(int id) {
        Player player = getPlayer(id);
        player.setRole(Identification.ROLE_WEREWOLF);
        aliveWerewolves.add(player);
    }

    private void setCivilian(int id) {
        Player player = getPlayer(id);
        player.setRole(Identification.ROLE_CIVILIAN);
        aliveCivilians.add(player);
    }

    private String checkWinner() {
        if (aliveWerewolves.isEmpty()) {
            return Identification.ROLE_CIVILIAN;
        } else if (aliveWerewolves.size() == aliveCivilians.size()) {
            return Identification.ROLE_WEREWOLF;
        } else {
            return null;
        }
    }

    public void addKpuProposal(int id) {
        if (kpuProposals.containsKey(id)) {
            kpuProposals.put(id, kpuProposals.get(id) + 1);
        } else {
            kpuProposals.put(id, 1);
        }
        if (kpuProposals.get(id)>=players.size()-2) {
            selectedKpu = id;
            mServerHandle.sendKpuSelected(id);
            mServerHandle.sendVoteDay();
            kpuProposals.clear();
        }
    }

    public int getSelectedKpu() {
        return selectedKpu;
    }

    public void killPlayer(int id) {
        Player player = getPlayer(id);
        player.setAlive(false);
        if ((player.getRole()).equals(Identification.ROLE_WEREWOLF)) {
            aliveWerewolves.remove(player);
        } else {
            aliveCivilians.remove(player);
        }

        if (checkWinner() != null) {
            mServerHandle.sendGameOver(checkWinner());
            restartGame();
        } else {
            changePhase();
        }
    }

    private void changePhase() {
        if (time.equals(Identification.TIME_DAY)) {
            time = Identification.TIME_NIGHT;
        } else {
            time = Identification.TIME_DAY;
            days++;
        }
        mServerHandle.sendChangePhase();
    }

    public int getDays() {
        return days;
    }

    public String getTime() {
        return time;
    }

    public ArrayList<String> getWerewolvesUsernames() {
        ArrayList<String> usernames = new ArrayList<>();
        for (Player werewolf : aliveWerewolves) {
            usernames.add(werewolf.getUsername());
        }
        return usernames;
    }

    public ArrayList<Player> getAliveWerewolves() {
        return aliveWerewolves;
    }

    public ArrayList<Player> getAlivePlayers() {
        ArrayList<Player> alivePlayers = new ArrayList<>(aliveCivilians);
        alivePlayers.addAll(aliveWerewolves);
        return alivePlayers;
    }

    public ArrayList<Player> getPlayersList() {
        ArrayList<Player> playersList = new ArrayList<>(players.values());
        return playersList;
    }

    public void tieVote() {
        if (time.equals(Identification.TIME_DAY)) {
            if (dayVoted) {
                dayVoted = false;
                changePhase();
            } else {
                dayVoted = true;
                mServerHandle.sendVoteDay();
            }
        } else {
            mServerHandle.sendVoteNight();
        }
    }

    public Player getPlayer(int id) {
        return players.get(id);
    }

}
