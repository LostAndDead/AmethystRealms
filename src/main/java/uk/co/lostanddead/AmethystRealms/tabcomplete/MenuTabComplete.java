package uk.co.lostanddead.AmethystRealms.tabcomplete;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;

import java.util.ArrayList;
import java.util.List;

public class MenuTabComplete implements TabCompleter {

    private final AmethystRealmsCore core;

    public MenuTabComplete(AmethystRealmsCore core){this.core = core;}

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length == 1){
            List<String> options = new ArrayList<>();
            options.add("welcome");
            options.add("shop");
            return options;
        }else{
            List<String> empty = new ArrayList<>();
            empty.add("");
            return empty;
        }
    }
}
