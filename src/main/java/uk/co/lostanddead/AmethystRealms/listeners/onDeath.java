package uk.co.lostanddead.AmethystRealms.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;

public class onDeath implements Listener {

    private final AmethystRealmsCore core;

    public onDeath(AmethystRealmsCore core){this.core = core;}

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Entity e = event.getEntity();
        if (e instanceof Player){
            Player p = (Player) e;
            String msg = event.getDeathMessage();
            event.setDeathMessage(core.getPrefix(p) + ((Player) p).getDisplayName() + ChatColor.GRAY + event.getDeathMessage().replace(p.getName(), ""));
        }
    }
}
