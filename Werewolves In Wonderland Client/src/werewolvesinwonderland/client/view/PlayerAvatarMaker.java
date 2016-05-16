/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.client.view;

import java.util.HashMap;
import java.util.Map;
import werewolvesinwonderland.protocol.model.Player;

/**
 *
 * @author Tifani
 */
public class PlayerAvatarMaker {
    private static int currentCivilianImage = 1;
    private static int currentWerewolfImage = 1;
    private static final int maxCivilianImage = 4;
    private static final int maxWerewolfImage = 2;
    private static Map<Integer, Integer> playersImages = new HashMap<>();
    
    public static void addPlayer(HashMap<Integer, Player> players) {
        for (Map.Entry<Integer, Player> player : players.entrySet()) {
            playersImages.put(player.getValue().getPlayerId(), getCurrentCivilianImage());
        }
    }
    
    public static void addPlayer(Player player) {
        playersImages.put(player.getPlayerId(), getCurrentCivilianImage());
    }
    
    public static void setPlayerHasDied(Player player) {
        playersImages.put(player.getPlayerId(), getCurrentWerewolfImage());
    }
    
    public static void setPlayerIsWerewolf(Player player) {
        playersImages.put(player.getPlayerId(), getCurrentWerewolfImage());
    }
    
    private static int getCurrentCivilianImage() {
        int ret = currentCivilianImage;
        currentCivilianImage++;
        if (currentCivilianImage > maxCivilianImage)
            currentCivilianImage = 1;
        return ret;
    }
    
    private static int getCurrentWerewolfImage() {
        int ret = currentWerewolfImage;
        currentWerewolfImage++;
        if (currentWerewolfImage > maxWerewolfImage)
            currentWerewolfImage = 1;
        return ret;
    }
    
    private static int getPlayerImageId(Player player) {
        if (playersImages.containsKey(player.getPlayerId())) {
            return playersImages.get(player.getPlayerId());
        } else {
            addPlayer(player);
            return playersImages.get(player.getPlayerId());
        }
    }
    
    public static String createStringImageResource(Player player, String size) {
        if (player.getRole() != null) {
            if (player.isAlive()) {
            
            System.out.println("/werewolvesinwonderland/client/assets/ic_player" + size +
                    "_" + player.getRole() + getPlayerImageId(player) + ".png");
            return "/werewolvesinwonderland/client/assets/ic_player" + size +
                    "_" + player.getRole() + getPlayerImageId(player) + ".png";
            } else {
                System.out.println("/werewolvesinwonderland/client/assets/ic_player" + size +
                        "_" + player.getRole() + getPlayerImageId(player) + "_died.png");

                return "/werewolvesinwonderland/client/assets/ic_player" + size +
                        "_" + player.getRole() + getPlayerImageId(player) + "_died.png";
            }
        } else {
            return "/werewolvesinwonderland/client/assets/ic_player" + size +
                    "_" + "civilian" + getPlayerImageId(player) + ".png"; // dummy
        }
        
    }
    
}
