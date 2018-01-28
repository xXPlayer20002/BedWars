package net.aiohub.bedwars.shop;

import net.aiohub.bedwars.utils.ItemBuilder;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;

@Getter
public class ShopItem {

    private String name;
    private ItemStack item;
    private ItemStack neededItems;
    private int amount;

    public ShopItem(String name, ItemStack item, String itemName, int amount) {
        this.name = name;
        this.item = item;
        if(itemName.contains("Bronze")) {
            this.neededItems = new ItemBuilder(Material.CLAY_BRICK, amount).setName("§cBronze").toItemStack();
        }else  if(itemName.contains("Eisen")) {
            this.neededItems = new ItemBuilder(Material.IRON_INGOT, amount).setName("§7Eisen").toItemStack();
        }else {
           this.neededItems = new ItemBuilder(Material.GOLD_INGOT, amount).setName("§6Gold").toItemStack();
        }
        this.amount = amount;

    }

    public void setAmount(int amount) {
       this.amount = amount;
        item.setAmount(amount);
    }

    public void setNeededItems(int neededItems) {
        getNeededItems().setAmount(neededItems);
    }

}

