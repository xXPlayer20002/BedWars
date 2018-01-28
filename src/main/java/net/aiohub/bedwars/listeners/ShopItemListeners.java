package net.aiohub.bedwars.listeners;

import net.aiohub.bedwars.BedWars;
import net.aiohub.bedwars.utils.Status;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class ShopItemListeners implements Listener{

    @EventHandler
    public void onTeamChest(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if(e.getClickedBlock() == null) {
            return;
        }
        if(BedWars.getInstance().getStatus() == Status.INGAME) {
            if (e.getClickedBlock().getType() == Material.ENDER_CHEST) {
                e.setCancelled(true);
                Inventory inv = Bukkit.createInventory(null, 27, "§aTeamchest");
                ItemStack[] stack = BedWars.getInstance().getPlayerUtils().getGetTeamByPlayer(player).getItemsByTeam();
                if (stack != null)
                    inv.setContents(stack);
                player.openInventory(inv);
                return;
            }
        }
    }

    @EventHandler
    public void onTeamChestClose(InventoryCloseEvent e) {
        if (e.getInventory() == null) {
            return;
        }
        if (e.getInventory().getName() == null) {
            return;
        }
        if (BedWars.getInstance().getStatus() == Status.INGAME) {
            if (e.getInventory().getName().contains("§aTeamchest")) {
                if (e.getInventory().getContents() != null)
                    BedWars.getInstance().getPlayerUtils().getGetTeamByPlayer((Player) e.getPlayer()).setItems(e.getInventory().getContents());
            }
        }
    }

    @EventHandler
    public void onPlatform(PlayerInteractEvent e) {
        if(e.getItem() == null) {
            return;
        }
        if(BedWars.getInstance().getStatus() == Status.INGAME) {
            if (e.getItem().getType() == Material.BLAZE_ROD) {
                if (e.getItem().getAmount() > 1) {
                    e.getItem().setAmount(e.getItem().getAmount() - 1);
                } else {
                    e.getPlayer().getInventory().removeItem(e.getItem());
                }
                World world = e.getPlayer().getWorld();
                Block b = world.getBlockAt(e.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation());
                Block b1 = world.getBlockAt(e.getPlayer().getLocation().clone().getBlock().getRelative(BlockFace.DOWN).getLocation().subtract(1D, 0D, 0D));
                Block b2 = world.getBlockAt(e.getPlayer().getLocation().clone().getBlock().getRelative(BlockFace.DOWN).getLocation().add(1D, 0D, 0D));
                Block b3 = world.getBlockAt(e.getPlayer().getLocation().clone().getBlock().getRelative(BlockFace.DOWN).getLocation().subtract(0D, 0D, 1D));
                Block b4 = world.getBlockAt(e.getPlayer().getLocation().clone().getBlock().getRelative(BlockFace.DOWN).getLocation().add(0D, 0D, 1D));
                Block b5 = world.getBlockAt(e.getPlayer().getLocation().clone().getBlock().getRelative(BlockFace.DOWN).getLocation().subtract(1D, 0D, 0D).add(0D, 0D, 1D));
                Block b6 = world.getBlockAt(e.getPlayer().getLocation().clone().getBlock().getRelative(BlockFace.DOWN).getLocation().add(1D, 0D, 0D).subtract(0D, 0D, 1D));
                Block b7 = world.getBlockAt(e.getPlayer().getLocation().clone().getBlock().getRelative(BlockFace.DOWN).getLocation().subtract(1D, 0D, 0D).subtract(0D, 0D, 1D));
                Block b8 = world.getBlockAt(e.getPlayer().getLocation().clone().getBlock().getRelative(BlockFace.DOWN).getLocation().add(1D, 0D, 0D).add(0D, 0D, 1D));
                Block b9 = world.getBlockAt(e.getPlayer().getLocation().clone().getBlock().getRelative(BlockFace.DOWN).getLocation().subtract(2D, 0D, 0D));
                Block b10 = world.getBlockAt(e.getPlayer().getLocation().clone().getBlock().getRelative(BlockFace.DOWN).getLocation().add(2D, 0D, 0D));
                Block b11 = world.getBlockAt(e.getPlayer().getLocation().clone().getBlock().getRelative(BlockFace.DOWN).getLocation().subtract(0D, 0D, 2D));
                Block b12 = world.getBlockAt(e.getPlayer().getLocation().clone().getBlock().getRelative(BlockFace.DOWN).getLocation().add(0D, 0D, 2D));
                ArrayList<Block> blocks = new ArrayList<>();
                blocks.add(b);
                blocks.add(b1);
                blocks.add(b2);
                blocks.add(b3);
                blocks.add(b4);
                blocks.add(b5);
                blocks.add(b6);
                blocks.add(b7);
                blocks.add(b8);
                blocks.add(b9);
                blocks.add(b10);
                blocks.add(b11);
                blocks.add(b12);
                MaterialData m = new MaterialData(Material.STAINED_GLASS, BedWars.getInstance().getPlayerUtils().getGetTeamByPlayer(e.getPlayer()).getColorData());
                blocks.forEach(block -> {
                    if (block.getType() == Material.AIR) {
                        block.setType(m.getItemType());
                        block.setData(m.getData());
                        CancelListener.placedBlocks.add(block);
                    }

                });
                return;
            }
        }
    }

    @EventHandler
    public void onTeleport(PlayerInteractEvent e) {
        if(e.getItem() == null) {
            return;
        }
        if(BedWars.getInstance().getStatus() == Status.INGAME) {
            if (e.getItem().getType() == Material.FIREWORK) {
                e.setCancelled(true);
                BedWars.getInstance().getGameUtils().setBaseTeleportCountdown(e.getPlayer(), e.getItem());
            }
        }
    }
    //TODO: LOOK FOR OTHER BUGS
    @EventHandler
    public void onConsume(PlayerItemConsumeEvent e) {
        if(e.getItem().getDurability() == 14) {
            e.setCancelled(true);
            if(e.getItem().getAmount() > 1) {
                e.getItem().setAmount(e.getItem().getAmount()-1);
            }else{
                e.getPlayer().getInventory().removeItem(e.getItem());
            }
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 301, 1));
        }
    }

    @EventHandler
    public void onMobilerShop(PlayerInteractEvent e) {
        if(e.getItem() == null) {
            return;
        }
        if(e.getClickedBlock() == null) {
            return;
        }
        if(BedWars.getInstance().getStatus() == Status.INGAME) {
            if (e.getItem().getType() == Material.ARMOR_STAND) {
                e.setCancelled(true);
                Location loc = e.getClickedBlock().getLocation();
                loc.add(0,1,0);
                Vector dirBetweenLocations = e.getPlayer().getLocation().toVector().subtract(loc.toVector());
                loc.setDirection(dirBetweenLocations);
                BedWars.getInstance().getGameUtils().setVillager(loc);
                if(e.getItem().getAmount() > 1) {
                    e.getItem().setAmount(e.getItem().getAmount()-1);
                }else{
                    e.getPlayer().getInventory().removeItem(e.getItem());
                }
            }
        }
    }

    @EventHandler
    public void onTnt(BlockPlaceEvent e) {
        if(BedWars.getInstance().getStatus() == Status.INGAME) {
            if (e.getBlock().getType() == Material.TNT) {
                TNTPrimed tntPrimed = e.getBlock().getWorld().spawn(e.getBlock().getLocation(), TNTPrimed.class);
                tntPrimed.setFuseTicks(80);
                e.setCancelled(true);
                if(e.getItemInHand().getAmount() > 1) {
                    e.getItemInHand().setAmount(e.getItemInHand().getAmount()-1);
                }else{
                    e.getPlayer().getInventory().removeItem(e.getItemInHand());
                }
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if(BedWars.getInstance().getGameUtils().getIsPlayerMoving().contains(e.getPlayer())) {
            if(e.getTo().getBlockX() != e.getFrom().getBlockX() || e.getTo().getBlockZ() != e.getFrom().getBlockZ()) {
                e.getPlayer().sendMessage("§cDu hast dich bewegt. Teleport abgebrochen.");
                BedWars.getInstance().getGameUtils().getIsPlayerMoving().remove(e.getPlayer());
                return;
            }
        }
    }

}
