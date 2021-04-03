package uk.co.lostanddead.AmethystRealms.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;

public class ToggleHud implements CommandExecutor {

    private final AmethystRealmsCore core;

    public ToggleHud(AmethystRealmsCore core){this.core = core;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(core.playersWithHud.contains(((Player) sender).getUniqueId())){
            core.playersWithHud.remove(((Player) sender).getUniqueId());
            core.getSQL().setHudEnabled((Player) sender, false);
            sender.sendMessage("于 " + ChatColor.AQUA + "Hud Disabled" + ChatColor.RESET + " 于");
        }else{
            core.playersWithHud.add(((Player) sender).getUniqueId());
            core.getSQL().setHudEnabled((Player) sender, true);
            sender.sendMessage("于 " + ChatColor.AQUA + "Hud Enabled" + ChatColor.RESET + " 于");
        }
        return true;
    }
}
