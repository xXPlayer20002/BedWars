package net.aiohub.bedwars.utils;

import net.aiohub.bedwars.BedWars;
import net.aiohub.bedwars.shop.CustomEntityTypes;
import net.aiohub.bedwars.shop.CustomVillager;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import lombok.Getter;

public class GameUtils {

    public void onWin() {


        Team team = BedWars.getInstance().getServerUtils().getTeamsLeft().get(0);
        Bukkit.getOnlinePlayers().forEach(all -> {
            TitleApi.sendTitel(all, team.getPrefix() + "Team " + team.getName());
            TitleApi.subTitel(all, "§fhat gewonnen!");
            all.sendMessage("§7Team " + team.getPrefix() + team.getName() + " §7hat gewonnen!");
        });
        team.getPlayers().forEach(player ->
                BedWars.getInstance().getStatsAPI().addValueToKey(player.getUniqueId(), "wins", 1));

        BedWars.getInstance().getCountdownHandler().onEnd();
    }

    public void detectTeamDestroyed(Team team) {
        if (team.getPlayers().size() == 0) {
            Bukkit.broadcastMessage("§4Team " + team.getPrefix() + team.getName() + " §4wurde vernichtet.");
            BedWars.getInstance().getServerUtils().getTeamsLeft().remove(team);
        }
        if (BedWars.getInstance().getServerUtils().getTeamsLeft().size() == 1) {
            onWin();
            return;
        }
    }

    public void updateScoreboard(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("aaa", "bbb");
        objective.setDisplayName("§6AIOHub.net");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);


        IntStream.range(0, BedWars.getInstance().getServerUtils().getAllTeams().size()).forEach(i -> {

            Team bedwarsTeam = BedWars.getInstance().getServerUtils().getAllTeams().get(i);

            org.bukkit.scoreboard.Team team = null;

            if (scoreboard.getTeam("00" + i + bedwarsTeam.getName()) == null) {
                team = scoreboard.registerNewTeam("00" + i + bedwarsTeam.getName());
            }
            team.setAllowFriendlyFire(false);
            team.setPrefix(bedwarsTeam.getPrefix());
            for (Player all : Bukkit.getOnlinePlayers()) {
                if (bedwarsTeam.getPlayers().contains(all)) {
                    team.addPlayer(all);
                }
            }
            org.bukkit.scoreboard.Team bwTeam = null;
            if (scoreboard.getTeam(bedwarsTeam.getName()) == null) {
                bwTeam = scoreboard.registerNewTeam(bedwarsTeam.getName());
            }
            if (bedwarsTeam.isBedAlive() && bedwarsTeam.getPlayers().size() > 0) {
                objective.getScore("§2✔ " + bedwarsTeam.getPrefix() + bedwarsTeam.getName()).setScore(bedwarsTeam.getPlayers().size());
            } else {
                objective.getScore("§4✘ " + bedwarsTeam.getPrefix() + bedwarsTeam.getName()).setScore(bedwarsTeam.getPlayers().size());
            }
            objective.getScore("§l").setScore(-1);
            objective.getScore("§6Gold:").setScore(-2);
            if (BedWars.getInstance().getGoldVoting().getGoldVotingEnum() == GoldVoting.GoldVotingEnum.YES) {
                objective.getScore("§aaktiviert").setScore(-3);
            } else {
                objective.getScore("§cdeaktiviert").setScore(-3);
            }
        });

