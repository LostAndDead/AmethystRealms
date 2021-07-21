package uk.co.lostanddead.AmethystRealms.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;

public class onCropTrample implements Listener {

    private final AmethystRealmsCore core;

    public onCropTrample(AmethystRealmsCore core){this.core = core;}

    @EventHandler
    public void onPlayerTrample(PlayerInteractEvent event){
        if (event.getAction() == Action.PHYSICAL){
            Block block = event.getClickedBlock();
            if (block == null){
                return;
            }
            if (block.getType() == Material.FARMLAND){
                event.setUseInteractedBlock(Event.Result.DENY);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler void onMobTrample(EntityInteractEvent event){
        Block block = event.getBlock();
        if (block == null){
            return;
        }
        if (block.getType() == Material.FARMLAND){
            event.setCancelled(true);
        }
    }
}
