package uk.co.lostanddead.AmethystRealms.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;
import uk.co.lostanddead.AmethystRealms.MySQL;

public class UnMute implements CommandExecutor {

    private final AmethystRealmsCore core;

    public UnMute(AmethystRealmsCore core){this.core = core;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player toUnMute = Bukkit.getPlayer(args[0]);

        if(toUnMute == null){
            sender.sendMessage("也 " + ChatColor.RED + "Player Not Found" + ChatColor.RESET + " 也");
        }else{
            MySQL sql = core.getSQL();
            boolean Muted = sql.getMuted(toUnMute);
            if(!Muted){
                sender.sendMessage("也 " + ChatColor.RED + "Player Not Muted" + ChatColor.RESET + " 也");
            }else{
                sql.setMuted(toUnMute, false);
                toUnMute.sendMessage("于 " + ChatColor.AQUA + "You Have Been Un-Muted" + ChatColor.RESET + " 于");
                toUnMute.playSound(toUnMute.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
                sender.sendMessage("于 " + ChatColor.GRAY + toUnMute.getDisplayName() + ChatColor.AQUA +" Has Been Un-Muted" + ChatColor.RESET + " 于");
            }
        }
        return true;
    }
}
