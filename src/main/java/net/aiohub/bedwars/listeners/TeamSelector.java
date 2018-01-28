package net.aiohub.bedwars.listeners;

import net.aiohub.bedwars.BedWars;
import net.aiohub.bedwars.shop.ShopCategory;
import net.aiohub.bedwars.shop.ShopItem;
import net.aiohub.bedwars.utils.Status;
import net.aiohub.bedwars.utils.Team;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.stream.IntStream;

public class TeamSelector implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (BedWars.getInstance().getStatus() == Status.LOBBY) {
            if (e.getItem() == null) {
                return;
            }
            if (e.getItem().getType() == Material.BED) {
                Inventory inv;
                if (BedWars.getInstance().getServerUtils().getAllTeams().size() <= 9) {
                    inv = Bukkit.createInventory(null, 9, "§aTeamSelector");
                } else if (BedWars.getInstance().getServerUtils().getAllTeams().size() > 9 && BedWars.getInstance().getServerUtils().getAllTeams().size() <= 18) {
                    inv = Bukkit.createInventory(null, 18, "§aTeamSelector");
                } else {
                    inv = Bukkit.createInventory(null, 27, "§aTeamSelector");
                }

                IntStream.range(0, BedWars.getInstance().getServerUtils().getAllTeams().size()).forEach(i ->
                        inv.setItem(i, BedWars.getInstance().getServerUtils().getAllTeams().get(i).getIcon()));
                e.getPlayer().openInventory(inv);
                return;
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (BedWars.getInstance().getStatus() == Status.LOBBY) {
            if (e.getInventory().getName().contains("§aTeamSelector")) {
                e.setCancelled(true);
                if (e.getCurrentItem() == null) {
                    return;
                }
                if (e.getCurrentItem().getItemMeta() == null) {
                    return;
                }
                if (e.getCurrentItem().getItemMeta().getDisplayName() == null) {
                    return;
                }

                Team team = getTeamByName(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
                if (team.getPlayers().size() == team.getSize()) {
                    player.sendMessage("§cDas Team ist bereits voll.");
                    return;
                }
                team.addPlayer(player);
                IntStream.range(0, BedWars.getInstance().getServerUtils().getAllTeams().size()).forEach(i ->
                        e.getClickedInventory().setItem(i, BedWars.getInstance().getServerUtils().getAllTeams().get(i).getIcon()));
                return;
            }
        }
    }

    public Team getTeamByName(String teamName) {
        final Team[] team1 = new Team[1];
        BedWars.getInstance().getServerUtils().getAllTeams().forEach(team -> {
            if (team.getName().equalsIgnoreCase(teamName)) {
                team1[0] = team;
            }
        });

        return team1[0];
    }

}
