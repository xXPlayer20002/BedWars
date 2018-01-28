package net.aiohub.bedwars.commands;

import net.aiohub.bedwars.BedWars;
import net.aiohub.bedwars.utils.Status;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Start implements CommandExecutor{
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			return true;
		}
		Player player = (Player) sender;
		if(BedWars.getInstance().getStatus() != Status.LOBBY) {
			
			return true;
		}
		if(BedWars.getInstance().getCountdownHandler().getLobbyCountdown() <= 10) {

			return true;
		}
		player.sendMessage("§7Du hast das Spiel gestartet.");
		BedWars.getInstance().getCountdownHandler().setLobbyCountdown(11);
		return true;
		
		
	}

}
