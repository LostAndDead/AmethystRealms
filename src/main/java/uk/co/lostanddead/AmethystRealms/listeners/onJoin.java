package uk.co.lostanddead.AmethystRealms.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.scheduler.BukkitRunnable;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;
import uk.co.lostanddead.AmethystRealms.MySQL;
import uk.co.lostanddead.AmethystRealms.menus.Welcome;

import java.util.UUID;
import java.util.function.Supplier;

public class onJoin implements Listener {

    private final AmethystRealmsCore core;
    private final MySQL sql;

    public onJoin(AmethystRealmsCore core){this.core = core; this.sql = core.getSQL();}

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        Player p = event.getPlayer();
        p.setAllowFlight(true);
        sql.createPlayer(p);
        core.playersJoining.add(p.getUniqueId());

        p.setResourcePack(core.defaultPack, core.decodeHexString(core.defaultPackHash));
        p.setPlayerListHeader("\n          §7Welcome To " + net.md_5.bungee.api.ChatColor.of("#8d6acc") + "Amethyst Realms!          \n");
        if(Bukkit.getOnlinePlayers().size() == 1){
            p.setPlayerListFooter("\n          §7You Are Alone " + net.md_5.bungee.api.ChatColor.of("#8d6acc") + ":(          \n");
        }else{
            p.setPlayerListFooter("\n          §7Currently " + net.md_5.bungee.api.ChatColor.of("#8d6acc") + Bukkit.getOnlinePlayers().size() + " §7Players Online          \n");
        }


        new onJoinRunnable(core, p).runTaskTimer(core, 0L, 1L);

        Bukkit.getScheduler().scheduleSyncDelayedTask(core, new Runnable() {
            @Override
            public void run() {
                Location loc = p.getLocation();
                loc.add(0, 100, 0);
                ArmorStand stand = loc.getWorld().spawn(loc, ArmorStand.class);
                stand.setGravity(false);
                stand.setVisible(false);
                stand.setCanPickupItems(false);
                stand.setCustomName(ChatColor.GRAY + "Loading");
                stand.setCustomNameVisible(true);
                stand.setMarker(true);
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        if(!core.playersJoining.contains(p.getUniqueId())){
                            this.cancel();
                            stand.remove();
                        }else{
                            if (p.isSneaking()) {
                                stand.teleport(p.getLocation().add(0, 1.65, 0));
                            } else {
                                stand.teleport(p.getLocation().add(0, 2.08, 0));
                            }
                        }
                    }
                }.runTaskTimer(core, 1L, 1L);
            }
        },1L);
    }

    @EventHandler
    public void confirmPack(PlayerResourcePackStatusEvent event){
        Player p = event.getPlayer();
        switch (event.getStatus()){
            case ACCEPTED: {
                break;
            }
            case DECLINED: {
                p.setAllowFlight(true);
                core.getKicker().kick(p,"You Did Not Accept The Recourse Pack");
                break;
            }
            case SUCCESSFULLY_LOADED: {
                p.sendMessage("逘");
                p.sendMessage("");
                p.sendMessage("               §7Welcome To " + net.md_5.bungee.api.ChatColor.of("#8d6acc") + "Amethyst Realms!");
                p.sendMessage("               §7Whitelisted §6Vanilla + §7Survival Multiplayer");
                p.sendMessage("");
                p.sendMessage("");

                boolean Welcomed = sql.getWelcomed(p);

                if(!Welcomed){
                    Welcome welcome = new Welcome(core);
                    welcome.open(p);
                }

                boolean hudEnabled = sql.getHudEnabled(p);

                if(hudEnabled){
                    core.playersWithHud.add(p.getUniqueId());
                }

                String prefix = core.getPrefix(p);

                for(Player pl : Bukkit.getOnlinePlayers()){
                    if(!(pl == p)){
                        pl.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&a+&8] &r" + prefix + p.getName()));
                    }
                    if(Bukkit.getOnlinePlayers().size() == 1){
                        p.setPlayerListFooter("\n          §7You Are Alone " + net.md_5.bungee.api.ChatColor.of("#8d6acc") + ":(          \n");
                    }else{
                        pl.setPlayerListFooter("\n          §7Currently " + net.md_5.bungee.api.ChatColor.of("#8d6acc") + Bukkit.getOnlinePlayers().size() + " §7Players Online          \n");
                    }
                }
                core.playersJoining.remove(p.getUniqueId());
                p.setAllowFlight(true);
                break;
            }
            case FAILED_DOWNLOAD: {
                p.setAllowFlight(true);
                core.getKicker().kick(p,"Error While Loading Pack, This Is Likely A Server Issue.");
                break;
            }
        }
    }
}
