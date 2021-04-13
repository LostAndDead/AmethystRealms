package uk.co.lostanddead.AmethystRealms.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;

import java.util.Random;

public class Link implements CommandExecutor {

    private final AmethystRealmsCore core;

    public Link (AmethystRealmsCore core){this.core = core;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender){ return true; }
        if(core.getSQL().isLinked((Player) sender)){
            String username = core.bot.getDiscordUsername((Player) sender);
            if (username == null){
                sender.sendMessage("也 " + ChatColor.RED + "There was an error, contact staff!" + ChatColor.RESET + " 也");
            }
            sender.sendMessage("也 " + ChatColor.RED + "Already linked to " + ChatColor.GOLD + username + ChatColor.RESET + " 也");
            return true;
        }

        Random rand = new Random();
        String code = String.format("%04d", rand.nextInt(10000));

        core.bot.createPendingLink(code, (Player) sender);
        sender.sendMessage("于 " + ChatColor.AQUA + "Send this code: " + ChatColor.GOLD + code + ChatColor.AQUA + " to "+ ChatColor.GOLD+ "#link-me" + ChatColor.AQUA +" on Discord" + ChatColor.RESET + " 于");
        return true;
    }
}
