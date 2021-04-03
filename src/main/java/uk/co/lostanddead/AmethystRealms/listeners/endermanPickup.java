package uk.co.lostanddead.AmethystRealms.listeners;

import org.bukkit.entity.Enderman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;

public class endermanPickup implements Listener {

    private final AmethystRealmsCore core;

    public endermanPickup(AmethystRealmsCore core){this.core = core;}

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event){
        if(event.getEntity() instanceof Enderman){
            event.setCancelled(true);
        }
    }
}
