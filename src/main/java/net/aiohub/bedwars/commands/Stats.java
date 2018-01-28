package net.aiohub.bedwars.commands;

import net.aiohub.bedwars.BedWars;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class Stats implements CommandExecutor{

    @Override
    public boolean onCommand(final CommandSender cs, Command cmd, String label,
                             String[] args) {
        if(!(cs instanceof Player)) {
            return true;
        }
        if(args.length == 0) {

            Player p = (Player) cs;
            int kills = BedWars.getInstance().getStatsAPI().getValue(p.getUniqueId(), "kills");
            int deaths =BedWars.getInstance().getStatsAPI().getValue(p.getUniqueId(), "deaths");
            int wins = BedWars.getInstance().getStatsAPI().getValue(p.getUniqueId(), "wins");
            int games = BedWars.getInstance().getStatsAPI().getValue(p.getUniqueId(), "games");
            int beds = BedWars.getInstance().getStatsAPI().getValue(p.getUniqueId(), "beds");
            double kd = Double.valueOf(kills) / Double.valueOf(deaths);
            if(deaths == 0) {
                kd = kills;
            }

            DecimalFormat f = new DecimalFormat("#0.00");
            double toFormat = ((double)Math.round(kd*100))/100;

            String formatted = f.format(toFormat);
            cs.sendMessage("");
            cs.sendMessage("§6Stats §8● §7Spieler §8» §e"+p.getName());
            cs.sendMessage("§6Stats §8● §7Kills §8» §e" + kills);
            cs.sendMessage("§6Stats §8● §7Deaths §8» §e" + deaths);
            cs.sendMessage("§6Stats §8● §7K/D §8» §e" + formatted.replace("NaN", "0").replace("Infinity", "0"));
            cs.sendMessage("§6Stats §8● §7Betten §8» §e" + beds);
            cs.sendMessage("§6Stats §8● §7wins §8» §e" + wins);

            if(games <=0) {
                cs.sendMessage("§6Stats §8● §7Niederlagen §8» §e" + (0));
            }else{
                int looses = games-wins;
                if(looses < 0) {
                    looses = 0;
                }
                cs.sendMessage("§6Stats §8● §7Niederlagen §8» §e" +looses);
            }
            cs.sendMessage("§6Stats §8● §7Rang §8» §e" + BedWars.getInstance().getStatsAPI().getRank(p.getUniqueId(), "wins"));
            cs.sendMessage("");

            return true;
        }
        if(Bukkit.getPlayer(args[0]) != null) {
            Player p = Bukkit.getPlayer(args[0]);
            int kills = BedWars.getInstance().getStatsAPI().getValue(p.getUniqueId(), "kills");
            int deaths =BedWars.getInstance().getStatsAPI().getValue(p.getUniqueId(), "deaths");
            int wins = BedWars.getInstance().getStatsAPI().getValue(p.getUniqueId(), "wins");
            int games = BedWars.getInstance().getStatsAPI().getValue(p.getUniqueId(), "games");
            int beds = BedWars.getInstance().getStatsAPI().getValue(p.getUniqueId(), "beds");
            double kd = Double.valueOf(kills) / Double.valueOf(deaths);
            if(deaths == 0) {
                kd = kills;
            }

            DecimalFormat f = new DecimalFormat("#0.00");
            double toFormat = ((double)Math.round(kd*100))/100;

            String formatted = f.format(toFormat);
            cs.sendMessage("");
            cs.sendMessage("§6Stats §8● §7Spieler §8» §e"+p.getName());
            cs.sendMessage("§6Stats §8● §7Kills §8» §e" + kills);
            cs.sendMessage("§6Stats §8● §7Deaths §8» §e" + deaths);
            cs.sendMessage("§6Stats §8● §7K/D §8» §e" + formatted.replace("NaN", "0").replace("Infinity", "0"));
            cs.sendMessage("§6Stats §8● §7Betten §8» §e" + beds);
            cs.sendMessage("§6Stats §8● §7wins §8» §e" + wins);

            if(games <=0) {
                cs.sendMessage("§6Stats §8● §7Niederlagen §8» §e" + (0));
            }else{
                int looses = games-wins;
                if(looses < 0) {
                    looses = 0;
                }
                cs.sendMessage("§6Stats §8● §7Niederlagen §8» §e" +looses);
            }
            cs.sendMessage("§6Stats §8● §7Rang §8» §e" + BedWars.getInstance().getStatsAPI().getRank(p.getUniqueId(), "wins"));
            cs.sendMessage("");

            return true;
        }else{
            OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
            if(!BedWars.getInstance().getStatsAPI().isRegistered(p.getUniqueId())) {
                cs.sendMessage("§cDieser Spieler hat noch nie BedWars gespielt.");
                return true;
            }
            int kills = BedWars.getInstance().getStatsAPI().getValue(p.getUniqueId(), "kills");
            int deaths =BedWars.getInstance().getStatsAPI().getValue(p.getUniqueId(), "deaths");
            int wins = BedWars.getInstance().getStatsAPI().getValue(p.getUniqueId(), "wins");
            int games = BedWars.getInstance().getStatsAPI().getValue(p.getUniqueId(), "games");
            int beds = BedWars.getInstance().getStatsAPI().getValue(p.getUniqueId(), "beds");
            double kd = Double.valueOf(kills) / Double.valueOf(deaths);
            if(deaths == 0) {
                kd = kills;
            }

            DecimalFormat f = new DecimalFormat("#0.00");
            double toFormat = ((double)Math.round(kd*100))/100;

            String formatted = f.format(toFormat);
            cs.sendMessage("");
            cs.sendMessage("§6Stats §8● §7Spieler §8» §e"+p.getName());
            cs.sendMessage("§6Stats §8● §7Kills §8» §e" + kills);
            cs.sendMessage("§6Stats §8● §7Deaths §8» §e" + deaths);
            cs.sendMessage("§6Stats §8● §7K/D §8» §e" + formatted.replace("NaN", "0").replace("Infinity", "0"));
            cs.sendMessage("§6Stats §8● §7Betten §8» §e" + beds);
            cs.sendMessage("§6Stats §8● §7wins §8» §e" + wins);

            if(games <=0) {
                cs.sendMessage("§6Stats §8● §7Niederlagen §8» §e" + (0));
            }else{
                int looses = games-wins;
                if(looses < 0) {
                    looses = 0;
                }
                cs.sendMessage("§6Stats §8● §7Niederlagen §8» §e" +looses);
            }
            cs.sendMessage("§6Stats §8● §7Rang §8» §e" + BedWars.getInstance().getStatsAPI().getRank(p.getUniqueId(), "wins"));
            cs.sendMessage("");

            return true;

        }
    }
}
