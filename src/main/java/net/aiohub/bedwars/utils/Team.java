package net.aiohub.bedwars.utils;

import net.aiohub.bedwars.BedWars;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Team {

    private String name, prefix;
    private Integer size;
    private Location loc;
    private Block bed;
    private List<Player> players;
    private boolean bedAlive = true;
    private byte colorData;

    public Team(String pName, String pPrefix, Integer pSize, byte pColorData) {
        this.name = pName;
        this.prefix = pPrefix;
        this.colorData = pColorData;
        this.size = pSize;
        this.players = new ArrayList<>();
        BedWars.getInstance().getServerUtils().addTeamToAll(this);
    }

    public void addPlayer(Player player) {
        if (isInTeam(player)) {
            player.sendMessage("§cDu bist bereits in diesem Team.");
            return;
        }
        if (BedWars.getInstance().getPlayerUtils().getGetTeamByPlayer(player) != null) {
            BedWars.getInstance().getPlayerUtils().getGetTeamByPlayer(player).removePlayer(player);
        }
        BedWars.getInstance().getPlayerUtils().setTeam(player, this);
        players.add(player);
        player.sendMessage("§7Du hast das Team " + getPrefix() + getName() + " §7betreten.");
        player.setDisplayName(getPrefix() + player.getName());
    }

    public void removePlayer(Player player) {
        BedWars.getInstance().getPlayerUtils().removeTeamPlayer(player);
        players.remove(player);
    }

    public ItemStack getIcon() {
        ItemStack stack = new ItemStack(Material.STAINED_CLAY, 1, colorData);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(prefix + name);
        List<String> teamPlayers = new ArrayList<>();
        getPlayers().forEach(player ->
                teamPlayers.add(prefix + player.getName()));

        meta.setLore(teamPlayers);
        stack.setItemMeta(meta);
        return stack;
    }

    public boolean isInTeam(Player player) {

        return players.contains(player);
    }

    private HashMap<Team, ItemStack[]> teamChest = new HashMap<>();

    public void setItems(ItemStack[] itemStacks) {
        teamChest.put(this, itemStacks);
    }

    public ItemStack[] getItemsByTeam() {
        if(!teamChest.containsKey(this)) {
            return null;
        }
        return teamChest.get(this);
    }

    public void setBed(Location loc) {
        bed = loc.getBlock();
        BedWars.getInstance().getServerUtils().setBedByTeam(this, bed);
    }


}
