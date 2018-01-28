package net.aiohub.bedwars.shop;

import net.aiohub.bedwars.BedWars;
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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import lombok.Getter;

public class ShopUtilsByPlayer {

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
        setChests();
        setSpecials();

    }


    private void setBlocks() {
        ItemStack[] items2 = getShopItems(ShopCategory.STANDART);
        Inventory items = Bukkit.createInventory(null, 18, "§bBedWars Shop");
        items.setContents(items2);
        items.setItem(11, new ItemBuilder(Material.STAINED_CLAY, 2, BedWars.getInstance().getPlayerUtils().getGetTeamByPlayer(player).getColorData()).setName("§fSteine").setLore(Arrays.asList("§63 AIOs", "3")).toItemStack());
        items.setItem(12, new ItemBuilder(Material.GLASS, 1).setName("§fGlas").setLore(Arrays.asList("§65 AIOs", "5")).toItemStack());
        items.setItem(13, new ItemBuilder(Material.GLOWSTONE, 1).setName("§fGlowstone").setLore(Arrays.asList("§67 AIOs", "7")).toItemStack());
        items.setItem(14, new ItemBuilder(Material.ENDER_STONE, 1).setName("§fEndstein").setLore(Arrays.asList("§610 AIOs", "10")).toItemStack());
        items.setItem(16, new ItemBuilder(Material.IRON_BLOCK, 1).setName("§fEisenblock").setLore(Arrays.asList("§f30 AIOs", "30")).toItemStack());
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
            ItemStack stack = new ItemBuilder(Material.LEATHER_HELMET, 1).addUnsafeEnchantment(Enchantment.DURABILITY, 1).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setName("§fHelm").setLore(Arrays.asList("§65 AIOs", "5")).toItemStack();
            LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
            if (meta.getColor() != null) {
                this.setArmorColor(stack, BedWars.getInstance()
                        .getPlayerUtils().getGetTeamByPlayer(player));

            }
            items.setItem(9, stack);
        }

        {
            ItemStack stack = new ItemBuilder(Material.LEATHER_CHESTPLATE, 1).addUnsafeEnchantment(Enchantment.DURABILITY, 1).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setName("§fBrustplatte").setLore(Arrays.asList("§67 AIOs", "7")).toItemStack();
            LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
            if (meta.getColor() != null) {
                this.setArmorColor(stack, BedWars.getInstance()
                        .getPlayerUtils().getGetTeamByPlayer(player));

            }
            items.setItem(10, stack);
        }

        {
            ItemStack stack = new ItemBuilder(Material.LEATHER_LEGGINGS, 1).addUnsafeEnchantment(Enchantment.DURABILITY, 1).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setName("§fHose").setLore(Arrays.asList("§66 AIOs", "6")).toItemStack();
            LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
            if (meta.getColor() != null) {
                this.setArmorColor(stack, BedWars.getInstance()
                        .getPlayerUtils().getGetTeamByPlayer(player));

            }
            items.setItem(11, stack);
        }
        {
            ItemStack stack = new ItemBuilder(Material.LEATHER_BOOTS, 1).addUnsafeEnchantment(Enchantment.DURABILITY, 1).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setName("§fStiefel").setLore(Arrays.asList("§65 AIOs", "5")).toItemStack();
            LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
            if (meta.getColor() != null) {
                this.setArmorColor(stack, BedWars.getInstance()
                        .getPlayerUtils().getGetTeamByPlayer(player));

            }
            items.setItem(12, stack);
        }



        items.setItem(15, new ItemBuilder(Material.CHAINMAIL_CHESTPLATE, 1).addUnsafeEnchantment(Enchantment.DURABILITY, 1).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setName("§fBrustplatte I").setLore(Arrays.asList("§650 AIOs", "50")).toItemStack());
        items.setItem(16, new ItemBuilder(Material.CHAINMAIL_CHESTPLATE, 1).addUnsafeEnchantment(Enchantment.DURABILITY, 1).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).setName("§fBrustplatte II").setLore(Arrays.asList("§6125 AIOs", "125")).toItemStack());
        items.setItem(17, new ItemBuilder(Material.CHAINMAIL_CHESTPLATE, 1).addUnsafeEnchantment(Enchantment.DURABILITY, 1).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).setName("§fBrustplatte III").setLore(Arrays.asList("§6250 AIOs", "250")).toItemStack());
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
        items.setItem(10, new ItemBuilder(Material.WOOD_PICKAXE, 1).setName("§fHolzspitzhacke").setLore(Arrays.asList("§620 AIOs", "20")).addEnchant(Enchantment.DURABILITY,1).addEnchant(Enchantment.DIG_SPEED,1).toItemStack());
        items.setItem(11, new ItemBuilder(Material.STONE_PICKAXE, 1).addEnchant(Enchantment.DURABILITY,1).addEnchant(Enchantment.DIG_SPEED,1).setName("§fSteinspitzhacke").setLore(Arrays.asList("§650 AIOs", "50")).toItemStack());
        items.setItem(12, new ItemBuilder(Material.IRON_PICKAXE, 1).addEnchant(Enchantment.DURABILITY,3).addEnchant(Enchantment.DIG_SPEED,1).setName("§fGoldspitzhacke").setLore(Arrays.asList("§6125 AIOs", "125")).toItemStack());
        items.setItem(14, new ItemBuilder(Material.FISHING_ROD, 1).setName("§fAngel").setLore(Arrays.asList("§6250 AIOs", "250")).toItemStack());
        items.setItem(15, new ItemBuilder(Material.FLINT_AND_STEEL, 1).setName("§fFeuerzeug").setLore(Arrays.asList("§6250 AIOs", "250")).toItemStack());
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
        items.setItem(11, new ItemBuilder(Material.STICK, 1).addUnsafeEnchantment(Enchantment.KNOCKBACK,1).setName("§fKnockbackstick").setLore(Arrays.asList("§630 AIOs", "30")).toItemStack());
        items.setItem(13, new ItemBuilder(Material.GOLD_SWORD, 1).addUnsafeEnchantment(Enchantment.DAMAGE_ALL,1).addUnsafeEnchantment(Enchantment.DURABILITY,1).setName("§fGoldschwert I").setLore(Arrays.asList("§650 AIOs", "1")).toItemStack());
        items.setItem(14, new ItemBuilder(Material.GOLD_SWORD, 1).addUnsafeEnchantment(Enchantment.DURABILITY,1).addUnsafeEnchantment(Enchantment.DAMAGE_ALL,2).setName("§fGoldschwert II").setLore(Arrays.asList("§6125 AIOs", "3")).toItemStack());
        items.setItem(15, new ItemBuilder(Material.IRON_SWORD, 1).addUnsafeEnchantment(Enchantment.DURABILITY,1).addUnsafeEnchantment(Enchantment.KNOCKBACK, 1).addUnsafeEnchantment(Enchantment.DAMAGE_ALL,2).setName("§fEisenschwert").setLore(Arrays.asList("§6400 AIOs", "400")).toItemStack());
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
        items.setItem(10, new ItemBuilder(Material.BOW, 1).setDurability((short) 260).addUnsafeEnchantment(Enchantment.ARROW_INFINITE,1).setName("§fBogen I").setLore(Arrays.asList("§6200 AIOs", "200")).toItemStack());
        items.setItem(12, new ItemBuilder(Material.BOW, 1).setDurability((short) 260).addUnsafeEnchantment(Enchantment.ARROW_INFINITE,1).addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1).setName("§fBogen II").setLore(Arrays.asList("§6550 AIOs", "550")).toItemStack());
        items.setItem(14, new ItemBuilder(Material.BOW, 1).setDurability((short) 260).addUnsafeEnchantment(Enchantment.ARROW_INFINITE,1).addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 2).addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 1).setName("§fBogen III").setLore(Arrays.asList("§6800 AIOs", "800")).toItemStack());
        items.setItem(16, new ItemBuilder(Material.ARROW, 1).setName("§fPfeil").setLore(Arrays.asList("§6115 AIOs", "115")).toItemStack());
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
        items.setItem(11, new ItemBuilder(Material.APPLE, 1).setName("§fApfel").setLore(Arrays.asList("§63 AIOs", "3")).toItemStack());
        items.setItem(12, new ItemBuilder(Material.COOKED_BEEF, 1).setName("§fSteak").setLore(Arrays.asList("§66 AIOs", "6")).toItemStack());
        items.setItem(10, new ItemBuilder(Material.COOKIE, 2).setName("§fKeks").setLore(Arrays.asList("§63 AIOs", "3")).toItemStack());
        items.setItem(15, new ItemBuilder(Material.CAKE, 1).setName("§fKuchen").setLore(Arrays.asList("§620 AIOs", "20")).toItemStack());
        items.setItem(16, new ItemBuilder(Material.GOLDEN_APPLE, 1).setName("§fGoldapfel").setLore(Arrays.asList("§6175 AIOs", "175")).toItemStack());
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

    private void setChests() {
        ItemStack[] items2 = getShopItems(ShopCategory.STANDART);
        Inventory items = Bukkit.createInventory(null, 18, "§bBedWars Shop");
        items.setContents(items2);
        items.setItem(11, new ItemBuilder(Material.CHEST, 1).setName("§fKiste").setLore(Arrays.asList("§650 AIOs", "50")).toItemStack());
        items.setItem(15, new ItemBuilder(Material.ENDER_CHEST, 1).setName("§fTeamchest").setLore(Arrays.asList("§6180 AIOs", "180")).toItemStack());

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
        shopItems.put(ShopCategory.KISTEN, items.getContents());
    }

    private void setSpecials() {
        ItemStack[] items2 = getShopItems(ShopCategory.STANDART);
        Inventory items = Bukkit.createInventory(null, 18, "§bBedWars Shop");
        items.setContents(items2);
        items.setItem(9, new ItemBuilder(Material.LADDER, 1).setName("§fLeiter").setLore(Arrays.asList("§63 AIOs", "3")).toItemStack());
        items.setItem(11, new ItemBuilder(Material.FIREWORK, 1).setName("§fBaseTeleporter").setLore(Arrays.asList("§6200 AIOs", "200")).toItemStack());
        items.setItem(12, new ItemBuilder(Material.ARMOR_STAND, 1).setName("§fMobiler Shop").setLore(Arrays.asList("§6475 AIOs", "475")).toItemStack());
        items.setItem(17, new ItemBuilder(Material.ENDER_PEARL, 1).setName("§fEnderperle").setLore(Arrays.asList("§6750 AIOs", "750")).toItemStack());
        items.setItem(10, new ItemBuilder(Material.WEB, 1).setName("§fCobweb").setLore(Arrays.asList("§618 AIOs", "18")).toItemStack());
        items.setItem(15, new ItemBuilder(Material.TNT, 1).setName("§fTNT").setLore(Arrays.asList("§6180 AIOs", "180")).toItemStack());
        items.setItem(16, new ItemBuilder(Material.BLAZE_ROD, 1).setName("§fRettungsplattform").setLore(Arrays.asList("§6225 AIOs", "225")).toItemStack());

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

    private void setPotions() {
        ItemStack[] items2 = getShopItems(ShopCategory.STANDART);
        Inventory items = Bukkit.createInventory(null, 18, "§bBedWars Shop");
        items.setContents(items2);
        items.setItem(10, new ItemBuilder(Material.POTION, 1, (byte) 5).setName("§fHeilung 1").setLore(Arrays.asList("§645 AIOs", "45")).toItemStack());
        items.setItem(11, new ItemBuilder(Material.POTION, 1, (byte) 37).setName("§fHeilung 2").setLore(Arrays.asList("§680 AIOs", "80")).toItemStack());
        items.setItem(12, new ItemBuilder(Material.POTION, 1, (byte) 2).setName("§fGeschwindigkeit").setLore(Arrays.asList("§6150 AIOs", "150")).toItemStack());
        items.setItem(13, new ItemBuilder(Material.POTION, 1, (byte) 43).setName("§fSprungkraft").setLore(Arrays.asList("§6280 AIOs", "280")).toItemStack());
        items.setItem(14, new ItemBuilder(Material.POTION, 1, (byte) 14).setName("§fUnsichtbarkeit").setLore(Arrays.asList("§6400 AIOs", "400")).toItemStack());
        items.setItem(16, new ItemBuilder(Material.POTION, 1, (byte) 9).setName("§fStärke").setLore(Arrays.asList("§6700 AIOs", "700")).toItemStack());
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
        inv.setItem(7, new ItemBuilder(Material.CHEST).setName("§aKisten").toItemStack());
        inv.setItem(8, new ItemBuilder(Material.EMERALD).setName("§aSpecial").toItemStack());
        shopItems.put(ShopCategory.STANDART, inv.getContents());
    }

    private ItemStack setArmorColor(ItemStack stack, Team team) {
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
