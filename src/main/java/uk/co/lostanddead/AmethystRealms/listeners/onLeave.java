package uk.co.lostanddead.AmethystRealms.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;

public class onLeave implements Listener {

    private final AmethystRealmsCore core;

    public onLeave(AmethystRealmsCore core){this.core = core;}

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){

        event.setQuitMessage(null);
        Player p = event.getPlayer();
        String prefix = core.getPrefix(p);
        core.playersWithHud.remove(p.getUniqueId());
        core.playerMessages.remove(p.getUniqueId());
        core.playersJoining.remove(p.getUniqueId());

        for(Player pl : Bukkit.getOnlinePlayers()){
            pl.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&c-&8] &r" + prefix + p.getName()));
            if(Bukkit.getOnlinePlayers().size()-1 == 1){
                pl.setPlayerListFooter("\n          §7You Are Alone " + net.md_5.bungee.api.ChatColor.of("#8d6acc") + ":(          \n");
            }else{
                pl.setPlayerListFooter("\n          §7Currently " + net.md_5.bungee.api.ChatColor.of("#8d6acc") + Bukkit.getOnlinePlayers().size() + " §7Players Online          \n");
            }
        }
    }
}
