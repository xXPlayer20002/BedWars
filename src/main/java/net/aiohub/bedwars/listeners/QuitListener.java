package net.aiohub.bedwars.listeners;

import net.aiohub.bedwars.BedWars;
import net.aiohub.bedwars.utils.Status;
import net.aiohub.bedwars.utils.Team;
import net.aiohub.statsmodule.stats.StatsAPI;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.material.Bed;


public class QuitListener implements Listener{
	
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		e.setQuitMessage(null);
		if(BedWars.getInstance().getStatus() == Status.LOBBY) {
			e.setQuitMessage("§7Der Spieler §b" + e.getPlayer().getName() + " §7hat das Spiel verlassen.");
			Team team = BedWars.getInstance().getPlayerUtils().getGetTeamByPlayer(e.getPlayer());
			if(team != null) {
				team.removePlayer(e.getPlayer());
			}
			BedWars.getInstance().getPlayerUtils().removePlayer(e.getPlayer());
			BedWars.getInstance().getPlayerUtils().removePlayer2(e.getPlayer());
			BedWars.getInstance().getMapVoting().removeVoteByPlayer(e.getPlayer());
			BedWars.getInstance().getGoldVoting().removeVoteByPlayer(e.getPlayer());
			return;
		}
		if(BedWars.getInstance().getPlayerUtils().getPlayers().contains(e.getPlayer())) {
		Bukkit.broadcastMessage("§7Der Spieler " + e.getPlayer().getDisplayName() + " §7hat das Spiel verlassen.");
			BedWars.getInstance().getPlayerUtils().removePlayer(e.getPlayer());
            BedWars.getInstance().getPlayerUtils().removePlayer2(e.getPlayer());
		   Team team = BedWars.getInstance().getPlayerUtils().getGetTeamByPlayer(e.getPlayer());
            team.removePlayer(e.getPlayer());
			BedWars.getInstance().getStatsAPI().addValueToKey(e.getPlayer().getUniqueId(), "deaths", 1);
            Bukkit.getOnlinePlayers().forEach(all ->
                BedWars.getInstance().getGameUtils().updateScoreboard(all));
            BedWars.getInstance().getGameUtils().detectTeamDestroyed(team);
		}
	}

	@EventHandler
	public void onKick(PlayerKickEvent e) {
		e.setLeaveMessage("§7Der Spieler " + e.getPlayer().getDisplayName() + " §7hat das Spiel verlassen.");
	}

}
