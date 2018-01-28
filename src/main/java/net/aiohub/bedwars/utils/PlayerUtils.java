package net.aiohub.bedwars.utils;

import net.aiohub.bedwars.BedWars;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import org.bukkit.entity.Player;

import lombok.Getter;

@Getter
public class PlayerUtils {

    private List<Player> players = new ArrayList<>();
    private List<Player> players2 = new ArrayList<>();
    private List<Player> specs = new ArrayList<>();
    private HashMap<Player, Team> getTeamByPlayer = new HashMap<>();

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public void addPlayer2(Player player) {
        players2.add(player);
    }

    public Team getGetTeamByPlayer(Player player) {
        if (!getTeamByPlayer.containsKey(player)) {
            return null;
        }
        return getTeamByPlayer.get(player);
    }

    public void setTeam(Player player, Team team) {
        if (getTeamByPlayer.containsKey(player)) {
            removeTeamPlayer(player);
        }
        getTeamByPlayer.put(player, team);
    }

    public void removeTeamPlayer(Player player) {
        getTeamByPlayer.remove(player);
    }

    public void removePlayer2(Player player) {
        players2.remove(player);
    }

    public void addSpec(Player player) {
        specs.add(player);
    }

    public void removeSpec(Player player) {
        specs.remove(player);
    }

    public void addRandomTeam(Player player) {
        if (this.getGetTeamByPlayer(player) != null) {
            return;
        }
        IntStream.range(0, BedWars.getInstance().getServerUtils().getMaxSize()).forEach(teamSize -> {
            IntStream.range(0, BedWars.getInstance().getServerUtils().getAllTeams().size()).forEach(i -> {
                if (this.getGetTeamByPlayer(player) != null) {
                    return;
                }
                Team team = BedWars.getInstance().getServerUtils().getAllTeams().get(i);
                if (team.getPlayers().size() == teamSize) {
                    if (!team.getPlayers().contains(player)) {
                        setTeam(player, team);
                        team.addPlayer(player);
                    }
                }
            });
        });

    }


}
