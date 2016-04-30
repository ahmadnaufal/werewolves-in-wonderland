/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.protocol.model;

/**
 *
 * @author Tifani
 */
public class Player {
    private int playerId;
    private boolean alive;
    private String udpAddress;
    private int udpPort;
    private String username;
    private String role;

    public Player(int playerId, String username, String udpAddress, int udpPort) {
      this.playerId = playerId;
      this.username = username;
      this.udpAddress = udpAddress;
      this.udpPort = udpPort;
      this.alive = true;
    }

    public Player(int playerId, boolean alive, String udpAddress, int udpPort, String username, String role) {
        this.playerId = playerId;
        this.alive = alive;
        this.udpAddress = udpAddress;
        this.udpPort = udpPort;
        this.username = username;
        this.role = role;
    }

    public int getPlayerId() {
        return playerId;
    }

    public boolean isAlive() {
        return alive;
    }

    public String getUdpAddress() {
        return udpAddress;
    }

    public int getUdpPort() {
        return udpPort;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setudpAddress(String udpAddress) {
        this.udpAddress = udpAddress;
    }

    public void setudpPort(int udpPort) {
        this.udpPort = udpPort;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
