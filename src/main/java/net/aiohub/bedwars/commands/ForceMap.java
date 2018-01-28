package net.aiohub.bedwars.commands;

import net.aiohub.bedwars.BedWars;
import net.aiohub.bedwars.utils.Status;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ForceMap implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if(BedWars.getInstance().getStatus() != Status.LOBBY) {

            return true;
        }
        if(args.length != 1)
            return true;
        if(BedWars.getInstance().getCountdownHandler().getLobbyCountdown() <= 5) {
            player.sendMessage("§cForceMap nicht mehr möglich.");
            return true;
        }
        String map = args[0];
        if(BedWars.getInstance().getMaps().contains(map)) {
            player.sendMessage("§cDiese Map gibt es nicht.");
            return true;
        }
        BedWars.getInstance().setMapName(map);
        BedWars.getInstance().setForceMap(true);
        return true;


    }

}

