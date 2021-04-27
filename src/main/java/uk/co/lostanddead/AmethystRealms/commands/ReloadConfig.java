package uk.co.lostanddead.AmethystRealms.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;

public class ReloadConfig implements CommandExecutor {

    public final AmethystRealmsCore core;

    public ReloadConfig(AmethystRealmsCore core){this.core = core;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        core.reloadConfig();
        core.defaultPack = core.getConfig().getString("defaultPack");
        core.defaultPackHash = core.getConfig().getString("defaultPackHash");
        core.noPack = core.getConfig().getString("noPack");
        core.noPackHash = core.getConfig().getString("noPackHash");
        core.devPack = core.getConfig().getString("devPack");
        core.devPackHash = core.getConfig().getString("devPackHash");
        for (Player p : Bukkit.getOnlinePlayers()){
            p.setResourcePack(core.defaultPack, core.decodeHexString(core.defaultPackHash));
        }
        return true;
    }
}
