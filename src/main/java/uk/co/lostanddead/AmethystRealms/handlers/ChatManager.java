package uk.co.lostanddead.AmethystRealms.handlers;

import com.google.common.base.CharMatcher;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;

public class ChatManager implements Listener {
    
    public final AmethystRealmsCore core;
    
    public ChatManager(AmethystRealmsCore core){this.core = core;}

    @EventHandler
    public void chatFormat(AsyncPlayerChatEvent event){
        Player p = event.getPlayer();

        if(core.getSQL().getMuted(p)){
            p.sendMessage("也 " + ChatColor.RED + "You Are Muted" + ChatColor.RESET + " 也");
            event.setCancelled(true);
        }

        String premessage = event.getMessage().replaceAll("%","%%");
        StringBuilder message = new StringBuilder(CharMatcher.noneOf("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!\"£$%^&*()-=_+\\,./|<>?[];'#{}:@~ ").removeFrom(premessage));
        String removed = CharMatcher.anyOf("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!\"£$%^&*()-=_+\\,./|<>?[];'#{}:@~ ").removeFrom(premessage);

        String original = message.toString();

        if(message.length() <= 0){
            p.sendMessage("也" + ChatColor.RED +" You can not send an empty message " + ChatColor.RESET + "也");
            event.setCancelled(true);
            return;
        }

        if(removed.length() >= 1){
            p.sendMessage("也" + ChatColor.RED +" Some characters were removed from your message " + ChatColor.RESET + "也");
        }

        String[] words = message.toString().split(" ");
        message = new StringBuilder();

        for (String word : words){
            boolean foundName = false;
            for (Player pl : Bukkit.getOnlinePlayers()){
                if (word.contains(pl.getDisplayName())){
                    pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
                    if(p.hasPermission("smp.premium")){
                        message.append(core.getPrefixColor(pl)).append(word).append(ChatColor.WHITE).append(" ");
                    }else{
                        message.append(core.getPrefixColor(pl)).append(word).append(ChatColor.GRAY).append(" ");
                    }
                    foundName = true;
                }
            }
            if(!foundName){
                message.append(word).append(" ");
            }
        }

        final String altMessage = message.toString();

        if(p.hasPermission("smp.owner")){
            event.setFormat("上 선 " + ChatColor.RED + p.getDisplayName() + ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + String.format("%s", ChatColor.translateAlternateColorCodes('&', message.toString())));
        }
        else if(p.hasPermission("smp.admin")){
            event.setFormat("上 我 " + ChatColor.DARK_PURPLE + p.getDisplayName() + ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + String.format("%s", ChatColor.translateAlternateColorCodes('&', message.toString())));
        }
        else if(p.hasPermission("smp.mod")){
            event.setFormat("上 有 " + ChatColor.AQUA + p.getDisplayName() + ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + String.format("%s", ChatColor.translateAlternateColorCodes('&', message.toString())));
        }
        else if(p.hasPermission("smp.premium")){
            event.setFormat("上 到 " + ChatColor.LIGHT_PURPLE + p.getDisplayName() + ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + String.format("%s", ChatColor.translateAlternateColorCodes('&', message.toString())));
        }
        else{
            event.setFormat("上 섚 " + ChatColor.GOLD + p.getDisplayName() + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY + String.format("%s", message.toString()));
        }

        if((message.length() <= 50) && !(core.playerMessages.contains(p.getUniqueId()))){
            Bukkit.getScheduler().scheduleSyncDelayedTask(core, new Runnable() {
                @Override
                public void run() {
                    Location loc = p.getLocation();
                    loc.add(0, 100, 0);
                    ArmorStand stand = loc.getWorld().spawn(loc, ArmorStand.class);
                    stand.setGravity(false);
                    stand.setVisible(false);
                    stand.setCanPickupItems(false);
                    if (p.hasPermission("smp.premium")){
                        stand.setCustomName("上 "+ String.format("%s", ChatColor.translateAlternateColorCodes('&', altMessage)));
                    }else{
                        stand.setCustomName("上 "+ altMessage);
                    }
                    stand.setCustomNameVisible(true);
                    stand.setMarker(true);
                    core.playerMessages.put(p.getUniqueId(), stand);
                    new BukkitRunnable(){
                        private int i = 0;
                        @Override
                        public void run() {
                            if(i >= 80){
                                this.cancel();
                                stand.remove();
                                core.playerMessages.remove(p.getUniqueId());
                            }else{
                                i++;
                                if(p.isSneaking()){
                                    stand.teleport(p.getLocation().add(0, 1.65, 0));
                                }else{
                                    stand.teleport(p.getLocation().add(0, 2.08, 0));
                                }
                            }
                        }
                    }.runTaskTimer(core, 1L, 1L);
                }
            },1L);
        }
        core.bot.sendDiscordChat(p, original);
    }
}
