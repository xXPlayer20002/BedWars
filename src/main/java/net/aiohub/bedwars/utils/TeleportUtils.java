package net.aiohub.bedwars.utils;

import net.aiohub.bedwars.BedWars;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;


public class TeleportUtils {

    public void setLocations() {
        BedWars.getInstance().getServerUtils().getAllTeams().forEach(team -> {
            if (BedWars.getInstance().getLocationsConfig().getString("Map.Locations." + BedWars.getInstance().getMapName()+"." + team.getName()) == null) {
                System.out.println("No Location found for: " + team.getName());
                return;
            }
            String location = BedWars.getInstance().getLocationsConfig().getString("Map.Locations." + BedWars.getInstance().getMapName() +"." + team.getName());
            String[] arrayLocation = location.split(";");
            World world = Bukkit.getWorld(arrayLocation[0]);
            double x = Double.valueOf(arrayLocation[1]);
            double y =Double.valueOf(arrayLocation[2]);
            double z = Double.valueOf(arrayLocation[3]);
            double yaw = Double.valueOf(arrayLocation[4]);
            double pitch = Double.valueOf(arrayLocation[5]);
            Location loc = new Location(world, x, y, z, (float) yaw, (float) pitch);
            team.setLoc(loc);
        });

    }

    public void teleportTeams() {
        BedWars.getInstance().getServerUtils().getTeamsLeft().forEach(team ->
            team.getPlayers().forEach(player ->
                player.teleport(team.getLoc())));
    }

}
