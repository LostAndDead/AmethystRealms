package uk.co.lostanddead.AmethystRealms.tabcomplete;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;

import java.util.ArrayList;
import java.util.List;

public class UnMuteTabComplete implements TabCompleter {

    private final AmethystRealmsCore core;

    public UnMuteTabComplete(AmethystRealmsCore core){this.core = core;}

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length == 1){
            List<String> pNames = new ArrayList<>();
            for (Player p : Bukkit.getOnlinePlayers()){
                pNames.add(p.getDisplayName());
            }
            return pNames;
        }else{
            List<String> empty = new ArrayList<>();
            empty.add("");
            return empty;
        }
    }
}
