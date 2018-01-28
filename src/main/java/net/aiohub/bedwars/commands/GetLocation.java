package net.aiohub.bedwars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GetLocation implements CommandExecutor{

    public static ArrayList<Player> getLocation = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(commandSender instanceof Player) {
            if(commandSender.hasPermission("bw.admin")) {
                Player player = (Player)commandSender;
                player.sendMessage(player.getLocation().getX() + ":" + player.getLocation().getY()+
                        player.getLocation().getZ() + ":" +player.getLocation().getYaw() + ":" + player.getLocation().getPitch());
                player.sendMessage("Desweiteren siehst du nun die Bed Locations beim schlagen");
                getLocation.add(player);
                return true;
            }

            return true;
        }

        return true;
    }
}
