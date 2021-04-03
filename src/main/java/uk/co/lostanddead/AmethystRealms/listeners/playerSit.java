package uk.co.lostanddead.AmethystRealms.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;
import uk.co.lostanddead.AmethystRealms.events.PlayerSitEvent;

public class playerSit implements Listener {

    private final AmethystRealmsCore core;

    public playerSit(AmethystRealmsCore core) {
        this.core = core;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if ((event.getAction() == Action.RIGHT_CLICK_BLOCK) && (event.getHand() == EquipmentSlot.HAND) && (event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.AIR))) {
            Player player = event.getPlayer();
            Block block = event.getClickedBlock();
            if (block.getBlockData() instanceof Stairs) {
                if (core.seatsTaken.contains(block.getLocation())){
                    player.sendMessage("也 " + ChatColor.RED + "Seat Already Taken" + ChatColor.RESET + " 也");
                    return;
                }
                Block satOn = player.getLocation().add(0,1,0).getBlock();
                if(core.seatsTaken.contains(satOn.getLocation())){
                    player.sendMessage("也 " + ChatColor.RED + "You Are Already Sat Down" + ChatColor.RESET + " 也");
                    return;
                }
                float yaw = player.getLocation().getYaw();
                Stairs stairs = (Stairs) block.getBlockData();
                BlockFace face = stairs.getFacing();
                switch (face.getOppositeFace()) {
                    case NORTH: {
                        yaw = 180;
                        break;
                    }
                    case EAST: {
                        yaw = -90;
                        break;
                    }
                    case SOUTH: {
                        yaw = 0;
                        break;
                    }
                    case WEST: {
                        yaw = 90;
                        break;
                    }
                }
                Location sitLocation = block.getLocation();
                sitLocation.setYaw(yaw);
                sitLocation.add(0.4, -0.1, 0.5);

                PlayerSitEvent sitEvent = new PlayerSitEvent(player, sitLocation.clone());
                Bukkit.getPluginManager().callEvent(sitEvent);
                if (sitEvent.isCancelled()) {
                    return;
                }
                sitLocation = sitEvent.getSitLocation().clone();
                Arrow arrow = sitLocation.getWorld().spawnArrow(sitLocation, new Vector(0, 1, 0), 0, 0);
                arrow.setGravity(false);
                arrow.setInvulnerable(true);
                arrow.setPersistent(true);
                arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);

                new BukkitRunnable(){
                    @Override
                    public void run() {
                        if(arrow != null || arrow.isDead()){
                            arrow.setTicksLived(1);
                        }else{
                            this.cancel();
                        }
                    }
                }.runTaskTimer(core, 120L, 120L);

                player.teleport(sitLocation);
                arrow.addPassenger(player);
                core.seatsTaken.add(block.getLocation());
                event.setCancelled(true);
            }
        }
    }
}
