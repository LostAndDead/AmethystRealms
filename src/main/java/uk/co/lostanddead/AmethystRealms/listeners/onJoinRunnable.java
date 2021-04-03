package uk.co.lostanddead.AmethystRealms.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;

import java.util.UUID;

public class onJoinRunnable extends BukkitRunnable implements Listener {

    private final AmethystRealmsCore core;
    private final Player p;

    public onJoinRunnable(AmethystRealmsCore core, Player p){
        this.core = core;
        this.p = p;

        Bukkit.getServer().getPluginManager().registerEvents(this, core);
    }

    @Override
    public void run() {
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        super.cancel();
        HandlerList.unregisterAll(this); //This unregisters the event that is done via this class.
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        for (UUID uuid : core.playersJoining){
            if (uuid == event.getPlayer().getUniqueId()){
                event.setCancelled(true);
            }
        }
        //if(core.playersJoining.contains(p.getUniqueId())){
        //    event.setCancelled(true);
        //}else{
        //    cancel();
        //}
    }
}
