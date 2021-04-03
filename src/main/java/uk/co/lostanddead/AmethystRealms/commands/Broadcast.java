package uk.co.lostanddead.AmethystRealms.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;

public class Broadcast implements CommandExecutor {
    private final AmethystRealmsCore core;

    public Broadcast(AmethystRealmsCore core){this.core = core;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        StringBuilder message = new StringBuilder();
        for (String i : args){
            message.append(i).append(" ");
        }
        for (Player p : Bukkit.getOnlinePlayers()){
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
            p.sendTitle("定", ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', message.toString()), 10, 140, 10);
        }
        return true;
    }
}