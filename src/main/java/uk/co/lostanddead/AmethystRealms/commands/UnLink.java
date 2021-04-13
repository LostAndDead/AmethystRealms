package uk.co.lostanddead.AmethystRealms.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;
import uk.co.lostanddead.AmethystRealms.JSONMessage;

import java.util.Locale;
import java.util.Random;

public class UnLink implements CommandExecutor {

    private final AmethystRealmsCore core;

    public UnLink (AmethystRealmsCore core){this.core = core;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender){ return true; }
        if(!core.getSQL().isLinked((Player) sender)){
            sender.sendMessage("也 " + ChatColor.RED + "Not linked to a Discord account" + ChatColor.RESET + " 也");
            return true;
        }

        if (args.length >= 1){
            if (args[0].equalsIgnoreCase("confirm")){
                core.bot.removeLink((Player) sender);
                return true;
            }else{
                sender.sendMessage("也 " + ChatColor.RED + "You need to confirm" + ChatColor.RESET + " 也");
                JSONMessage.create("").then("  [Confirm]").color(ChatColor.RED).style(ChatColor.BOLD).runCommand("/unlink confirm").send((Player) sender);
            }
        }else{
            sender.sendMessage("也 " + ChatColor.RED + "You need to confirm" + ChatColor.RESET + " 也");
            JSONMessage.create("").then("      [Confirm]").color(ChatColor.RED).style(ChatColor.BOLD).runCommand("/unlink confirm").send((Player) sender);
        }

        return true;
    }
}
