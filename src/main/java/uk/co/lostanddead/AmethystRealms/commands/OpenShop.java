package uk.co.lostanddead.AmethystRealms.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.co.lostanddead.AmethystRealms.JSONMessage;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;

public class OpenShop implements CommandExecutor {

    private final AmethystRealmsCore core;

    public OpenShop(AmethystRealmsCore core){this.core = core;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(core.shopOpen){
            sender.sendMessage("也 " + ChatColor.RED + "The Shop Is Already Open" + ChatColor.RESET + " 也");
        }
        core.shopOpen = true;
        sender.sendMessage("于 " + ChatColor.AQUA + "Shop Now Open" + ChatColor.RESET + " 于");
        Bukkit.getLogger().info("Shop Now Open");
        for (Player p : Bukkit.getOnlinePlayers()){
            p.sendMessage("看");
            p.sendMessage("");
            p.sendMessage("               " + net.md_5.bungee.api.ChatColor.of("#8d6acc") + "The Shop Is Now Open For §610 Minutes");
            JSONMessage.create("               ").then("[Open The Shop]").color(ChatColor.GREEN).style(ChatColor.BOLD).runCommand("/shop").send(p);
            p.sendMessage("");
            p.sendMessage("");
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
        }
        Bukkit.getScheduler().runTaskLater(core, () -> {
            core.shopOpen = false;
            Bukkit.getLogger().info("Shop Now Closed");
            for (Player p : Bukkit.getOnlinePlayers()){
                p.sendMessage("学");
                p.sendMessage("");
                p.sendMessage("               " + net.md_5.bungee.api.ChatColor.of("#8d6acc") +"The Shop Is Now Closed");
                p.sendMessage("               §7Next Opening: §650 Minutes");
                p.sendMessage("");
                p.sendMessage("");
            }
        },12000L);
        return true;
    }
}
