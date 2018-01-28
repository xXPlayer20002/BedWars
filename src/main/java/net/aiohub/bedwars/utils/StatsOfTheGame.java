package net.aiohub.bedwars.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class StatsOfTheGame {

    private HashMap<Player, Integer> kills = new HashMap<>();
    private HashMap<Player, Integer> blocks = new HashMap<>();
    private HashMap<Player, Integer> beds = new HashMap<>();

    public void addKill(Player player) {
        if(!kills.containsKey(player)) {
            kills.put(player, 1);
            return;
        }
        kills.put(player, kills.get(player)+1);
    }

    public void addBlocks(Player player) {
        if(!blocks.containsKey(player)) {
            blocks.put(player, 1);
            return;
        }
        blocks.put(player, blocks.get(player)+1);
    }

    public void addBed(Player player) {
        if(!beds.containsKey(player)) {
            beds.put(player, 1);
            return;
        }
        beds.put(player, beds.get(player)+1);
    }

    public void sendMessage(Player player) {
        player.sendMessage("§6Deine Statistiken dieser Runde");
        player.sendMessage("§7Zerstörte Betten: §6" + getBeds(player));
        player.sendMessage("§7Kills: §6" + getKills(player));
        player.sendMessage("§7Platzierte Blöcke: §6" + getBlocks(player));
    }

    public Integer getKills(Player player) {
        if(!kills.containsKey(player)) {
           return 0;
        }
        return kills.get(player);
    }

    public Integer getBeds(Player player) {
        if(!beds.containsKey(player)) {
            return 0;
        }
        return beds.get(player);
    }

    public Integer getBlocks(Player player) {
        if(!blocks.containsKey(player)) {
            return 0;
        }
        return blocks.get(player);
    }

}
