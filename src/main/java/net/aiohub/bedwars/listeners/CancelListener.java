package net.aiohub.bedwars.listeners;

import net.aiohub.bedwars.BedWars;
import net.aiohub.bedwars.commands.GetLocation;
import net.aiohub.bedwars.utils.ItemBuilder;
import net.aiohub.bedwars.utils.Status;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Bed;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CancelListener implements Listener{
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
		if(BedWars.getInstance().getStatus() != Status.INGAME ) {
			e.setCancelled(true);
			return;
		}
		}
		if(BedWars.getInstance().getPlayerUtils().getSpecs().contains(e.getEntity())) {
			e.setCancelled(true);
			return;
		}
		if(e.getCause() == EntityDamageEvent.DamageCause.VOID) {
			e.setDamage(100);
		}
		
	}
	@EventHandler
	public void onForm(BlockFormEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onBurn(BlockBurnEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onTree(StructureGrowEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockGrow(BlockGrowEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onFade(BlockFadeEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onLeash(LeavesDecayEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onSpread(BlockSpreadEvent e) {
		e.setCancelled(true);
	}
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if(BedWars.getInstance().getStatus() == Status.INGAME) {
			if(BedWars.getInstance().getPlayerUtils().getSpecs().contains(e.getPlayer())) {
				e.setCancelled(true);
				return;
			}
            if(e.getMessage().startsWith("@all")) {

				if(e.getMessage().equalsIgnoreCase("@all")) {
                    e.setCancelled(true);
					return;
				}
				String message = e.getMessage().replaceFirst("@all", "");
                if(message == null) {
                    e.setCancelled(true);
                    return;
                }
                boolean startWithSpace = Character.isSpaceChar(message.codePointAt(0));
				if(startWithSpace) {
					e.setFormat("§7[§fAll§7] " + p.getDisplayName() + "§f:" + message);
				}else{
					e.setFormat("§7[§fAll§7] " + p.getDisplayName() + "§f: " + message);
				}
                return;
            }else if(e.getMessage().startsWith("@a")) {
				if(e.getMessage().equalsIgnoreCase("@a")) {
                    e.setCancelled(true);
                    return;
				}
                String message = e.getMessage().replaceFirst("@a", "");
                if(message == null || message == "") {
                    e.setCancelled(true);
                    return;
                }
                boolean startWithSpace = Character.isSpaceChar(message.codePointAt(0));
                if(startWithSpace) {
                    e.setFormat("§7[§fAll§7] " + p.getDisplayName() + "§f:" + message);
                }else{
                    e.setFormat("§7[§fAll§7] " + p.getDisplayName() + "§f: " + message);
                }
                return;
            }else if(e.getMessage().startsWith("@")) {
				if(e.getMessage().equalsIgnoreCase("@")) {
                    e.setCancelled(true);
					return;
				}
                String message = e.getMessage().replaceFirst("@", "");
                if(message == null) {
                    e.setCancelled(true);
                    return;
                }
                boolean startWithSpace = Character.isSpaceChar(message.codePointAt(0));
                if(startWithSpace) {
                    e.setFormat("§7[§fAll§7] " + p.getDisplayName() + "§f:" + message);
                }else{
                    e.setFormat("§7[§fAll§7] " + p.getDisplayName() + "§f: " + message);
                }
                return;
            }
			e.setCancelled(true);
			BedWars.getInstance().getPlayerUtils().getGetTeamByPlayer(p).getPlayers().forEach(player ->
			player.sendMessage("§7[§fTeam§7] " + p.getDisplayName() + "§f: " + e.getMessage()));
			return;
		}
		e.setFormat("§f<" + p.getName() + "§f> " + e.getMessage());
		
	}
	
	@EventHandler
	public void onSpawn(CreatureSpawnEvent e) {

		if(e.getSpawnReason() == SpawnReason.CUSTOM) {
			e.setCancelled(false);
		}else{
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onHealth(EntityRegainHealthEvent e) {
		e.setCancelled(false);
	}
	
	
	
	
	
	@EventHandler
	public void onAchievment(PlayerAchievementAwardedEvent e) {
		e.setCancelled(true);
	}
	
	
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent e) {
		e.getWorld().setThundering(false);
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if(BedWars.getInstance().getStatus() != Status.INGAME ) {
	e.setCancelled(true);
}
	}

	@EventHandler
	public void onPickUp(PlayerPickupItemEvent e) {
			if(BedWars.getInstance().getStatus() != Status.INGAME ) {
				e.setCancelled(true);
			return;
		}
        if(e.getItem().getItemStack().getType() == Material.CLAY_BRICK) {
            e.getPlayer().setLevel(e.getPlayer().getLevel()+1*e.getItem().getItemStack().getAmount());
            e.setCancelled(true);
            e.getItem().remove();
			e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ITEM_PICKUP, 1,1);
            return;
        }
        if(e.getItem().getItemStack().getType() == Material.IRON_INGOT) {
            e.getPlayer().setLevel(e.getPlayer().getLevel()+10*e.getItem().getItemStack().getAmount());
            e.setCancelled(true);
            e.getItem().remove();
			e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ITEM_PICKUP, 1,1);
            return;
        }
        if(e.getItem().getItemStack().getType() == Material.GOLD_INGOT) {
            e.getPlayer().setLevel(e.getPlayer().getLevel()+100*e.getItem().getItemStack().getAmount());
            e.setCancelled(true);
            e.getItem().remove();
			e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ITEM_PICKUP, 1,1);
            return;
        }
	}
	
	@EventHandler
	public void onFood(FoodLevelChangeEvent e) {
		if(BedWars.getInstance().getStatus() != Status.INGAME ) {
			e.setCancelled(true);
		}
	}

	public static ArrayList<Block> placedBlocks = new ArrayList<>();

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if (e.getBlock().getType() == Material.BED_BLOCK) {
			if (BedWars.getInstance().getStatus() != Status.INGAME) {
				if (GetLocation.getLocation.contains(e.getPlayer())) {
					Block b = e.getBlock();
					Bed bed = new Bed(b.getType(), b.getData());
					e.getPlayer().sendMessage(String.valueOf(bed.isHeadOfBed()));
					e.getPlayer().sendMessage(b.getX() + ";" + b.getY() + ";" + b.getZ());
				}
				e.setCancelled(true);
				return;
			}

		}
        if (e.getBlock().getType() == Material.BED_BLOCK) {
            e.setCancelled(false);
            return;
        }
		if (!placedBlocks.contains(e.getBlock())) {
			e.setCancelled(true);
			return;
		}
		if(e.getBlock().getType() == Material.STAINED_CLAY) {
            e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemBuilder(Material.STAINED_CLAY, 1, BedWars.getInstance().getPlayerUtils().getGetTeamByPlayer(e.getPlayer()).getColorData()).setName("§fSteine").toItemStack());
            e.getBlock().setType(Material.AIR);
        }
        if(e.getBlock().getType() == Material.ENDER_STONE) {
            e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemBuilder(Material.ENDER_STONE).setName("§fEndstein").toItemStack());
            e.getBlock().setType(Material.AIR);
        }
        if(e.getBlock().getType() == Material.IRON_BLOCK) {
            e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemBuilder(Material.IRON_BLOCK).setName("§fEisenblock").toItemStack());
            e.getBlock().setType(Material.AIR);
        }
		if(e.getBlock().getType() == Material.GLOWSTONE) {
			e.getBlock().setType(Material.AIR);
		}

		return;

	}

	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if(BedWars.getInstance().getStatus() != Status.INGAME) {
			e.setCancelled(true);
			return;
		}
		placedBlocks.add(e.getBlock());
		BedWars.getInstance().getStatsOfTheGame().addBlocks(e.getPlayer());
		Location loc = BedWars.getInstance().getPlayerUtils().getGetTeamByPlayer(e.getPlayer()).getLoc();
		if(loc == e.getBlock().getLocation()) {
			e.setCancelled(true);
			return;
		}
		if(loc.clone().add(0,1,0) == e.getBlock().getLocation()) {
			e.setCancelled(true);
			return;
		}
		if(loc.clone().add(0,2,0) == e.getBlock().getLocation()) {
			e.setCancelled(true);
			return;
		}
	}

	

}
