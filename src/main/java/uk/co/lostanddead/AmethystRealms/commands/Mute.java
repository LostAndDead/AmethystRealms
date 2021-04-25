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

public class Mute implements CommandExecutor {

    private final AmethystRealmsCore core;

    public Mute(AmethystRealmsCore core){this.core = core;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0){
            sender.sendMessage("也 " + ChatColor.RED + "Player Not Found" + ChatColor.RESET + " 也");
            return true;
        }

        Player toMute = Bukkit.getPlayer(args[0]);
        StringBuilder reason = new StringBuilder();
        for (String i : args){
            if (i.equals(args[0])){
                reason.append(i).append(" ");
            }
        }
        if (reason.toString().equals("")){
            reason = new StringBuilder("No Reason Given ");
        }
        if(toMute == null){
            sender.sendMessage("也 " + ChatColor.RED + "Player Not Found" + ChatColor.RESET + " 也");
            return true;
        }else if(toMute.hasPermission("smp.admin")){
            sender.sendMessage("也 " + ChatColor.RED + "This Player Can't Be Muted" + ChatColor.RESET + " 也");
            return true;
        }else{
            MySQL sql = core.getSQL();
            boolean Muted = sql.getMuted(toMute);
            if(Muted){
                sender.sendMessage("也 " + ChatColor.RED + "Player Already Muted" + ChatColor.RESET + " 也");
            }else{
                sql.setMuted(toMute, true);
                toMute.sendMessage("于 " + ChatColor.AQUA + "You Have Been Muted For " + ChatColor.GRAY + reason + ChatColor.RESET + "于");
                toMute.playSound(toMute.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
                sender.sendMessage("于 " + ChatColor.GRAY + toMute.getDisplayName() + ChatColor.AQUA +" Has Been Muted For " + ChatColor.GRAY + reason + ChatColor.RESET + "于");
            }
            return true;
        }
    }
}