        player.setScoreboard(scoreboard);

    }

    private HashMap<Spawner.SpawnerType, List<Spawner>> getSpawner = new HashMap<>();

    public void setSpawner(Spawner.SpawnerType spawnerType, Spawner spawner) {
        if (!getSpawner.containsKey(spawnerType)) {
            List<Spawner> spawnerList = new ArrayList<>();
            spawnerList.add(spawner);
            getSpawner.put(spawnerType, spawnerList);
        } else {
            List<Spawner> spawnerList = getSpawner.get(spawnerType);
            spawnerList.add(spawner);
            getSpawner.put(spawnerType, spawnerList);
        }
    }

    public void setBronzeSpawner() {
        new BukkitRunnable() {
            @Override
            public void run() {

                getSpawner.get(Spawner.SpawnerType.BRONZE).forEach(spawner ->


                        spawner.getLocation().getWorld().dropItem(spawner.getLocation(), new ItemBuilder(Material.CLAY_BRICK, 1).setName("§cBronze").toItemStack()));


            }
        }.runTaskTimer(BedWars.getInstance(), 0, 15);
    }

    public void setEisenSpawner() {
        new BukkitRunnable() {
            @Override
            public void run() {

                getSpawner.get(Spawner.SpawnerType.EISEN).forEach(spawner ->


                        spawner.getLocation().getWorld().dropItem(spawner.getLocation(), new ItemBuilder(Material.IRON_INGOT).setName("§7Eisen").toItemStack()));


            }
        }.runTaskTimer(BedWars.getInstance(), 180, 180);
    }

    public void setGoldSpawner() {
        new BukkitRunnable() {
            @Override
            public void run() {

                getSpawner.get(Spawner.SpawnerType.GOLD).forEach(spawner ->


                        spawner.getLocation().getWorld().dropItem(spawner.getLocation(), new ItemBuilder(Material.GOLD_INGOT).setName("§6Gold").toItemStack()));


            }
        }.runTaskTimer(BedWars.getInstance(), 500, 500);
    }
    @Getter
    private ArrayList<Player> isPlayerMoving = new ArrayList<>();

    public void setBaseTeleportCountdown(Player player, ItemStack stack) {
        isPlayerMoving.add(player);
        new BukkitRunnable() {
            int timer = 0;
            int timer2 = 7;
            List<Location> locations = new ArrayList<Location>();

            @Override
            public void run() {
                if(!isPlayerMoving.contains(player)) {
                    cancel();
                    return;
                }
                if (timer == 0 || timer == 2 || timer == 4 || timer == 6) {

                    locations.addAll(getCircle(player.getLocation().add(0, timer / 2, 0), 1, 6));
                    locations.forEach(location ->
                            new Particle(EnumParticle.HEART, location, true, 0,0,0, 3, 1).sendAll());

                }
                if(timer <= 6) {
                    timer++;
                    timer2--;
                    TitleApi.endTitel(player, "§aTeleport in §b" + timer2/2);
                }

                if (timer == 7) {
                    player.teleport(BedWars.getInstance().getPlayerUtils().getGetTeamByPlayer(player).getLoc());
                    if(stack.getAmount() == 1)
                    player.getInventory().removeItem(stack);
                    else {
                        stack.setAmount(1);
                        player.getInventory().removeItem(stack);
                    }
                    isPlayerMoving.remove(player);
                    cancel();
                }
            }
        }.runTaskTimer(BedWars.getInstance(), 0, 10);
    }

    public void setVillager(Location loc) {
        CustomVillager villager = new CustomVillager(((CraftWorld)loc.getWorld()).getHandle());
        villager.setCustomName("§aMobiler Shop");
        villager.setCustomNameVisible(true);
        CustomEntityTypes.spawnLocation(villager, loc);
        NBTTagCompound compound = new NBTTagCompound();
        ((CraftEntity) villager.getBukkitEntity()).getHandle().e(compound);
        compound.setInt("NoAI", 1);
        ((CraftEntity) villager.getBukkitEntity()).getHandle().f(compound);
        new BukkitRunnable() {
            int timer = 31;

            @Override
            public void run() {


                if(timer <= timer) {
                    timer--;
                    villager.setCustomName("§aMobiler Shop | §3" + timer);
                }

                if(timer == 0) {
                   villager.setHealth(0);
                }
            }
        }.runTaskTimer(BedWars.getInstance(), 0, 20);
    }

    public ArrayList<Location> getCircle(Location center, double radius, int amount) {
        World world = center.getWorld();
        double increment = (2 * Math.PI) / amount;
        ArrayList<Location> locations = new ArrayList<Location>();
        for (int i = 0; i < amount; i++) {
            double angle = i * increment;
            double x = center.getX() + (radius * Math.cos(angle));
            double z = center.getZ() + (radius * Math.sin(angle));
            locations.add(new Location(world, x, center.getY(), z));
        }
        return locations;
    }

}
