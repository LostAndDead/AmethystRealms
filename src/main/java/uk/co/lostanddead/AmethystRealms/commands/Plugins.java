package uk.co.lostanddead.AmethystRealms.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;

public class Plugins implements CommandExecutor {

    private final AmethystRealmsCore core;

    public Plugins(AmethystRealmsCore core){this.core = core;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.WHITE + "Plugins (1): " + net.md_5.bungee.api.ChatColor.of("#8d6acc") + "AmethystRealms" + ChatColor.GREEN + " v1.0.0 (Dev: LostAndDead)");
        return true;
    }
}
