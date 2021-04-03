package uk.co.lostanddead.AmethystRealms.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;

import java.util.Arrays;

public class Welcome {

    private final AmethystRealmsCore core;

    public Welcome(AmethystRealmsCore core){this.core = core;}

    public void open(Player p){
        Inventory inv = Bukkit.createInventory(null, InventoryType.DISPENSER, "§fꈁ来");
        Inventory pinv = p.getInventory();
        pinv.clear();
        inv.setItem(4, createItem(Material.MAP, "§3§lMap of Adventure", "§7Drag me to the slot bellow", "§7to confirm you have agreed", "§7to the rules."));
        pinv.setItem(16, createItem(Material.BARRIER, "§d§lDiscord", "§dJoin The Discord For Full Rules","§7https://discord.gg/EWBKRTZxGt"));
        pinv.setItem(0, createItem(Material.BARRIER, "§c§lDecline", ""));
        pinv.setItem(4, null);
        pinv.setItem(8, createItem(Material.BARRIER, "§2§lAccept", "§cYou must also move the map","§cto the right slot"));
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
