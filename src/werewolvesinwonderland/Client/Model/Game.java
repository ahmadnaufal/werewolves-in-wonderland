/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.Client.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

/**
 *
 * @author Ahmad Naufal Farhan
 */
public class Game {
    private String time;
    private int days;
    
    private boolean isStarted = false;
    private Map<Integer, Player> listPlayers = new HashMap<>();
    
    public Game() {
        time = "night";
        days = 0;
    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return the days
     */
    public int getDays() {
        return days;
    }

    /**
     * @param days the days to set
     */
    public void setDays(int days) {
        this.days = days;
    }

    /**
     * @return the isStarted
     */
    public boolean isIsStarted() {
        return isStarted;
    }

    /**
     * @param isStarted the isStarted to set
     */
    public void setIsStarted(boolean isStarted) {
        this.isStarted = isStarted;
    }

    /**
     * @return the listPlayers
     */
    public Map<Integer, Player> getListPlayers() {
        return listPlayers;
    }

    /**
     * @param listPlayers the listPlayers to set
     */
    public void setListPlayers(Map<Integer, Player> listPlayers) {
        this.listPlayers = listPlayers;
    }
    
    /**
     * Get all the dead players from the player list
     * @return the list of dead players
     */
    public Map<Integer,Player> getDeadPlayers() {
        Map<Integer, Player> listDeadPlayers = new HashMap<>();
        getListPlayers().entrySet().stream().filter((entry) -> (!entry.getValue().isAlive()))
                .forEach((Entry<Integer, Player> entry) -> {
            listDeadPlayers.put(entry.getKey(), entry.getValue());
        });
        
        return listDeadPlayers;
    }
    
    /**
     * Get all the alive players from the player list
     * @return the list of alive players
     */
    public Map<Integer,Player> getAlivePlayers() {
        Map<Integer, Player> listAlivePlayers = new HashMap<>();
        getListPlayers().entrySet().stream().filter((entry) -> (entry.getValue().isAlive()))
                .forEach((Entry<Integer, Player> entry) -> {
            listAlivePlayers.put(entry.getKey(), entry.getValue());
        });
        
        return listAlivePlayers;
    }
    
}
