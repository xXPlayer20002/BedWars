package net.aiohub.bedwars.listeners;

import net.aiohub.bedwars.BedWars;
import net.aiohub.bedwars.utils.Team;
import net.aiohub.statsmodule.stats.StatsAPI;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class DeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        e.getDrops().clear();
        Player player = e.getEntity();
        if (BedWars.getInstance().getPlayerUtils().getPlayers().contains(player)) {

            if (e.getEntity().getKiller() != null) {
                Team team = BedWars.getInstance().getPlayerUtils().getGetTeamByPlayer(player);
                if (!team.isBedAlive()) {
                    BedWars.getInstance().getStatsAPI().addValueToKey(player.getUniqueId(), "deaths", 1);
                    BedWars.getInstance().getStatsAPI().addValueToKey(player.getKiller().getUniqueId(), "kills", 1);
                    player.getKiller().playSound(player.getKiller().getLocation(), Sound.LEVEL_UP, 1, 1);
                }
                player.sendMessage("§7Du wurdest von " + player.getKiller().getDisplayName() + " §7getötet.");
                e.getEntity().getKiller().sendMessage("§7Du hast den Spieler " + player.getDisplayName() + " §7getötet.");
                e.setDeathMessage(player.getDisplayName() + " §7wurde von " + player.getKiller().getDisplayName() + " §7getötet.");
                BedWars.getInstance().getStatsOfTheGame().addKill(player.getKiller());
            } else {
                e.setDeathMessage(player.getDisplayName() + " §7ist gestorben.");
            }

            player.setWalkSpeed((float) 0.2);
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            new BukkitRunnable() {

                @Override
                public void run() {
                    player.spigot().respawn();
                }
            }.runTaskLater(BedWars.getInstance(), 10);


            return;
        }
    }

    private Random random = new Random();

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Team team = BedWars.getInstance().getPlayerUtils().getGetTeamByPlayer(e.getPlayer());
        if (team.isBedAlive()) {
            Location loc = team.getLoc();
            e.setRespawnLocation(loc);
        } else {
            if (BedWars.getInstance().getPlayerUtils().getPlayers().contains(e.getPlayer())) {
                BedWars.getInstance().getPlayerUtils().removePlayer(e.getPlayer());
                BedWars.getInstance().getPlayerUtils().removePlayer2(e.getPlayer());
                BedWars.getInstance().getPlayerUtils().addSpec(e.getPlayer());
                team.removePlayer(e.getPlayer());
                e.getPlayer().setGameMode(GameMode.SPECTATOR);
                Bukkit.getOnlinePlayers().forEach(all -> {
                    BedWars.getInstance().getGameUtils().updateScoreboard(all);
                    all.hidePlayer(e.getPlayer());
                });
                BedWars.getInstance().getGameUtils().detectTeamDestroyed(team);
            }
            e.setRespawnLocation(BedWars.getInstance().getPlayerUtils().getPlayers().get(random.nextInt(BedWars.getInstance().getPlayerUtils().getPlayers().size())).getLocation());

        }
    }


}
