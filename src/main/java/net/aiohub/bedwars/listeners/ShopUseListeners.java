package net.aiohub.bedwars.listeners;

import net.aiohub.bedwars.BedWars;
import net.aiohub.bedwars.utils.Status;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class ShopUseListeners implements Listener{

    public static HashMap<Player, ShopEnum> shop = new HashMap<>();

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(e.getItem() == null) {
            return;
        }
        Player player = e.getPlayer();
        if(e.getItem().getTypeId() == 383 && BedWars.getInstance().getStatus() == Status.LOBBY) {
            if(shop.get(player) == ShopEnum.ALT) {
                shop.put(player, ShopEnum.NEU);
                ItemStack itemStack = e.getItem();
                ItemMeta meta = itemStack.getItemMeta();
                meta.setDisplayName("§7Klassicher Shop §8| §eSchneller Shop");
                itemStack.setItemMeta(meta);
                e.getItem().setItemMeta(meta);
                return;
            }
            shop.put(player, ShopEnum.ALT);
            ItemStack itemStack = e.getItem();
            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName("§eKlassicher Shop §8| §7Schneller Shop");
            itemStack.setItemMeta(meta);
            e.getItem().setItemMeta(meta);
            return;
        }
    }

    public enum ShopEnum {
        ALT, NEU;
    }



}
