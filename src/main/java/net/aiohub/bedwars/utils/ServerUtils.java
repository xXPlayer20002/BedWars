package net.aiohub.bedwars.utils;

import net.aiohub.bedwars.BedWars;
import net.aiohub.bedwars.shop.CustomEntityTypes;
import net.aiohub.bedwars.shop.CustomVillager;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.material.Bed;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

@Getter
@Setter
public class ServerUtils {

    private List<Team> allTeams = new ArrayList<>();
    private List<Team> teamsLeft = new ArrayList<>();
    private int maxSize = 0;
    private File teamsFile = new File("plugins/BedWars", "teams.yml");
    private FileConfiguration teamsConfiguration;
    private HashMap<Block, Team> getBedByTeam = new HashMap<>();

    public ServerUtils() {
        teamsConfiguration = YamlConfiguration.loadConfiguration(teamsFile);
    }

    public void registerTeams() {
        IntStream.range(0, BedWars.getInstance().getTeams()).forEach(i -> {
            String teamStrings = teamsConfiguration.getStringList("Teams.Default").get(i);

            String[] array = teamStrings.split(";");
            new Team(array[0], array[1].replace("&", "§"), Integer.valueOf(array[2]), Byte.valueOf(array[3]));
        });

       // setBed();
        //setSpawner();
    }

