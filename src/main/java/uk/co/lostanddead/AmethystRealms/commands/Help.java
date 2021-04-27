package uk.co.lostanddead.AmethystRealms.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;

public class Help implements CommandExecutor {

    private final AmethystRealmsCore core;

    public Help(AmethystRealmsCore core){this.core = core;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0){
            sender.sendMessage("");
            sender.sendMessage(net.md_5.bungee.api.ChatColor.of("#8d6acc") + "§lTopics");
            sender.sendMessage("");
            sender.sendMessage("§61. §cShop (NYI)");
            sender.sendMessage("§62. §7Communication");
            sender.sendMessage("§63. §7Premium");
            sender.sendMessage("§64. §7Other");
            sender.sendMessage("");
            sender.sendMessage("§7Do /help (Subtopic) for more info");
            sender.sendMessage("");
            return true;
        }

        switch (args[0].toLowerCase()){
            //case "1":
            //case "shop": {
            //    sender.sendMessage("");
            //    sender.sendMessage(net.md_5.bungee.api.ChatColor.of("#8d6acc") + "§l§cThe Shop (NYI)");
            //    sender.sendMessage("");
            //    sender.sendMessage("§7The shop opens every hour for 10 minutes, once it opens you will see a message in chat.");
            //    sender.sendMessage("");
            //    sender.sendMessage("§7You can spend your " + net.md_5.bungee.api.ChatColor.of("#8d6acc") + "Amethyst §7here, what items are available varies, but you can be sure you will always be able to get a map incase you lose yours.");
            //    sender.sendMessage("");
            //    return true;
            //}
            case "2":
            case "communication": {
                sender.sendMessage("");
                sender.sendMessage(net.md_5.bungee.api.ChatColor.of("#8d6acc") + "§lCommunication");
                sender.sendMessage("");
                sender.sendMessage("§7Link to Discord with §b/link");
                sender.sendMessage("");
                sender.sendMessage("§7Chat is global across the whole server.");
                sender.sendMessage("");
                sender.sendMessage("§7Player messages can also be seen above their head in-game, however this is limited to 50 characters to avoid spam.");
                sender.sendMessage("");
                sender.sendMessage("§7Profanity is blocked to the best of the servers ability but staff can mute anyone who works around it or breaks other rules.");
                sender.sendMessage("");
                sender.sendMessage("§b/msg (Player) (Message) §7Send messages to other players, /r can be used for a quick reply.");
                sender.sendMessage("");
                return true;
            }
            case "3":
            case "premium": {
                sender.sendMessage("");
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "§lPremium");
                sender.sendMessage("");
                sender.sendMessage("到 " + "§7users gain a few small things, nothing pay to win.");
                sender.sendMessage("");
                sender.sendMessage("§7You get a special prefix in chat and on the tab list.");
                sender.sendMessage("");
                sender.sendMessage("§7You can use color codes in chat.");
                sender.sendMessage("");
                sender.sendMessage("§7You can rename items with color codes in an anvil.");
                sender.sendMessage("");
                sender.sendMessage("§7You can also change your nickname on Discord.");
                sender.sendMessage("");
                sender.sendMessage("§b/hat §7Puts your held item on your head.");
                sender.sendMessage("");
                return true;
            }
            case "4":
            case "other": {
                sender.sendMessage("");
                sender.sendMessage(net.md_5.bungee.api.ChatColor.of("#8d6acc") + "§lOther");
                sender.sendMessage("");
                sender.sendMessage("§7You can relax and sit down on any chair (stairs)");
                sender.sendMessage("");
                sender.sendMessage("§7Nether portals can be many different shapes and sizes.");
                sender.sendMessage("");
                sender.sendMessage("§7Drop and ender pearl onto a wool block to turn it into an elevator.");
                sender.sendMessage("");
                sender.sendMessage("§7Players and mobs drop heads.");
                sender.sendMessage("");
                sender.sendMessage("§7Sign a book \"Statues\" to have it turn into a book you can use to manipulate armour statues.");
                sender.sendMessage("");
                sender.sendMessage("§7Right click a enchanting table with glass bottles to turn them into XP bottles.");
                sender.sendMessage("");
                sender.sendMessage("§7Smelt these bottles for lossless XP storage.");
                sender.sendMessage("");
                sender.sendMessage("§7Name a mob \"silence me\" to make it become silenced.");
                sender.sendMessage("");
                sender.sendMessage("§b/togglehud §7Toggles a neat little co-ordinate hud for ease of use.");
                sender.sendMessage("");
                return true;
            }
            default: {
                sender.sendMessage("");
                sender.sendMessage(net.md_5.bungee.api.ChatColor.of("#8d6acc") + "§lTopics");
                sender.sendMessage("");
                sender.sendMessage("§61. §7Shop");
                sender.sendMessage("§62. §7Communication");
                sender.sendMessage("§63. §7Premium");
                sender.sendMessage("§64. §7Other");
                sender.sendMessage("");
                return true;
            }
        }
    }
}
