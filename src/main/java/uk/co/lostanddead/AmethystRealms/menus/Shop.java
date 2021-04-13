package uk.co.lostanddead.AmethystRealms.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;

import java.util.Arrays;

public class Shop{

    private final AmethystRealmsCore core;

    public Shop(AmethystRealmsCore core){this.core = core;}

    public void open(Player p){
        Inventory inv = Bukkit.createInventory(null, 45, "§f多用");
        inv.setItem(10, createItem(Material.MAP, "§3§lMap §f年"+ net.md_5.bungee.api.ChatColor.of("#8d6acc") + "10 ", "§7Did you lose your map?","","§7Luckily for you I have more in stock."));
        inv.setItem(13, createItem(Material.BARRIER, "§cNo Item For Sale", ""));
        inv.setItem(16, createItem(Material.BARRIER, "§cNo Item For Sale", ""));
        inv.setItem(40, createItem(Material.BARRIER, "§cNo Item For Sale", ""));
        inv.setItem(43, createItem(Material.BARRIER, "§cNo Item For Sale", ""));
        p.openInventory(inv);
    }

    protected ItemStack createItem(final Material material, final String name, final String... lore){
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }
}