    public void setBed() {
        IntStream.range(0, allTeams.size()).forEach(i ->{
            Team team = allTeams.get(i);
            if (BedWars.getInstance().getLocationsConfig().getString("Bed." + BedWars.getInstance().getMapName() + "." + team.getName()) == null) {
                Bukkit.broadcastMessage("Null");
                BedWars.getInstance().getLocationsConfig().set("Bed1." + BedWars.getInstance().getMapName() + "." + team.getName(), "abc");
                try {
                    BedWars.getInstance().getLocationsConfig().save(BedWars.getInstance().getLocationsFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
            String location = BedWars.getInstance().getLocationsConfig().getString("Bed." + BedWars.getInstance().getMapName()+ "." + team.getName());
            String[] arrayLocation = location.split(";");
            World world = Bukkit.getWorld(arrayLocation[0]);
            double x = Double.valueOf(arrayLocation[1]);
            double y =Double.valueOf(arrayLocation[2]);
            double z = Double.valueOf(arrayLocation[3]);
            Location loc = new Location(world, x, y, z);

            team.setBed(loc);
        });
    }

    public void setSpawner() {

            if (BedWars.getInstance().getLocationsConfig().getString("Map.BronzeSpawner." + BedWars.getInstance().getMapName()) == null) {
                Bukkit.broadcastMessage("No Locations for Bronze");
                return;
            }
        IntStream.range(0, BedWars.getInstance().getLocationsConfig().getStringList("Map.BronzeSpawner." + BedWars.getInstance().getMapName()).size()).forEach(i ->{
            List<String> location = BedWars.getInstance().getLocationsConfig().getStringList("Map.BronzeSpawner." + BedWars.getInstance().getMapName());
            String[] arrayLocation = location.get(i).split(";");

            World world = Bukkit.getWorld(arrayLocation[0]);
            double x = Double.valueOf(arrayLocation[1]);
            double y =Double.valueOf(arrayLocation[2])+1;
            double z = Double.valueOf(arrayLocation[3]);
            double yaw = Double.valueOf(arrayLocation[4]);
            double pitch = Double.valueOf(arrayLocation[5]);
            Location loc = new Location(world, x, y, z, (float) yaw, (float) pitch);
          Spawner spawner = new Spawner(Spawner.SpawnerType.BRONZE, loc);
          BedWars.getInstance().getGameUtils().setSpawner(spawner.getSpawnerType(), spawner);
        });
        IntStream.range(0, BedWars.getInstance().getLocationsConfig().getStringList("Map.EisenSpawner." + BedWars.getInstance().getMapName()).size()).forEach(i ->{
            if (BedWars.getInstance().getLocationsConfig().getString("Map.EisenSpawner." + BedWars.getInstance().getMapName()) == null) {

                return;
            }

            List<String> location = BedWars.getInstance().getLocationsConfig().getStringList("Map.EisenSpawner." + BedWars.getInstance().getMapName());
            String[] arrayLocation = location.get(i).split(";");
            World world = Bukkit.getWorld(arrayLocation[0]);
            double x = Double.valueOf(arrayLocation[1]);
            double y =Double.valueOf(arrayLocation[2])+1;
            double z = Double.valueOf(arrayLocation[3]);
            double yaw = Double.valueOf(arrayLocation[4]);
            double pitch = Double.valueOf(arrayLocation[5]);
            Location loc = new Location(world, x, y, z, (float) yaw, (float) pitch);
            Spawner spawner = new Spawner(Spawner.SpawnerType.EISEN, loc);
            BedWars.getInstance().getGameUtils().setSpawner(spawner.getSpawnerType(), spawner);
        });
        IntStream.range(0, BedWars.getInstance().getLocationsConfig().getStringList("Map.GoldSpawner." + BedWars.getInstance().getMapName()).size()).forEach(i ->{
            if (BedWars.getInstance().getLocationsConfig().getString("Map.GoldSpawner." + BedWars.getInstance().getMapName()) == null) {

                return;
            }
            List<String> location = BedWars.getInstance().getLocationsConfig().getStringList("Map.GoldSpawner." + BedWars.getInstance().getMapName());
            String[] arrayLocation = location.get(i).split(";");
            World world = Bukkit.getWorld(arrayLocation[0]);
            double x = Double.valueOf(arrayLocation[1]);
            double y =Double.valueOf(arrayLocation[2])+1;
            double z = Double.valueOf(arrayLocation[3]);
            double yaw = Double.valueOf(arrayLocation[4]);
            double pitch = Double.valueOf(arrayLocation[5]);
            Location loc = new Location(world, x, y, z, (float) yaw, (float) pitch);
            Spawner spawner = new Spawner(Spawner.SpawnerType.GOLD, loc);
            BedWars.getInstance().getGameUtils().setSpawner(spawner.getSpawnerType(), spawner);
        });
    }

    public void setVillager() {
        IntStream.range(0, BedWars.getInstance().getLocationsConfig().getStringList("Map.Villager." + BedWars.getInstance().getMapName()).size()).forEach(i ->{
            if (BedWars.getInstance().getLocationsConfig().getString("Map.Villager." + BedWars.getInstance().getMapName()) == null) {

                return;
            }
            List<String> location = BedWars.getInstance().getLocationsConfig().getStringList("Map.Villager." + BedWars.getInstance().getMapName());
            String[] arrayLocation = location.get(i).split(";");
            World world = Bukkit.getWorld(arrayLocation[0]);
            double x = Double.valueOf(arrayLocation[1]);
            double y =Double.valueOf(arrayLocation[2]);
            double z = Double.valueOf(arrayLocation[3]);
            double yaw = Double.valueOf(arrayLocation[4]);
            double pitch = Double.valueOf(arrayLocation[5]);
            Location loc = new Location(world, x, y, z, (float) yaw, (float) pitch);
            CustomVillager villager = new CustomVillager(((CraftWorld)world).getHandle());
            villager.setCustomName("§aShop");
            villager.setCustomNameVisible(true);
            CustomEntityTypes.spawnLocation(villager, loc);
            NBTTagCompound compound = new NBTTagCompound();
            ((CraftEntity) villager.getBukkitEntity()).getHandle().e(compound);
            compound.setInt("NoAI", 1);
            ((CraftEntity) villager.getBukkitEntity()).getHandle().f(compound);

        });
    }

    public Location getHologramLocation() {

            if (BedWars.getInstance().getLocationsConfig().getString("Stats.Hologram") == null) {

                return null;
            }
            String location = BedWars.getInstance().getLocationsConfig().getString("Stats.Hologram");
            String[] arrayLocation = location.split(";");
            World world = Bukkit.getWorld(arrayLocation[0]);
            double x = Double.valueOf(arrayLocation[1]);
            double y =Double.valueOf(arrayLocation[2]);
            double z = Double.valueOf(arrayLocation[3]);
            double yaw = Double.valueOf(arrayLocation[4]);
            double pitch = Double.valueOf(arrayLocation[5]);
            return new Location(world, x, y, z, (float) yaw, (float) pitch);
    }

    public Team getBedByTeam(Block block) {
        if(!getBedByTeam.containsKey(block)) {
            return null;
        }
        return getBedByTeam.get(block);
    }

    public void setBedByTeam(Team team, Block block) {
        getBedByTeam.put(block, team);
    }

    public void setConfig() {
        teamsConfiguration = YamlConfiguration.loadConfiguration(teamsFile);
    }

    public void addTeamToAll(Team team) {
        allTeams.add(team);
    }

    public void addTeamsLeft(Team team) {
        teamsLeft.add(team);
    }
}
