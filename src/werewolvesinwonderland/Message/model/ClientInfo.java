/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.Message.model;

/**
 *
 * @author Tifani
 */
public class ClientInfo {
    private int playerId;
    private int isAlive;
    private String address;
    private int port;
    private String username;
    private String role;

    public ClientInfo(int playerId, int isAlive, String address, int port, String username, String role) {
        this.playerId = playerId;
        this.isAlive = isAlive;
        this.address = address;
        this.port = port;
        this.username = username;
        this.role = role;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getIsAlive() {
        return isAlive;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
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

    public void setIsAlive(int isAlive) {
        this.isAlive = isAlive;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    
}
