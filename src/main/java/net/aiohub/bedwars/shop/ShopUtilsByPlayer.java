package net.aiohub.bedwars.shop;

import net.aiohub.bedwars.BedWars;
import net.aiohub.bedwars.listeners.PaketListeners;
import net.aiohub.bedwars.utils.ItemBuilder;
import net.aiohub.bedwars.utils.Team;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import lombok.Getter;

public class ShopUtilsByPlayer{

    private HashMap<ShopCategory, ItemStack[]> shopItems = new HashMap<>();
    private HashMap<Player, ShopCategory> playerCategory = new HashMap<>();
    private HashMap<ItemStack, ShopItem> getItemByShop = new HashMap<>();
    private Player player;
    public ShopUtilsByPlayer(Player player) {
        this.player = player;
        getColorByString.put("§0", "0;0;0");
        getColorByString.put("§1", "0;0;170");
        getColorByString.put("§2", "0;170;0");
        getColorByString.put("§3", "0;170;170");
        getColorByString.put("§4", "170;0;0");
        getColorByString.put("§5", "170;0;170");
        getColorByString.put("§6", "255;170;0");
        getColorByString.put("§7", "170;170;170");
        getColorByString.put("§8", "85;85;85");
        getColorByString.put("§9", "85;85;85");
        getColorByString.put("§a", "85;255;5");
        getColorByString.put("§b", "85;255;255");
        getColorByString.put("§c", "255;85;85");
        getColorByString.put("§d", "255;85;255");
        getColorByString.put("§e", "255;255;85");
        getColorByString.put("§f", "255;255;255");
        setNormal();
        setArmor();
        setBlocks();
        setTools();
        setWeapons();
        setBows();
        setEatStuff();
        setPotions();
        setPakete();
        setSpecials();

    }

    public ShopUtilsByPlayer() {
        getColorByString.put("§0", "0;0;0");
        getColorByString.put("§1", "0;0;170");
        getColorByString.put("§2", "0;170;0");
        getColorByString.put("§3", "0;170;170");
        getColorByString.put("§4", "170;0;0");
        getColorByString.put("§5", "170;0;170");
        getColorByString.put("§6", "255;170;0");
        getColorByString.put("§7", "170;170;170");
        getColorByString.put("§8", "85;85;85");
        getColorByString.put("§9", "85;85;85");
        getColorByString.put("§a", "85;255;5");
        getColorByString.put("§b", "85;255;255");
        getColorByString.put("§c", "255;85;85");
        getColorByString.put("§d", "255;85;255");
        getColorByString.put("§e", "255;255;85");
        getColorByString.put("§f", "255;255;255");
    }


