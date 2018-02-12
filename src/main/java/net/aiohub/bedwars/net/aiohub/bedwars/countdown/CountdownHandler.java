package net.aiohub.bedwars.net.aiohub.bedwars.countdown;

import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.bridge.CloudServer;
import de.dytanic.cloudnet.lib.server.ServerConfig;
import de.dytanic.cloudnet.lib.server.ServerState;

import net.aiohub.bedwars.BedWars;
import net.aiohub.bedwars.listeners.ShopListener;
import net.aiohub.bedwars.listeners.ShopUseListeners;
import net.aiohub.bedwars.utils.GoldVoting;
import net.aiohub.bedwars.utils.Status;
import net.aiohub.bedwars.utils.TitleApi;
import net.aiohub.statsmodule.stats.StatsAPI;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountdownHandler {

    private int lobbyCountdown = 61;
    private boolean started = false;



    public void onLobby() {
        new BukkitRunnable() {

            @Override
            public void run() {
                if (Bukkit.getOnlinePlayers().size() < BedWars.getInstance().getMinPlayers()) {
                    Bukkit.getOnlinePlayers().forEach(all -> {
                        all.setExp(0);
                        all.setLevel(0);
                        int playersLeft = BedWars.getInstance().getMinPlayers()-Bukkit.getOnlinePlayers().size();
                        if(playersLeft > 1)
                        TitleApi.endTitel(all, "§7Es fehlen noch §b" + playersLeft + " §7Spieler.");
                        else
                            TitleApi.endTitel(all, "§7Es fehlt noch §b" + playersLeft + " §7Spieler.");
                    });
                    started = false;
                    return;
                }else{
                    setStarted(true);
                }
                if (lobbyCountdown <= lobbyCountdown) {
                    lobbyCountdown--;
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        player.setLevel(lobbyCountdown);
                        player.setExp((float) lobbyCountdown / 60);
                    });


                    if (lobbyCountdown == 60 || lobbyCountdown == 30 || lobbyCountdown == 15 || lobbyCountdown == 10
                            || lobbyCountdown <= 5 && lobbyCountdown >= 1) {
                        if (lobbyCountdown == 5) {
                            Bukkit.getOnlinePlayers().forEach(player -> {
                                if (!BedWars.getInstance().isForceMap()) {
                                    BedWars.getInstance().getMapVoting().handleVoting();
                                }
                                TitleApi.sendTitel(player, "§7Karte: §b" + BedWars.getInstance().getMapName());
                                player.sendMessage("§7Das Map-Voting ist vorbei!" + "\n"
                                        + "§7Karte: §b" + BedWars.getInstance().getMapName());
                                CloudServer.getInstance().setMotd(BedWars.getInstance().getMapName());
                            });
                            BedWars.getInstance().getMaps().forEach(maps -> {
                                if (!maps.equalsIgnoreCase(BedWars.getInstance().getMapName()))
                                    Bukkit.unloadWorld(maps, false);
                            });
                        }
                        Bukkit.getOnlinePlayers().forEach(player -> {
                            player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 1);
                            player.sendMessage("§7Das Spiel startet in §b" + lobbyCountdown + " §7Sekunden.");
                            if (lobbyCountdown != 4 && lobbyCountdown != 5) {
                                TitleApi.sendTitel(player, "§3" + lobbyCountdown);
                            }
                        });
                    }


                }
                if (lobbyCountdown == 0) {
                    Bukkit.getOnlinePlayers().forEach(player -> {

                        player.setLevel(0);
                        player.setExp(0);
                        player.getInventory().clear();
                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                        player.getInventory().setArmorContents(null);
                        player.sendMessage("§7Das Spiel startet.");
                        StatsAPI.getInstance().addValueToKey(player.getUniqueId(), "games", 1);
                        if (BedWars.getInstance().getPlayerUtils().getGetTeamByPlayer(player) == null) {
                            BedWars.getInstance().getPlayerUtils().addRandomTeam(player);
                        }
                        ShopListener.addShopType1(player);

                    });
                    BedWars.getInstance().getServerUtils().getAllTeams().forEach(team -> {
                        if (team.getPlayers().size() > 0) {
                            BedWars.getInstance().getServerUtils().getTeamsLeft().add(team);
                        }
                    });
                    BedWars.getInstance().getServerUtils().setSpawner();
                    BedWars.getInstance().getServerUtils().setBed();
                    BedWars.getInstance().getServerUtils().setVillager();
                    BedWars.getInstance().getGoldVoting().handleVoting();
                    BedWars.getInstance().getTeleportUtils().setLocations();
                    BedWars.getInstance().getTeleportUtils().teleportTeams();
                    BedWars.getInstance().getGameUtils().setBronzeSpawner();
                    BedWars.getInstance().getGameUtils().setEisenSpawner();
                    if (BedWars.getInstance().getGoldVoting().getGoldVotingEnum() == GoldVoting.GoldVotingEnum.YES) {
                        BedWars.getInstance().getGameUtils().setGoldSpawner();
                    }
                    onIngame();

                    cancel();
                }


            }
        }.runTaskTimer(BedWars.getInstance(), 0, 20);
    }

    public void onIngame() {
        CloudServer.getInstance().setServerStateAndUpdate(ServerState.INGAME);
        CloudServer.getInstance().changeToIngame();
        Bukkit.getOnlinePlayers().forEach(all ->
                BedWars.getInstance().getGameUtils().updateScoreboard(all));

        BedWars.getInstance().setStatus(Status.INGAME);
        new BukkitRunnable() {
            int ingame = 6001;

            @Override
            public void run() {
                if (ingame <= ingame) {
                    ingame--;
                    if (ingame == 5999) {
                        Bukkit.getWorlds().forEach(world -> {

                            world.getEntities().forEach(entity -> {
                                if (!(entity instanceof Player)) {
                                    entity.remove();
                                }
                            });
                        });

                        BedWars.getInstance().getServerUtils().setVillager();
                    }
                }
                if (ingame == 600 || ingame == 300 || ingame == 120 || ingame == 60) {

                    Bukkit.getOnlinePlayers().forEach(player -> {
                        player.sendMessage("§7Das Spiel endet in §b" + ingame / 60 + " §7Sekunden.");

                    });
                }
                if (ingame == 30 || ingame == 15 || ingame == 10 || ingame <= 5 && ingame > 0) {
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        player.sendMessage("§7Das Spiel endet in §b" + ingame + " §7Sekunden.");

                    });
                }
                if (ingame == 0) {
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        player.sendMessage("§7Das Spiel ist vorbei. Es gab kein Gewinner");

                    });
                }
            }
        }.runTaskTimer(BedWars.getInstance(), 0, 20);
    }

    public void onEnd() {
        BedWars.getInstance().getPlayerUtils().getPlayers2().forEach(player ->
                BedWars.getInstance().getStatsOfTheGame().sendMessage(player));
        Bukkit.getScheduler().cancelAllTasks();
        BedWars.getInstance().setStatus(Status.END);
        new BukkitRunnable() {
            int timer = 11;

            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (timer <= timer) {
                    timer--;
                }
                if (timer == 10 || timer <= 5 && timer > 0) {
                    Bukkit.getOnlinePlayers().forEach(all -> {
                        all.sendMessage("§7Der Server restartet in §b" + timer + " §7Sekunden.");
                    });
                }
                if (timer == 0) {
                    CloudAPI.getInstance().stopServer(CloudAPI.getInstance().getServerId());
                }

            }
        }.runTaskTimer(BedWars.getInstance(), 0, 20);
    }


}
