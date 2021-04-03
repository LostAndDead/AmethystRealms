package uk.co.lostanddead.AmethystRealms.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;
import uk.co.lostanddead.AmethystRealms.menus.Shop;
import uk.co.lostanddead.AmethystRealms.menus.Welcome;

public class Menus implements CommandExecutor {

    private final AmethystRealmsCore core;

    public Menus(AmethystRealmsCore core){this.core = core;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){return false;}
        if(!(args.length <= 0)){
            Player p = (Player) sender;
            switch(args[0].toLowerCase()){
                case "welcome":{
                    Welcome welcome = new Welcome(core);
                    welcome.open(p);
                    break;
                }
                case "shop": {
                    Shop shop = new Shop(core);
                    shop.open(p);
                    break;
                }
                default: {
                    sender.sendMessage("也 " + ChatColor.RED + "Invalid Menu" + ChatColor.RESET + " 也");
                    break;
                }
            }
            return true;
        }else{
            sender.sendMessage("也 " + ChatColor.RED + "Invalid Menu" + ChatColor.RESET + " 也");
        }
        return true;
    }
}
