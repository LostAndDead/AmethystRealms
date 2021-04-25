package uk.co.lostanddead.AmethystRealms.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;

public class Ban implements CommandExecutor {

    private final AmethystRealmsCore core;

    public Ban(AmethystRealmsCore core){this.core = core;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0){
            sender.sendMessage("也 " + ChatColor.RED + "Player Not Found" + ChatColor.RESET + " 也");
            return true;
        }
        Player toBan = Bukkit.getPlayer(args[0]);
        StringBuilder reason = new StringBuilder();
        for (String i : args){
            if (!i.equals(args[0])){
                reason.append(i).append(" ");
            }
        }
        if (reason.toString().equals("")){
            reason = new StringBuilder("No Reason Given ");
        }
        if(toBan == null){
            sender.sendMessage("也 " + ChatColor.RED + "Player Not Found" + ChatColor.RESET + " 也");
            return true;
        }else if(toBan.hasPermission("smp.admin")){
            sender.sendMessage("也 " + ChatColor.RED + "This Player Can't Be Banned" + ChatColor.RESET + " 也");
            return true;
        }else{
            core.getKicker().ban(toBan, reason.toString());
            sender.sendMessage("于 " + ChatColor.GRAY + toBan.getDisplayName() + ChatColor.AQUA +" Has Been Banned For " + ChatColor.GRAY + reason + ChatColor.RESET + "于");
            core.bot.banDiscord(toBan);
            return true;
        }
    }
}
