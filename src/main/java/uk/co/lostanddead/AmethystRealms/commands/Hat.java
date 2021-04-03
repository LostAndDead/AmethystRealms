package uk.co.lostanddead.AmethystRealms.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;

public class Hat implements CommandExecutor {

    private final AmethystRealmsCore core;

    public Hat(AmethystRealmsCore core){this.core = core;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        PlayerInventory pinv = p.getInventory();
        ItemStack held = pinv.getItemInMainHand();
        ItemStack helmet = pinv.getHelmet();
        pinv.setHelmet(held);
        pinv.setItemInMainHand(helmet);
        p.sendMessage("于 " + ChatColor.AQUA + "Equipped Item" + ChatColor.RESET + " 于");
        return true;
    }
}
