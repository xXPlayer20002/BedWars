package net.aiohub.bedwars.listeners;

import net.aiohub.bedwars.BedWars;
import net.aiohub.bedwars.utils.Status;
import net.aiohub.bedwars.utils.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Bed;


public class BedListener implements Listener {


    @EventHandler
    public void onExplode(EntityExplodeEvent e) {
        List<Block> blocks = new ArrayList<>();
        CancelListener.placedBlocks.forEach(block ->


        e.blockList().forEach(block1 -> {
            // TODO Auto-generated method stub

                if(block.equals(block1))
                blocks.add(block1);


        }));
        e.blockList().clear();
        blocks.forEach(block ->
        e.blockList().add(block));
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getClickedBlock() == null) {
                return;
            }
            if (e.getClickedBlock().getType() == Material.BED_BLOCK) {

                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Block block = e.getBlock();
        if (BedWars.getInstance().getStatus() == Status.INGAME) {
            if (e.getBlock().getType() == Material.BED_BLOCK) {
                Team team = BedWars.getInstance().getPlayerUtils().getGetTeamByPlayer(e.getPlayer());
                Bed bed = new Bed(e.getBlock().getType(), e.getBlock().getData());
                if (!bed.isHeadOfBed()) {
                    block = block.getRelative(bed.getFacing());
                }
                if (team.getBed().equals(block)) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage("§cDu kannst dein eigenes Bett nicht zerstören.");
                    return;
                }

                Team teamBed = BedWars.getInstance().getServerUtils().getBedByTeam(block);
                if (teamBed != null) {
                    teamBed.setBedAlive(false);
                }
                BedWars.getInstance().getStatsOfTheGame().addBed(e.getPlayer());
                BedWars.getInstance().getStatsAPI().addValueToKey(e.getPlayer().getUniqueId(), "beds", 1);
                e.getPlayer().sendMessage("§3Du hast das Bett von Team " + teamBed.getPrefix() + teamBed.getName()
                        + " §3zerstört.");

                Bukkit.getOnlinePlayers().forEach(player -> {

                    BedWars.getInstance().getGameUtils().updateScoreboard(player);
                    player.playSound(player.getLocation(), Sound.WITHER_DEATH, 1, 1);
                    player.sendMessage("§4Das Bett von Team " + teamBed.getPrefix() + teamBed.getName() +
                            " §4wurde von " + e.getPlayer().getDisplayName() + " §4zerstört.");
                });
                teamBed.getPlayers().forEach(player ->
                        player.sendMessage("§4Das Bett " + teamBed.getPrefix() + "deines Teams §4wurde abgebaut!"));


                return;
            }
        }
    }

    @EventHandler
    public void onItemSpawnEvent(ItemSpawnEvent e) {
        if (e.getEntity().getItemStack().getType() == Material.BED_BLOCK || e.getEntity().getItemStack().getType() == Material.BED) {
            e.setCancelled(true);
            return;
        }
    }


}
