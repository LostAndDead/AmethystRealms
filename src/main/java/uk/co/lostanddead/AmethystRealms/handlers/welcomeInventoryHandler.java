package uk.co.lostanddead.AmethystRealms.handlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;


public class welcomeInventoryHandler implements Listener {

    private final AmethystRealmsCore core;

    private boolean allowClose;

    public welcomeInventoryHandler(AmethystRealmsCore core){
        this.core = core;
    }

    @EventHandler
    public void invClose(InventoryCloseEvent event) {
        if(event.getView().getTitle().contains("来")){
            if(allowClose){
                allowClose = false;
                return;
            }
            Inventory inv = event.getInventory();
            Bukkit.getScheduler().runTaskLater(core, () -> event.getPlayer().openInventory(inv), 1L);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Player p = (Player) event.getWhoClicked();
        if(event.getView().getTitle().contains("来")){
            if(event.getRawSlot() == 36){
                event.setCancelled(true);
                allowClose = true;
                p.closeInventory();
                Bukkit.getScheduler().runTaskLater(core, () -> p.getInventory().clear(), 1L);
                core.getKicker().kick(p,"Well if you don't want to accept the rules you cant play!");
            }else if(event.getRawSlot() == 44){
                event.setCancelled(true);
                if(p.getInventory().getItem(4) != null){
                    core.getSQL().setWelcomed(p, true);
                    allowClose = true;
                    p.closeInventory();
                    Bukkit.getScheduler().runTaskLater(core, () -> {
                        p.getInventory().clear();
                        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "cartographer get 1 " + p.getName());
                    }, 1L);
                }
            }else if(!(event.getRawSlot() == 4 || event.getRawSlot() == 40)){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void inventoryDragEvent(InventoryDragEvent event){
        if(event.getView().getTitle().contains("来")){
            event.setCancelled(true);
        }
    }

}
