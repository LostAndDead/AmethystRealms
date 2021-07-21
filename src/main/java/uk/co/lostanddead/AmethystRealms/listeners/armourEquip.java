package uk.co.lostanddead.AmethystRealms.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;

public class armourEquip implements Listener {

    private final AmethystRealmsCore core;

    public armourEquip(AmethystRealmsCore core){this.core = core;}

    @EventHandler
    public void inventoryClick(InventoryClickEvent event){
        Player p = (Player) event.getWhoClicked();
        if (event.getSlot() == 39){
            ItemStack inHand = event.getCursor();
            ItemStack inSlot = p.getInventory().getHelmet();

            if (inHand.hasItemMeta()){
                if (inHand.getItemMeta().hasCustomModelData()){ }else{
                    return;
                }
            }else{
                return;
            }

            if (inHand != null && inHand.getType() != Material.AIR){
                event.setCancelled(true);

                event.setCurrentItem(inHand);
                p.setItemOnCursor(inSlot);
                p.updateInventory();
            }
        }
    }

    @EventHandler
    public void rightClick(PlayerInteractEvent event){
        Player p = (Player) event.getPlayer();
        PlayerInventory pinv = p.getInventory();
        ItemStack held = pinv.getItemInMainHand();
        ItemStack helmet = pinv.getHelmet();

        if (held.hasItemMeta() && (helmet == null || helmet.getType() == Material.AIR)){
            if (held.getItemMeta().hasCustomModelData()){ }else{
                return;
            }
        }else{
            return;
        }

        pinv.setHelmet(held);
        pinv.setItemInMainHand(helmet);

        p.updateInventory();
    }
}
