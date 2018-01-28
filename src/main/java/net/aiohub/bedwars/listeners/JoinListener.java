package net.aiohub.bedwars.listeners;

import net.aiohub.bedwars.BedWars;
import net.aiohub.bedwars.shop.ShopCategory;
import net.aiohub.bedwars.shop.ShopItem;
import net.aiohub.bedwars.utils.HoloAPi;
import net.aiohub.bedwars.utils.ItemBuilder;
import net.aiohub.bedwars.utils.Status;
import net.aiohub.statsmodule.stats.StatsAPI;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JoinListener implements Listener {



    @SuppressWarnings("deprecation")
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        e.setJoinMessage(null);
        player.setExp(0);
        player.setLevel(0);
        player.setMaxHealth(20);
        player.setHealth(20);
        
        player.setFoodLevel(22);
        if (BedWars.getInstance().getStatus() == Status.INGAME) {
            player.setGameMode(GameMode.SPECTATOR);
            BedWars.getInstance().getPlayerUtils().addSpec(e.getPlayer());
            Bukkit.getOnlinePlayers().forEach(all -> {
                    all.hidePlayer(player);
            });
            e.getPlayer().teleport(BedWars.getInstance().getPlayerUtils().getPlayers().get(new Random().nextInt(BedWars.getInstance().getPlayerUtils().getPlayers().size())).getLocation());
            return;
        }
        BedWars.getInstance().getStatsAPI().register(player.getUniqueId());
        e.setJoinMessage("§7Der Spieler §b" + player.getName() + " §7hat das Spiel betreten.");
        player.setAllowFlight(false);
        player.setFlying(false);
        player.getInventory().clear();
        player.teleport(Bukkit.getWorld("world").getSpawnLocation());
        player.getInventory().setArmorContents(null);
        player.setGameMode(GameMode.SURVIVAL);
        BedWars.getInstance().getPlayerUtils().addPlayer(e.getPlayer());
        BedWars.getInstance().getPlayerUtils().addPlayer2(e.getPlayer());
        int kills = BedWars.getInstance().getStatsAPI().getValue(player.getUniqueId(), "kills");
        int deaths =BedWars.getInstance().getStatsAPI().getValue(player.getUniqueId(), "deaths");
        int wins = BedWars.getInstance().getStatsAPI().getValue(player.getUniqueId(), "wins");
        int games = BedWars.getInstance().getStatsAPI().getValue(player.getUniqueId(), "games");
        int beds = BedWars.getInstance().getStatsAPI().getValue(player.getUniqueId(), "beds");
        double kd = Double.valueOf(kills) / Double.valueOf(deaths);
        if(deaths == 0) {
            kd = kills;
        }

        DecimalFormat f = new DecimalFormat("#0.00");
        double toFormat = ((double)Math.round(kd*100))/100;

        String formatted = f.format(toFormat);
        List<String> lines = new ArrayList<>();
        lines.add("§7Deine §6Statistiken");
        lines.add("§7Deine Position im Ranking: §6" + BedWars.getInstance().getStatsAPI().getRank(player.getUniqueId(), "wins"));
        lines.add("§7Kills: §6" + kills);
        lines.add("§7Deaths: §6" + deaths);
        lines.add("§7K/D: §6" + formatted);
        lines.add("§7Gespielte Spiele: §6" + games);
        lines.add("§7Gewonnene Spiele: §6" + wins);
        lines.add("§7Zerstörte Betten: §6" + beds);
        HoloAPi api = new HoloAPi(BedWars.getInstance().getHologramLocation(), lines);
        api.disp(player);
        player.getActivePotionEffects().forEach(potionEffect ->
                player.removePotionEffect(potionEffect.getType()));
        player.getInventory().setItem(0, new ItemBuilder(Material.BED).setName("§aTeamauswahl").toItemStack());
        player.getInventory().setItem(4, new ItemBuilder(Material.NETHER_STAR).setName("§aVoting").toItemStack());

        if (Bukkit.getOnlinePlayers().size() == BedWars.getInstance().getMinPlayers() && BedWars.getInstance().getCountdownHandler().isStarted() == false) {
            BedWars.getInstance().getCountdownHandler().setStarted(true);
            BedWars.getInstance().getCountdownHandler().onLobby();
        }

    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        if (BedWars.getInstance().getStatus() == Status.LOBBY) {
            if (e.getResult() == PlayerLoginEvent.Result.KICK_FULL) {
                if (e.getPlayer().hasPermission("server.join")) {
                    Bukkit.getOnlinePlayers().forEach(player -> {

                        if (!player.hasPermission("server.join")) {
                            player.kickPlayer("§7Du wurdest für einen §bPremium §7Spieler gekickt.");
                            return;


                        }
                    });
                    e.allow();
                }
            }
        }
    }
}
