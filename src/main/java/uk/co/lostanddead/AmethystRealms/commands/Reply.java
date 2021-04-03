package uk.co.lostanddead.AmethystRealms.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;

public class Reply implements CommandExecutor {

    private final AmethystRealmsCore core;

    public Reply(AmethystRealmsCore core){this.core = core;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String name = core.getSQL().getLastMessaged((Player) sender);
        if (name == null){
            sender.sendMessage("也 " + ChatColor.RED + "You Don't Have A Player To Reply To" + ChatColor.RESET + " 也");
            return true;
        }
        Player recipient = Bukkit.getPlayer(name);
        StringBuilder msg = new StringBuilder();
        for (String i : args){
            msg.append(i).append(" ");
        }
        if (msg.toString().equals("")){
            sender.sendMessage("也 " + ChatColor.RED + "Cant Send An Empty Message" + ChatColor.RESET + " 也");
            return true;
        }if(recipient == null){
            sender.sendMessage("也 " + ChatColor.RED + "You Don't Have A Player To Reply To." + ChatColor.RESET + " 也");
            return true;
        }else if(recipient == sender){
            sender.sendMessage("也 " + ChatColor.RED + "You Cant Message Yourself" + ChatColor.RESET + " 也");
            return true;
        }else{
            recipient.sendMessage("上 " + ChatColor.DARK_GRAY + "From: " + ChatColor.RESET + core.getPrefix((Player) sender)+ ((Player) sender).getDisplayName() + ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + msg);
            sender.sendMessage("上 " + ChatColor.DARK_GRAY + "To: " + ChatColor.RESET + core.getPrefix(recipient) + recipient.getDisplayName() + ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + msg);
            return true;
        }
    }
}
