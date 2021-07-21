package uk.co.lostanddead.AmethystRealms.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;

public class Rules implements CommandExecutor {

    private final AmethystRealmsCore core;

    public Rules(AmethystRealmsCore core){this.core = core;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(net.md_5.bungee.api.ChatColor.of("#8d6acc") + "Rules can be seen on our Discord");
        TextComponent msg = new TextComponent(ChatColor.AQUA + "https://discord.gg/EWMuhAkYkD");
        msg.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/EWMuhAkYkD"));
        sender.spigot().sendMessage(msg);
        return true;
    }
}
