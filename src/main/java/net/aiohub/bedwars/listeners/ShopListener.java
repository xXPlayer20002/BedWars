package net.aiohub.bedwars.listeners;

import net.aiohub.bedwars.BedWars;
import net.aiohub.bedwars.shop.PaketUtils;
import net.aiohub.bedwars.shop.PaketUtilsPlayer;
import net.aiohub.bedwars.shop.ShopCategory;
import net.aiohub.bedwars.shop.ShopItem;
import net.aiohub.bedwars.shop.ShopUtilsByPlayer;
import net.aiohub.bedwars.utils.ItemBuilder;
import net.aiohub.bedwars.utils.Status;
import net.minecraft.server.v1_8_R3.BlockBeacon;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShopListener implements Listener {

    private static HashMap<Player, ShopUtilsByPlayer> getShop = new HashMap<>();

    @EventHandler
    public void onInteractAtVillager(PlayerInteractEntityEvent e) {
        if (e.getRightClicked() == null) {
            return;
        }
        if (e.getRightClicked().getType() == EntityType.VILLAGER) {
            e.setCancelled(true);
        }
        if (BedWars.getInstance().getStatus() == Status.INGAME) {
            if (e.getRightClicked().getType() == EntityType.VILLAGER) {
                Player player = e.getPlayer();
                if(getShop.containsKey(player)) {
                    getShop.get(player).openShop(ShopCategory.STANDART, player);
                    return;
                }
                return;

            }
        }
    }


    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (!getShop.containsKey(player)) {
            return;
        }
        if (e.getInventory().getName().contains("§bBedWars Shop")) {

            e.setCancelled(true);
            if (e.getCurrentItem() == null) {
                return;
            }
            if (e.getCurrentItem().getItemMeta() == null) {
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName() == null) {
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§aBlöcke")) {
                if (this.getShop.get(player).getShopByPlayer(player) == ShopCategory.BLÖCKE) {
                    return;
                }
                getShop.get(player).openShop(ShopCategory.BLÖCKE, player);
                return;
            }

            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§aRüstung")) {
                if (this.getShop.get(player).getShopByPlayer(player) == ShopCategory.RÜSTUNG) {
                    return;
                }
                getShop.get(player).openShop(ShopCategory.RÜSTUNG, player);
                return;
            }

            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§aWerkzeuge")) {
                if (this.getShop.get(player).getShopByPlayer(player) == ShopCategory.WERKZEUGE) {
                    return;
                }
                getShop.get(player).openShop(ShopCategory.WERKZEUGE, player);
                return;
            }

            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§aWaffen")) {
                if (this.getShop.get(player).getShopByPlayer(player) == ShopCategory.WAFFEN) {
                    return;
                }
                getShop.get(player).openShop(ShopCategory.WAFFEN, player);
                return;
            }

            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§aBögen")) {
                if (this.getShop.get(player).getShopByPlayer(player) == ShopCategory.BÖGEN) {
                    return;
                }
                getShop.get(player).openShop(ShopCategory.BÖGEN, player);
                return;
            }

            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§aNahrung")) {
                if (this.getShop.get(player).getShopByPlayer(player) == ShopCategory.NAHRUNG) {
                    return;
                }
                getShop.get(player).openShop(ShopCategory.NAHRUNG, player);
                return;
            }

            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§aTränke")) {
                if (this.getShop.get(player).getShopByPlayer(player) == ShopCategory.TRÄNKE) {
                    return;
                }
                getShop.get(player).openShop(ShopCategory.TRÄNKE, player);
                return;
            }

            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§aPakete")) {
                if (this.getShop.get(player).getShopByPlayer(player) == ShopCategory.PAKETE) {
                    return;
                }
                getShop.get(player).openShop(ShopCategory.PAKETE, player);
                return;
            }

            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§aSpecial")) {
                if (this.getShop.get(player).getShopByPlayer(player) == ShopCategory.SPECIALS) {
                    return;
                }
                getShop.get(player).openShop(ShopCategory.SPECIALS, player);
                return;
            }

            ItemStack newItemStack = e.getCurrentItem().clone();
            ShopItem shopItem = this.getShop.get(player).getItemByShop(newItemStack);
            if(player.getInventory().firstEmpty() == -1) {
                player.sendMessage("§cDein Inventar ist voll.");
                player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,10);
                return;
            }
            if(e.getCurrentItem().getType() == Material.WORKBENCH) {
                PaketUtilsPlayer paketUtilsPlayer = PaketListeners.paketUtils.get(player);
                List list = paketUtilsPlayer.getList((e.getSlot()-9)+1);
                List<ItemStack> stack = new ArrayList<>();
                if (!list.isEmpty()) {
                    Integer preis = 0;
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i) != null && !list.get(i).toString().contains("List")) {
                            String array = list.get(i).toString();
                            String[] array3 = array.split(",");
                            ItemStack itemStack = new ItemBuilder(new ItemStack(Integer.valueOf(array3[0]), Integer.valueOf(array3[1]), Short.valueOf(array3[2]))).setName(array3[4]).toItemStack();
                            itemStack = BedWars.getInstance().getPaketUtils().getCostByItem(itemStack).getItem();
                            itemStack.getItemMeta().setLore(null);
                            itemStack.setItemMeta(itemStack.getItemMeta());
                            stack.add(itemStack);
                            preis = preis + Integer.valueOf(array3[3]);
                        }
                    }
                    int fullAmount = player.getLevel();
                    if(preis > fullAmount) {
                        player.sendMessage("§cDu hast nicht genug AIOs");
                        player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,10);
                        return;
                    }
                    for(ItemStack stack1 : stack) {
                        if(stack1.getType() == Material.STAINED_CLAY) {
                            stack1.setDurability(BedWars.getInstance().getPlayerUtils().getGetTeamByPlayer(player).getColorData());
                        }else if(stack1.getType() == Material.LEATHER_BOOTS || stack1.getType() == Material.LEATHER_CHESTPLATE ||
                                stack1.getType() == Material.LEATHER_LEGGINGS ||stack1.getType() == Material.LEATHER_BOOTS) {
                            ShopUtilsByPlayer shopUtilsByPlayer = new ShopUtilsByPlayer();
                            shopUtilsByPlayer.setArmorColor(stack1, BedWars.getInstance().getPlayerUtils().getGetTeamByPlayer(player));
                        }

                        player.getInventory().addItem(stack1);
                    }
                    player.setLevel(player.getLevel()-preis);
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1,10);
                    return;
                }
                player.sendMessage("§cDieses Paket ist Leer!");
                player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,10);
            }
            if (e.isShiftClick()) {

                int fullAmount = player.getLevel();

                if (fullAmount == 0) {
                    player.sendMessage("§cDu hast nicht genug AIOs");
                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,10);
                    return;
                }
                int newAmount = fullAmount/shopItem.getAmount();
                fullAmount = newAmount * shopItem.getAmount();
                newAmount = newAmount * shopItem.getItem().getAmount();
                player.sendMessage(newAmount +"");
                if(newAmount > 64) {
                    newAmount = 64;
                    if(shopItem.getItem().getAmount() >1) {
                    fullAmount = (newAmount/shopItem.getItem().getAmount())*shopItem.getAmount();
                    }else
                    fullAmount = newAmount*shopItem.getAmount();
                }


                if (player.getLevel() <fullAmount) {
                    player.sendMessage("§cDu hast nicht genug AIOs");
                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,10);
                    return;
                }
                //removeItem(player, shopItem.getNeededItems(), 64);
                ItemMeta meta = newItemStack.getItemMeta();
                meta.setLore(null);
                newItemStack.setItemMeta(meta);
                newItemStack.setAmount(newAmount);
                player.getInventory().addItem(newItemStack);
                player.setLevel(player.getLevel()-fullAmount);
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 1,10);
                return;
            } else {
                int amount = shopItem.getAmount();
                shopItem.setNeededItems(amount);
                if (player.getLevel() <amount) {
                    player.sendMessage("§cDu hast nicht genug AIOs");
                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1,10);
                    return;
                }
                //removeItem(player, shopItem.getNeededItems(), 64);
                ItemMeta meta = newItemStack.getItemMeta();
                meta.setLore(null);
                newItemStack.setItemMeta(meta);
                newItemStack.setAmount(shopItem.getItem().getAmount());
                player.getInventory().addItem(newItemStack);
                player.setLevel(player.getLevel()-amount);
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 1,10);
                return;
            }
        }

    }


    public static void addShopType1(Player player) {
        getShop.put(player, new ShopUtilsByPlayer(player));
    }


}