    private void setBlocks() {
        ItemStack[] items2 = getShopItems(ShopCategory.STANDART);
        Inventory items = Bukkit.createInventory(null, 18, "§bBedWars Shop");
        items.setContents(items2);
        if(BedWars.getInstance().getPlayerUtils().getGetTeamByPlayer(player) == null) {
            items.setItem(11, new ItemBuilder(Material.STAINED_CLAY, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.Bloecke")).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.Bloecke")).setLore(Arrays.asList("§6" + BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Bloecke") + " AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Amount.Bloecke") + "")).toItemStack());
        }else {
            items.setItem(11, new ItemBuilder(Material.STAINED_CLAY, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.Bloecke"), BedWars.getInstance().getPlayerUtils().getGetTeamByPlayer(player).getColorData()).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.Bloecke")).setLore(Arrays.asList("§6" + BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Bloecke") + " AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Amount.Bloecke") + "")).toItemStack());
        }
        items.setItem(12, new ItemBuilder(Material.GLASS, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.Glas")).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.Glas")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Glas")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Glas")+"")).toItemStack());
        items.setItem(13, new ItemBuilder(Material.GLOWSTONE, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.Glowstone")).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.Glowstone")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Glowstone")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Glowstone")+"")).toItemStack());
        items.setItem(14, new ItemBuilder(Material.ENDER_STONE, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.Endstein")).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.Endstein")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Endstein")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Endstein")+"")).toItemStack());
        items.setItem(15, new ItemBuilder(Material.IRON_BLOCK, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.Eisenblock")).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.Eisenblock")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Eisenblock")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Eisenblock")+"")).toItemStack());
        items.setItem(16, new ItemBuilder(Material.CHEST, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.Kiste")).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.Kiste")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Kiste")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Kiste")+"")).toItemStack());
        items.setItem(17, new ItemBuilder(Material.ENDER_CHEST, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.Teamkiste")).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.Teamkiste")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Teamkiste")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Teamkiste")+"")).toItemStack());
        IntStream.range(9,18).forEach(i -> {
            ItemStack itemStack = items.getItem(i);
            if(itemStack != null) {

                ShopItem item = new ShopItem(itemStack.getItemMeta().getDisplayName(), itemStack, itemStack.getItemMeta().getLore().get(0), Integer.valueOf(itemStack.getItemMeta().getLore().get(1)));
                List<String> lore = itemStack.getItemMeta().getLore();
                lore.remove(1);
                String name = itemStack.getItemMeta().getDisplayName();
                Map<Enchantment, Integer> enchants = itemStack.getEnchantments();
                getItemByShop.put(new ItemBuilder(item.getItem()).setName(name).setLore(lore).addEnchantments(enchants).toItemStack(), item);
            }
        });
        shopItems.put(ShopCategory.BLÖCKE, items.getContents());
    }

    private void setArmor() {
        ItemStack[] items2 = getShopItems(ShopCategory.STANDART);
        Inventory items = Bukkit.createInventory(null, 18, "§bBedWars Shop");
        items.setContents(items2);
        {
            ItemStack stack = new ItemBuilder(Material.LEATHER_HELMET, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.Helm")).addUnsafeEnchantment(Enchantment.DURABILITY, 1).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.Helm")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Helm")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Helm")+"")).toItemStack();
            LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
            if (meta.getColor() != null) {
                this.setArmorColor(stack, BedWars.getInstance()
                        .getPlayerUtils().getGetTeamByPlayer(player));

            }
            items.setItem(9, stack);
        }

        {
            ItemStack stack = new ItemBuilder(Material.LEATHER_CHESTPLATE, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.BrustplatteLeder")).addUnsafeEnchantment(Enchantment.DURABILITY, 1).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.BrustplatteLeder")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Price.BrustplatteLeder")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.BrustplatteLeder")+"")).toItemStack();
            LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
            if (meta.getColor() != null) {
                this.setArmorColor(stack, BedWars.getInstance()
                        .getPlayerUtils().getGetTeamByPlayer(player));

            }
            items.setItem(10, stack);
        }

        {
            ItemStack stack = new ItemBuilder(Material.LEATHER_LEGGINGS, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.Hose")).addUnsafeEnchantment(Enchantment.DURABILITY, 1).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.Hose")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Hose")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Hose")+"")).toItemStack();
            LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
            if (meta.getColor() != null) {
                this.setArmorColor(stack, BedWars.getInstance()
                        .getPlayerUtils().getGetTeamByPlayer(player));

            }
            items.setItem(11, stack);
        }
        {
            ItemStack stack = new ItemBuilder(Material.LEATHER_BOOTS, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.Schuhe")).addUnsafeEnchantment(Enchantment.DURABILITY, 1).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.Schuhe")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Schuhe")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Schuhe")+"")).toItemStack();
            LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
            if (meta.getColor() != null) {
                this.setArmorColor(stack, BedWars.getInstance()
                        .getPlayerUtils().getGetTeamByPlayer(player));

            }
            items.setItem(12, stack);
        }



        items.setItem(15, new ItemBuilder(Material.CHAINMAIL_CHESTPLATE, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.BrustplatteEisenI")).addUnsafeEnchantment(Enchantment.DURABILITY, 1).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.BrustplatteEisenI")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.BrustplatteEisenI")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.BrustplatteEisenI")+"")).toItemStack());
        items.setItem(16, new ItemBuilder(Material.CHAINMAIL_CHESTPLATE, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.BrustplatteEisenII")).addUnsafeEnchantment(Enchantment.DURABILITY, 1).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.BrustplatteEisenII")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.BrustplatteEisenII")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.BrustplatteEisenII")+"")).toItemStack());
        items.setItem(17, new ItemBuilder(Material.CHAINMAIL_CHESTPLATE, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.BrustplatteEisenIII")).addUnsafeEnchantment(Enchantment.DURABILITY, 1).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.BrustplatteEisenIII")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.BrustplatteEisenIII")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.BrustplatteEisenIII")+"")).toItemStack());
        IntStream.range(9,18).forEach(i -> {
            ItemStack itemStack = items.getItem(i);
            if(itemStack != null) {

                ShopItem item = new ShopItem(itemStack.getItemMeta().getDisplayName(), itemStack, itemStack.getItemMeta().getLore().get(0), Integer.valueOf(itemStack.getItemMeta().getLore().get(1)));
                List<String> lore = itemStack.getItemMeta().getLore();
                lore.remove(1);
                String name = itemStack.getItemMeta().getDisplayName();
                Map<Enchantment, Integer> enchants = itemStack.getEnchantments();
                getItemByShop.put(new ItemBuilder(item.getItem()).setName(name).setLore(lore).addEnchantments(enchants).toItemStack(), item);
            }
        });
        shopItems.put(ShopCategory.RÜSTUNG, items.getContents());
    }

    private void setTools() {
        ItemStack[] items2 = getShopItems(ShopCategory.STANDART);
        Inventory items = Bukkit.createInventory(null, 18, "§bBedWars Shop");
        items.setContents(items2);
        items.setItem(10, new ItemBuilder(Material.WOOD_PICKAXE, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.Holzspitzhacke")).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.Holzspitzhacke")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Holzspitzhacke")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Holzspitzhacke")+"")).addEnchant(Enchantment.DURABILITY,1).addEnchant(Enchantment.DIG_SPEED,1).toItemStack());
        items.setItem(11, new ItemBuilder(Material.STONE_PICKAXE, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.Steinspitzhacke")).addEnchant(Enchantment.DURABILITY,1).addEnchant(Enchantment.DIG_SPEED,1).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.Steinspitzhacke")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Steinspitzhacke")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Steinspitzhacke")+"")).toItemStack());
        items.setItem(12, new ItemBuilder(Material.IRON_PICKAXE, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.Eisenspitzhacke")).addEnchant(Enchantment.DURABILITY,3).addEnchant(Enchantment.DIG_SPEED,1).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.Eisenspitzhacke")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Eisenspitzhacke")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Eisenspitzhacke")+"")).toItemStack());
        items.setItem(14, new ItemBuilder(Material.FISHING_ROD, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.Angel")).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.Angel")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Angel")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Angel")+"")).toItemStack());
        items.setItem(15, new ItemBuilder(Material.FLINT_AND_STEEL, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.Feuerzeug")).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.Feuerzeug")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Feuerzeug")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Feuerzeug")+"")).toItemStack());
        IntStream.range(9,18).forEach(i -> {
            ItemStack itemStack = items.getItem(i);
            if(itemStack != null) {

                ShopItem item = new ShopItem(itemStack.getItemMeta().getDisplayName(), itemStack, itemStack.getItemMeta().getLore().get(0), Integer.valueOf(itemStack.getItemMeta().getLore().get(1)));
                List<String> lore = itemStack.getItemMeta().getLore();
                lore.remove(1);
                String name = itemStack.getItemMeta().getDisplayName();
                Map<Enchantment, Integer> enchants = itemStack.getEnchantments();
                getItemByShop.put(new ItemBuilder(item.getItem()).setName(name).setLore(lore).addUnsafeEnchantments(enchants).toItemStack(), item);
            }
        });
        shopItems.put(ShopCategory.WERKZEUGE, items.getContents());
    }

    private void setWeapons() {
        ItemStack[] items2 = getShopItems(ShopCategory.STANDART);
        Inventory items = Bukkit.createInventory(null, 18, "§bBedWars Shop");
        items.setContents(items2);
        items.setItem(11, new ItemBuilder(Material.STICK, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.Stick")).addUnsafeEnchantment(Enchantment.KNOCKBACK,1).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.Stick")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Stick")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Stick")+"")).toItemStack());
        items.setItem(13, new ItemBuilder(Material.GOLD_SWORD, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.GoldschwertI")).addUnsafeEnchantment(Enchantment.DAMAGE_ALL,1).addUnsafeEnchantment(Enchantment.DURABILITY,1).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.GoldschwertI")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.GoldschwertI")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.GoldschwertI")+"")).toItemStack());
        items.setItem(14, new ItemBuilder(Material.GOLD_SWORD, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.GoldschwertII")).addUnsafeEnchantment(Enchantment.DURABILITY,1).addUnsafeEnchantment(Enchantment.DAMAGE_ALL,2).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.GoldschwertII")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.GoldschwertII")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.GoldschwertII")+"")).toItemStack());
        items.setItem(15, new ItemBuilder(Material.IRON_SWORD, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.Eisenschwert")).addUnsafeEnchantment(Enchantment.DURABILITY,1).addUnsafeEnchantment(Enchantment.KNOCKBACK, 1).addUnsafeEnchantment(Enchantment.DAMAGE_ALL,2).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.Eisenschwert")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Eisenschwert")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Eisenschwert")+"")).toItemStack());
        IntStream.range(9,18).forEach(i -> {
            ItemStack itemStack = items.getItem(i);
            if(itemStack != null) {

                ShopItem item = new ShopItem(itemStack.getItemMeta().getDisplayName(), itemStack, itemStack.getItemMeta().getLore().get(0), Integer.valueOf(itemStack.getItemMeta().getLore().get(1)));
                List<String> lore = itemStack.getItemMeta().getLore();
                lore.remove(1);
                String name = itemStack.getItemMeta().getDisplayName();
                Map<Enchantment, Integer> enchants = itemStack.getEnchantments();
                getItemByShop.put(new ItemBuilder(item.getItem()).setName(name).setLore(lore).addUnsafeEnchantments(enchants).toItemStack(), item);
            }
        });
        shopItems.put(ShopCategory.WAFFEN, items.getContents());
    }

    private void setBows() {
        ItemStack[] items2 = getShopItems(ShopCategory.STANDART);
        Inventory items = Bukkit.createInventory(null, 18, "§bBedWars Shop");
        items.setContents(items2);
        items.setItem(10, new ItemBuilder(Material.BOW, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.BogenI")).setDurability((short) 260).addUnsafeEnchantment(Enchantment.ARROW_INFINITE,1).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.BogenI")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.BogenI")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.BogenI")+"")).toItemStack());
        items.setItem(12, new ItemBuilder(Material.BOW, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.BogenII")).setDurability((short) 260).addUnsafeEnchantment(Enchantment.ARROW_INFINITE,1).addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.BogenII")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.BogenII")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.BogenII")+"")).toItemStack());
        items.setItem(14, new ItemBuilder(Material.BOW, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.BogenIII")).setDurability((short) 260).addUnsafeEnchantment(Enchantment.ARROW_INFINITE,1).addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 2).addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 1).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.BogenIII")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.BogenIII")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.BogenIII")+"")).toItemStack());
        items.setItem(16, new ItemBuilder(Material.ARROW, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.Pfeil")).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.Pfeil")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Pfeil")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Pfeil")+"")).toItemStack());
        IntStream.range(9,18).forEach(i -> {
            ItemStack itemStack = items.getItem(i);
            if(itemStack != null) {

                ShopItem item = new ShopItem(itemStack.getItemMeta().getDisplayName(), itemStack, itemStack.getItemMeta().getLore().get(0), Integer.valueOf(itemStack.getItemMeta().getLore().get(1)));
                List<String> lore = itemStack.getItemMeta().getLore();
                lore.remove(1);
                String name = itemStack.getItemMeta().getDisplayName();
                Map<Enchantment, Integer> enchants = itemStack.getEnchantments();
                getItemByShop.put(new ItemBuilder(item.getItem()).setName(name).setLore(lore).addUnsafeEnchantments(enchants).toItemStack(), item);
            }
        });
        shopItems.put(ShopCategory.BÖGEN, items.getContents());
    }

    private void setEatStuff() {
        ItemStack[] items2 = getShopItems(ShopCategory.STANDART);
        Inventory items = Bukkit.createInventory(null, 18, "§bBedWars Shop");
        items.setContents(items2);
        items.setItem(11, new ItemBuilder(Material.APPLE, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.Apfel")).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.Apfel")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Apfel")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Apfel")+"")).toItemStack());
        items.setItem(12, new ItemBuilder(Material.COOKED_BEEF, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.Steak")).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.Steak")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Steak")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Steak")+"")).toItemStack());
        items.setItem(10, new ItemBuilder(Material.COOKIE, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.Kekse")).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.Kekse")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Kekse")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Kekse")+"")).toItemStack());
        items.setItem(15, new ItemBuilder(Material.CAKE, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.Kuchen")).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.Kuchen")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Kuchen")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Kuchen")+"")).toItemStack());
        items.setItem(16, new ItemBuilder(Material.GOLDEN_APPLE, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.GoldApfel")).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.GoldApfel")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.GoldApfel")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.GoldApfel")+"")).toItemStack());
        IntStream.range(9,18).forEach(i -> {
            ItemStack itemStack = items.getItem(i);
            if(itemStack != null) {

                ShopItem item = new ShopItem(itemStack.getItemMeta().getDisplayName(), itemStack, itemStack.getItemMeta().getLore().get(0), Integer.valueOf(itemStack.getItemMeta().getLore().get(1)));
                List<String> lore = itemStack.getItemMeta().getLore();
                lore.remove(1);
                String name = itemStack.getItemMeta().getDisplayName();
                Map<Enchantment, Integer> enchants = itemStack.getEnchantments();
                getItemByShop.put(new ItemBuilder(item.getItem()).setName(name).setLore(lore).addUnsafeEnchantments(enchants).toItemStack(), item);
            }
        });
        shopItems.put(ShopCategory.NAHRUNG, items.getContents());
    }

    private void setSpecials() {
        ItemStack[] items2 = getShopItems(ShopCategory.STANDART);
        Inventory items = Bukkit.createInventory(null, 18, "§bBedWars Shop");
        items.setContents(items2);
        items.setItem(9, new ItemBuilder(Material.LADDER, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.Leiter")).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.Leiter")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Leiter")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Leiter")+"")).toItemStack());
        items.setItem(11, new ItemBuilder(Material.FIREWORK, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.BaseTeleporter")).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.BaseTeleporter")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.BaseTeleporter")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.BaseTeleporter")+"")).toItemStack());
        items.setItem(12, new ItemBuilder(Material.ARMOR_STAND, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.MobilerShop")).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.MobilerShop")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.MobilerShop")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.MobilerShop")+"")).toItemStack());
        items.setItem(17, new ItemBuilder(Material.ENDER_PEARL, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.Enderperle")).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.Enderperle")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Enderperle")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Enderperle")+"")).toItemStack());
        items.setItem(10, new ItemBuilder(Material.WEB, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.Cobweb")).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.Cobweb")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Cobweb")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Cobweb")+"")).toItemStack());
        items.setItem(15, new ItemBuilder(Material.TNT, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.Tnt")).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.Tnt")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Tnt")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Tnt")+"")).toItemStack());
        items.setItem(16, new ItemBuilder(Material.BLAZE_ROD, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.Rettungsplattform")).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.Rettungsplattform")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Rettungsplattform")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Rettungsplattform")+"")).toItemStack());

        IntStream.range(9,18).forEach(i -> {
            ItemStack itemStack = items.getItem(i);
            if(itemStack != null) {

                ShopItem item = new ShopItem(itemStack.getItemMeta().getDisplayName(), itemStack, itemStack.getItemMeta().getLore().get(0), Integer.valueOf(itemStack.getItemMeta().getLore().get(1)));
                List<String> lore = itemStack.getItemMeta().getLore();
                lore.remove(1);
                String name = itemStack.getItemMeta().getDisplayName();
                Map<Enchantment, Integer> enchants = itemStack.getEnchantments();
                getItemByShop.put(new ItemBuilder(item.getItem()).setName(name).setLore(lore).addEnchantments(enchants).toItemStack(), item);
            }
        });
        shopItems.put(ShopCategory.SPECIALS, items.getContents());
    }

    private void setPakete() {
        List<ItemStack> stack = new ArrayList<>();
        ItemStack[] items2 = getShopItems(ShopCategory.STANDART);
        Inventory inv = Bukkit.createInventory(null, 18, "§bBedWars Shop");
        inv.setContents(items2);
        PaketUtilsPlayer paketUtilsPlayer = PaketListeners.paketUtils.get(player);
        for(int i2 = 0; i2 < 5; i2++) {


            List list = paketUtilsPlayer.getList(i2+1);
            List<String> firstPaket = new ArrayList<>();
            if (!list.isEmpty()) {
                Integer preis = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i) != null && !list.get(i).toString().contains("List")) {
                        String array = list.get(i).toString();
                        String[] array3 = array.split(",");
                        stack.add(new ItemBuilder(new ItemStack(Integer.valueOf(array3[0]), Integer.valueOf(array3[1]), Short.valueOf(array3[2]))).setName(array3[4]).toItemStack());
                        firstPaket.add("§f" + array3[1] + "§fx §6" + array3[4]);
                        preis = preis + Integer.valueOf(array3[3]);
                    }
                }
                if (!firstPaket.isEmpty()) {
                    firstPaket.add("§3Preis: §e" + String.valueOf(preis) + " §3AIOs");


                    inv.setItem(i2+9, new ItemBuilder(Material.WORKBENCH).setName("§aPaket"+i2).setLore(firstPaket).toItemStack());
                } else {
                    inv.setItem(i2+9, new ItemBuilder(Material.WORKBENCH).setName("§aPaket"+i2).setLore("§cLeer").toItemStack());
                }
            }else{
                inv.setItem(i2+9, new ItemBuilder(Material.WORKBENCH).setName("§aPaket"+i2).setLore("§cLeer").toItemStack());
            }
        }
        shopItems.put(ShopCategory.PAKETE, inv.getContents());
    }

    private void setPotions() {
        ItemStack[] items2 = getShopItems(ShopCategory.STANDART);
        Inventory items = Bukkit.createInventory(null, 18, "§bBedWars Shop");
        items.setContents(items2);
        items.setItem(10, new ItemBuilder(Material.POTION, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.Heilung1"), (byte) 5).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.Heilung1")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Heilung1")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Heilung1")+"")).toItemStack());
        items.setItem(11, new ItemBuilder(Material.POTION, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.Heilung2"), (byte) 37).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.Heilung2")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Heilung2")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Heilung2")+"")).toItemStack());
        items.setItem(12, new ItemBuilder(Material.POTION, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.Geschwindigkeit"), (byte) 2).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.Geschwindigkeit")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Geschwindigkeit")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Geschwindigkeit")+"")).toItemStack());
        items.setItem(13, new ItemBuilder(Material.POTION, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.Sprungkraft"), (byte) 43).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.Sprungkraft")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Sprungkraft")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Sprungkraft")+"")).toItemStack());
        items.setItem(14, new ItemBuilder(Material.POTION, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.Unsichtbarkeit"), (byte) 14).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.Unsichtbarkeit")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Unsichtbarkeit")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Unsichtbarkeit")+"")).toItemStack());
        items.setItem(16, new ItemBuilder(Material.POTION, BedWars.getInstance().getShopItemsByConfig().getItemAmountByConfig("Amount.Staerke"), (byte) 9).setName(BedWars.getInstance().getShopItemsByConfig().getItemNameByConfig("Name.Staerke")).setLore(Arrays.asList("§6"+BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Staerke")+" AIOs", BedWars.getInstance().getShopItemsByConfig().getItemPriceByConfig("Price.Staerke")+"")).toItemStack());
        IntStream.range(9,18).forEach(i -> {
            ItemStack itemStack = items.getItem(i);
            if(itemStack != null) {

                ShopItem item = new ShopItem(itemStack.getItemMeta().getDisplayName(), itemStack, itemStack.getItemMeta().getLore().get(0), Integer.valueOf(itemStack.getItemMeta().getLore().get(1)));
                List<String> lore = itemStack.getItemMeta().getLore();
                lore.remove(1);
                String name = itemStack.getItemMeta().getDisplayName();
                Map<Enchantment, Integer> enchants = itemStack.getEnchantments();
                getItemByShop.put(new ItemBuilder(item.getItem()).setName(name).setLore(lore).addUnsafeEnchantments(enchants).toItemStack(), item);
            }
        });
        shopItems.put(ShopCategory.TRÄNKE, items.getContents());
    }

    private void setNormal() {
        Inventory inv = Bukkit.createInventory(null, 18, "§bBedWars Shop");
        inv.setItem(0, new ItemBuilder(Material.SANDSTONE).setName("§aBlöcke").toItemStack());
        inv.setItem(1, new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).setName("§aRüstung").toItemStack());
        inv.setItem(2, new ItemBuilder(Material.WOOD_PICKAXE).setName("§aWerkzeuge").toItemStack());
        inv.setItem(3, new ItemBuilder(Material.IRON_SWORD).setName("§aWaffen").toItemStack());
        inv.setItem(4, new ItemBuilder(Material.BOW).setName("§aBögen").toItemStack());
        inv.setItem(5, new ItemBuilder(Material.COOKED_BEEF).setName("§aNahrung").toItemStack());
        inv.setItem(6, new ItemBuilder(Material.POTION).setName("§aTränke").toItemStack());
        inv.setItem(7, new ItemBuilder(Material.WORKBENCH).setName("§aPakete").toItemStack());
        inv.setItem(8, new ItemBuilder(Material.EMERALD).setName("§aSpecial").toItemStack());
        shopItems.put(ShopCategory.STANDART, inv.getContents());
    }

    public ItemStack setArmorColor(ItemStack stack, Team team) {
        LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
        meta.setColor(parseColor(team.getPrefix()));
        stack.setItemMeta(meta);
        return stack;
    }
    private HashMap<String, String> getColorByString = new HashMap<>();
    private Color parseColor(String s1) {

        Color color = null;
        String s = getColorByString.get(s1);
        String[] split = s.split(";");
        if (split.length > 2) {
            try {
                // RGB
                int red = Integer.parseInt(split[0]);
                int green = Integer.parseInt(split[1]);
                int blue = Integer.parseInt(split[2]);
                color = Color.fromRGB(red, green, blue);

            } catch (NumberFormatException e) {
                // Name
                Field[] fields = Color.class.getFields();
                for (Field field : fields) {
                    if (Modifier.isStatic(field.getModifiers())
                            && field.getType() == Color.class) {

                        if (field.getName().equalsIgnoreCase(s)) {
                            try {
                                return (Color) field.get(null);
                            } catch (IllegalArgumentException e1) {
                                e1.printStackTrace();
                            } catch (IllegalAccessException e1) {
                                e1.printStackTrace();
                            }
                        }

                    }
                }

            }
        } else {
            // Name
            Field[] fields = Color.class.getFields();
            for (Field field : fields) {
                if (Modifier.isStatic(field.getModifiers())
                        && field.getType() == Color.class) {

                    if (field.getName().equalsIgnoreCase(s)) {
                        try {
                            return (Color) field.get(null);
                        } catch (IllegalArgumentException e1) {
                            e1.printStackTrace();
                        } catch (IllegalAccessException e1) {
                            e1.printStackTrace();
                        }
                    }

                }
            }

        }

        return color;
    }



    public void openShop(ShopCategory shopCategory, Player player) {
        if (!shopItems.containsKey(shopCategory)) {
            return;
        }
        Inventory inv = Bukkit.createInventory(null, 18, "§bBedWars Shop");
        inv.setContents(shopItems.get(shopCategory));
        player.openInventory(inv);
        playerCategory.put(player, shopCategory);
    }

    public ItemStack[] getShopItems(ShopCategory shopCategory) {
        if (!shopItems.containsKey(shopCategory)) {
            return null;
        }

      return shopItems.get(shopCategory);
    }

    public ShopCategory getShopByPlayer(Player player) {
        if (!playerCategory.containsKey(player)) {
            return null;
        }

        return playerCategory.get(player);
    }

    public ShopItem getItemByShop(ItemStack stack) {
        if (!getItemByShop.containsKey(stack)) {
            return null;
        }

        return getItemByShop.get(stack);
    }

    public void addShopItem(ItemStack stack, ShopItem shopItem) {
        getItemByShop.put(stack, shopItem);
    }


}
