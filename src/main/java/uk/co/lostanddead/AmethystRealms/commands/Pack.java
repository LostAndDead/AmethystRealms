package uk.co.lostanddead.AmethystRealms.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;

public class Pack implements CommandExecutor {

    private final AmethystRealmsCore core;

    public Pack(AmethystRealmsCore core){this.core = core;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){return false;}
        if(!(args.length <= 0)){
            Player p = (Player) sender;
            switch(args[0].toLowerCase()){
                case "default": {
                    p.setResourcePack(core.defaultPack, core.decodeHexString(core.defaultPackHash));
                    p.sendMessage("于 " + ChatColor.AQUA + "Loading Default Pack..." + ChatColor.RESET + " 于");
                    break;
                }
                case "none": {
                    p.setResourcePack(core.noPack, core.decodeHexString(core.noPackHash));
                    p.sendMessage("于 " + ChatColor.AQUA + "Loading Empty Pack..." + ChatColor.RESET + " 于");
                    break;
                }
                case "dev":{
                    if(core.devPack.equals("") || core.devPackHash.equals("")){
                        sender.sendMessage("也 " + ChatColor.RED + "No Dev Pack At This Time" + ChatColor.RESET + " 也");
                    }else{
                        p.setResourcePack(core.devPack, core.decodeHexString(core.devPackHash));
                        p.sendMessage("于 " + ChatColor.AQUA + "Loading Dev Pack..." + ChatColor.RESET + " 于");
                    }
                    break;
                }
                default: {
                    sender.sendMessage("也 " + ChatColor.RED + "Invalid Pack Type" + ChatColor.RESET + " 也");
                    sender.sendMessage(ChatColor.RED + "Valid Types Are: " + ChatColor.GRAY + "default, none, dev");
                    break;
                }
            }
            return true;
        }else{
            sender.sendMessage("也 " + ChatColor.RED + "Invalid Pack Type" + ChatColor.RESET + " 也");
            sender.sendMessage(ChatColor.RED + "Valid Types Are: " + ChatColor.GRAY + "default, none, dev");
        }
        return true;
    }
}
