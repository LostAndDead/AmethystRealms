package uk.co.lostanddead.AmethystRealms.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;

public class Shop implements CommandExecutor {

    private final AmethystRealmsCore core;

    public Shop(AmethystRealmsCore core){this.core = core;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        uk.co.lostanddead.AmethystRealms.menus.Shop shop = new uk.co.lostanddead.AmethystRealms.menus.Shop(core);
        if(core.shopOpen){
            shop.open((Player) sender);
        }else{
            sender.sendMessage("也 " + ChatColor.RED + "The Shop Is Currently Closed" + ChatColor.RESET + " 也");
        }
        return true;
    }
}
