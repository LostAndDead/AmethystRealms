package uk.co.lostanddead.AmethystRealms.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;

public class serverPing implements Listener {

    private final AmethystRealmsCore core;

    public serverPing(AmethystRealmsCore core){this.core = core;}

    @EventHandler
    public void serverPingEvent(ServerListPingEvent event){
        event.setMotd("§7Welcome To " + net.md_5.bungee.api.ChatColor.of("#8d6acc") + "Amethyst Realms\n" + "§7Whitelisted §6Vanilla + §7Survival Multiplayer");
    }
}
