package uk.co.lostanddead.AmethystRealms.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;

public class shopInventoryHandler implements Listener {

    private final AmethystRealmsCore core;

    public shopInventoryHandler(AmethystRealmsCore core){
        this.core = core;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(event.getView().getTitle().contains("用")){
            if(event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT){
                event.setCancelled(true);
            }
            int slot = event.getRawSlot();
            Inventory inv = event.getInventory();
            Player p = (Player) event.getWhoClicked();
            if (!(slot >= 45)){
                //if (!(slot == 37|| slot == 40 || slot == 43)){
                if (!(slot == 37)){
                    event.setCancelled(true);
                    switch (slot){
                        case 10: {
                            if(inv.getItem(10) != null){
                                ItemStack payment = inv.getItem(37);
                                if(payment != null){
                                    if(payment.getType() == Material.AMETHYST_SHARD && payment.getAmount() >= 10){
                                        boolean freeSlot = false;
                                        for (ItemStack is : p.getInventory().getContents()){
                                            if (is == null) {
                                                freeSlot = true;
                                                break;
                                            }
                                        }
                                        if(freeSlot){
                                            payment.setAmount(payment.getAmount() - 10);
                                            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "cartographer get 1 " + p.getName());
                                        }else{
                                            break;
                                        }
                                    }
                                }else{
                                    break;
                                }
                            }else{
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void inventoryDragEvent(InventoryDragEvent event){
        if(event.getView().getTitle().contains("用")){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
        if(event.getView().getTitle().contains("用")) {
            boolean itemsReturned = false;
            ItemStack slot1 = event.getInventory().getItem(37);
            //ItemStack slot2 = event.getInventory().getItem(40);
            //ItemStack slot3 = event.getInventory().getItem(43);
            if(slot1 != null){
                event.getPlayer().getWorld().dropItemNaturally(event.getPlayer().getLocation(), slot1);
                itemsReturned = true;
            }
            //if(slot2 != null){
            //    event.getPlayer().getWorld().dropItemNaturally(event.getPlayer().getLocation(), slot3);
            //}
            //if(slot3 != null){
            //    event.getPlayer().getWorld().dropItemNaturally(event.getPlayer().getLocation(), slot3);
            //}

            if(itemsReturned){
                Player p = (Player) event.getPlayer();
                p.sendMessage("出");
                p.sendMessage("");
                p.sendMessage("               " + net.md_5.bungee.api.ChatColor.of("#8d6acc") + "You Left Some Items In My Store,");
                p.sendMessage("               " + net.md_5.bungee.api.ChatColor.of("#8d6acc") + "§7Here, Have Them Back.");
                p.sendMessage("");
                p.sendMessage("");
            }


        }
    }
}
