package uk.co.lostanddead.AmethystRealms.listeners;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;


public class onAdvancementDone implements Listener {

    private final AmethystRealmsCore core;

    public onAdvancementDone(AmethystRealmsCore core){this.core = core;}

    @EventHandler
    public void onAdvancement(PlayerAdvancementDoneEvent event){
        Player p = event.getPlayer();
        Advancement adv = event.getAdvancement();
        String key = adv.getKey().getKey().replace("/", ".");
        if (key.contains("recipes") || key.contains("root")){
            return;
        }
        Object obj = core.lang.get("advancements." + key + ".title");
        if(obj == null){
            //for (Player pl : Bukkit.getOnlinePlayers()){
            //    pl.sendMessage(core.getPrefix(p) + p.getName() + ChatColor.GRAY + " Completed an advancement.");
            //    pl.sendMessage(ChatColor.RED + "(Something went wrong and I cant figure out which)");
            //}
            Bukkit.getLogger().info("Advancement Error: " + key);
            return;
        }else{
            String name = obj.toString();
            if (key.contains("adventure")){
                for (Player pl : Bukkit.getOnlinePlayers()){
                    TextComponent msg = new TextComponent(core.getPrefix(p) + p.getName() + ChatColor.GRAY + " Completed " + ChatColor.DARK_PURPLE + name);
                    msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GRAY + core.lang.get("advancements." + key + ".description").toString()).create()));
                    pl.spigot().sendMessage(msg);
                    pl.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1f, 1f);
                }
            }else{
                for (Player pl : Bukkit.getOnlinePlayers()){
                    pl.sendMessage(core.getPrefix(p) + p.getName() + ChatColor.GRAY + " Completed " + ChatColor.GREEN + name);
                    TextComponent msg = new TextComponent(core.getPrefix(p) + p.getName() + ChatColor.GRAY + " Completed " + ChatColor.GREEN + name);
                    msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GRAY + core.lang.get("advancements." + key + ".description").toString()).create()));
                }
            }

        }

    }
}
