package uk.co.lostanddead.AmethystRealms.tabcomplete;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;

import java.util.ArrayList;
import java.util.List;

public class ReplyTabComplete implements TabCompleter {

    private final AmethystRealmsCore core;

    public ReplyTabComplete(AmethystRealmsCore core){this.core = core;}

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> empty = new ArrayList<>();
        if(args.length == 1){
            empty.add("Message");
        }else{
            empty.add("");
        }
        return empty;
    }

}
