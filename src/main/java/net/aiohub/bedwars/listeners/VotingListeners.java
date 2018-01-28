package net.aiohub.bedwars.listeners;

import net.aiohub.bedwars.BedWars;
import net.aiohub.bedwars.utils.GoldVoting;
import net.aiohub.bedwars.utils.ItemBuilder;
import net.aiohub.bedwars.utils.Status;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.stream.IntStream;

public class VotingListeners implements Listener{

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (BedWars.getInstance().getStatus() == Status.LOBBY) {
            if (e.getItem() == null) {
                return;
            }
            if (e.getItem().getType() == Material.NETHER_STAR) {
                Inventory inv = Bukkit.createInventory(null, 9, "§aVoting");
                inv.setItem(0, new ItemBuilder(Material.GOLD_INGOT).setName("§3Gold Voting").toItemStack());
                inv.setItem(8, new ItemBuilder(Material.MAP).setName("§3Map Voting").toItemStack());
                player.openInventory(inv);
                return;
            }
        }
    }

    @EventHandler
    public void onGoldVotingClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (BedWars.getInstance().getStatus() == Status.LOBBY) {
            if(e.getCurrentItem() == null) {
                return;
            }
            if(e.getCurrentItem().getItemMeta() == null) {
                return;
            }
            if(e.getClickedInventory().getName() == null) {
                return;
            }
            if(e.getClickedInventory().getName().contains("§aVoting")) {
                if(e.getCurrentItem().getType() == Material.GOLD_INGOT) {
                    Inventory inv = Bukkit.createInventory(null, 9, "§aGold Voting");
                    inv.setItem(0, new ItemBuilder(Material.INK_SACK,1, (byte) 10).setName("§aAktivieren").setLore("§7Votes: §a" + BedWars.getInstance().getGoldVoting().getVotes(GoldVoting.GoldVotingEnum.YES)).toItemStack());
                    inv.setItem(8, new ItemBuilder(Material.INK_SACK,1, (byte) 8).setName("§cDeaktivieren").setLore("§7Votes: §a" + BedWars.getInstance().getGoldVoting().getVotes(GoldVoting.GoldVotingEnum.NO)).toItemStack());
                    player.openInventory(inv);
                    return;
                }
                if(e.getCurrentItem().getType() == Material.MAP) {
                    Inventory inv = Bukkit.createInventory(null, 18, "§aMap Voting");
                    IntStream.range(0, BedWars.getInstance().getMaps().size()).forEach(i -> {
                        String name = BedWars.getInstance().getMaps().get(i);
                        inv.setItem(i, new ItemBuilder(Material.MAP).setName("§a" + name).setLore("§7Votes: §a" + BedWars.getInstance().getMapVoting().getVotes(name)).toItemStack());
                    });
                    player.openInventory(inv);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onGoldVoting(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (BedWars.getInstance().getStatus() == Status.LOBBY) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) {
                return;
            }
            if (e.getCurrentItem().getItemMeta() == null) {
                return;
            }
            if (e.getClickedInventory().getName() == null) {
                return;
            }
            if(e.getClickedInventory().getName().contains("§aGold Voting")) {
                if(e.getCurrentItem().getItemMeta().getDisplayName().contains("§aAktivieren")) {
                    BedWars.getInstance().getGoldVoting().setVoteByPlayer(player, GoldVoting.GoldVotingEnum.YES);
                    e.getClickedInventory().setItem(0, new ItemBuilder(Material.INK_SACK,1, (byte) 10).setName("§aAktivieren").setLore("§7Votes: §a" + BedWars.getInstance().getGoldVoting().getVotes(GoldVoting.GoldVotingEnum.YES)).toItemStack());
                    e.getClickedInventory().setItem(8, new ItemBuilder(Material.INK_SACK,1, (byte) 8).setName("§cDeaktivieren").setLore("§7Votes: §a" + BedWars.getInstance().getGoldVoting().getVotes(GoldVoting.GoldVotingEnum.NO)).toItemStack());
                    return;
                }
                if(e.getCurrentItem().getItemMeta().getDisplayName().contains("§cDeaktivieren")) {
                    BedWars.getInstance().getGoldVoting().setVoteByPlayer(player, GoldVoting.GoldVotingEnum.NO);
                    e.getClickedInventory().setItem(0, new ItemBuilder(Material.INK_SACK,1, (byte) 10).setName("§aAktivieren").setLore("§7Votes: §a" + BedWars.getInstance().getGoldVoting().getVotes(GoldVoting.GoldVotingEnum.YES)).toItemStack());
                    e.getClickedInventory().setItem(8, new ItemBuilder(Material.INK_SACK,1, (byte) 8).setName("§cDeaktivieren").setLore("§7Votes: §a" + BedWars.getInstance().getGoldVoting().getVotes(GoldVoting.GoldVotingEnum.NO)).toItemStack());
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onMapVoting(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (BedWars.getInstance().getStatus() == Status.LOBBY) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) {
                return;
            }
            if (e.getCurrentItem().getItemMeta() == null) {
                return;
            }
            if (e.getClickedInventory().getName() == null) {
                return;
            }
            if(e.getClickedInventory().getName().contains("§aMap Voting")) {
                if(BedWars.getInstance().getMapVoting().isOver()) {
                    player.closeInventory();
                    player.sendMessage("§cDas Voting ist vorbei.");
                    return;
                }
                String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                BedWars.getInstance().getMapVoting().setVoteByPlayer(player, name);
                IntStream.range(0, BedWars.getInstance().getMaps().size()).forEach(i -> {
                    String mapName = BedWars.getInstance().getMaps().get(i);
                    e.getClickedInventory().setItem(i, new ItemBuilder(Material.MAP).setName("§a" + mapName).setLore("§7Votes: §a" + BedWars.getInstance().getMapVoting().getVotes(mapName)).toItemStack());
                });
                return;
            }
        }
    }

}
