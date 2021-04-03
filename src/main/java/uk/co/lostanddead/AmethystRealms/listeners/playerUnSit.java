package uk.co.lostanddead.AmethystRealms.listeners;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;
import org.spigotmc.event.entity.EntityDismountEvent;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;


public class playerUnSit implements Listener {

    private final AmethystRealmsCore core;

    public playerUnSit(AmethystRealmsCore core){this.core = core;}

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        for(Arrow arrow: event.getPlayer().getLocation().getWorld().getEntitiesByClass(Arrow.class)){
            Entity entity = event.getPlayer();
            Vector vector = arrow.getVelocity();
            Location pos = arrow.getLocation();
            Location entityPos = entity.getLocation();
            if(vector.getX() != 0 || vector.getY() != 0 || vector.getZ() != 0){
                continue;
            }
            if (arrow.isInvulnerable() && ((pos.getX() == entityPos.getX()) && (pos.getZ() == entityPos.getZ()))){
                arrow.remove();
                Block block = pos.add(0,1,0).getBlock();
                core.seatsTaken.remove(block.getLocation());
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        if (event.getEntity() instanceof Player){
            for(Arrow arrow: event.getEntity().getLocation().getWorld().getEntitiesByClass(Arrow.class)){
                Entity entity = event.getEntity();
                Vector vector = arrow.getVelocity();
                Location pos = arrow.getLocation();
                Location entityPos = entity.getLocation();
                if(vector.getX() != 0 || vector.getY() != 0 || vector.getZ() != 0){
                    continue;
                }
                if (arrow.isInvulnerable() && ((pos.getX() == entityPos.getX()) && (pos.getZ() == entityPos.getZ()))){
                    arrow.remove();
                    Block block = pos.add(0,1,0).getBlock();
                    core.seatsTaken.remove(block.getLocation());
                }
            }
        }
    }

    //@EventHandler
    //public void onPlayerTeleport(PlayerTeleportEvent event){
    //    if (event.getPlayer() instanceof Player){
    //        for(Arrow arrow: event.getPlayer().getLocation().getWorld().getEntitiesByClass(Arrow.class)){
    //            Entity entity = event.getPlayer();
    //            Vector vector = arrow.getVelocity();
    //            Location pos = arrow.getLocation();
    //            Location entityPos = entity.getLocation();
    //            if(vector.getX() != 0 || vector.getY() != 0 || vector.getZ() != 0){
    //                continue;
    //            }
    //            if (arrow.isInvulnerable() && ((pos.getX() == entityPos.getX()) && (pos.getZ() == entityPos.getZ()))){
    //                arrow.remove();
    //                Block block = pos.add(0,1,0).getBlock();
    //                core.seatsTaken.remove(block.getLocation());
    //            }
    //        }
    //    }
    //}

    @EventHandler
    public void onExitVehicle(EntityDismountEvent event) throws InterruptedException {
        if (event.getEntity() instanceof Player){
            for(Arrow arrow: event.getEntity().getLocation().getWorld().getEntitiesByClass(Arrow.class)){
                if(arrow == null){
                    return;
                }
                Entity entity = event.getEntity();
                Vector vector = arrow.getVelocity();
                Location pos = arrow.getLocation();
                Location entityPos = entity.getLocation();
                if(vector.getX() != 0 || vector.getY() != 0 || vector.getZ() != 0){
                    continue;
                }
                if (arrow.isInvulnerable() && ((pos.getX() == entityPos.getX()) && (pos.getZ() == entityPos.getZ()))){
                    arrow.remove();
                    Block block = pos.add(0,1,0).getBlock();
                    core.seatsTaken.remove(block.getLocation());

                    Location location = new Location(entityPos.getWorld(), entityPos.getX(), entityPos.getY() + 1, entityPos.getZ());
                    location.setYaw(entityPos.getYaw());
                    location.setPitch(entityPos.getPitch());

                    entity.teleport(location);
                    event.setCancelled(true);
                }
            }
        }
    }

}
