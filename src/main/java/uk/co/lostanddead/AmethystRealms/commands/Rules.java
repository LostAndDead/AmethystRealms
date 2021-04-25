package uk.co.lostanddead.AmethystRealms.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;
import uk.co.lostanddead.AmethystRealms.JSONMessage;

public class Rules implements CommandExecutor {

    private final AmethystRealmsCore core;

    public Rules(AmethystRealmsCore core){this.core = core;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(net.md_5.bungee.api.ChatColor.of("#8d6acc") + "Rules can be seen on our Discord");
        JSONMessage.create("于 " + ChatColor.AQUA + "https://discord.gg/EWMuhAkYkD").openURL("https://discord.gg/EWMuhAkYkD").send((Player) sender);
        return true;
    }
}
