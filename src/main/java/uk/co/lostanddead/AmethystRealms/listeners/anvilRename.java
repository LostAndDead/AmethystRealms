package uk.co.lostanddead.AmethystRealms.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;

public class anvilRename implements Listener {

    private final AmethystRealmsCore core;

    public anvilRename(AmethystRealmsCore core) {
        this.core = core;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInventoryClick(InventoryClickEvent event){
        if(event.getInventory() instanceof AnvilInventory){
            Player p = (Player) event.getWhoClicked();
            int rawSlot = event.getRawSlot();
            InventoryView view = event.getView();
            if(rawSlot == view.convertSlot(rawSlot)){
                if(rawSlot == 2){
                    ItemStack item = event.getCurrentItem();
                    if(item != null){
                        ItemMeta meta = item.getItemMeta();
                        if(meta != null){
                            if(meta.hasDisplayName()){
                                boolean hasColorCodes = !(meta.getDisplayName().equals(ChatColor.stripColor(meta.getDisplayName())));
                                if(hasColorCodes){
                                    if(!p.hasPermission("smp.premium")){
                                        event.setCancelled(true);
                                        p.sendMessage("也 " + "到 " + ChatColor.RED + "Is Required To Name Items With Colors" + ChatColor.RESET + " 也");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void openInventory(PrepareAnvilEvent event){
        ItemStack item = event.getResult();
        if (item != null){
            ItemMeta meta = item.getItemMeta();
            if(meta != null){
                if(meta.hasDisplayName()){
                    if(meta.getDisplayName().contains("&")){
                        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', meta.getDisplayName()));
                        item.setItemMeta(meta);
                    }
                }
            }
            event.setResult(item);
        }
    }
}
