package uk.co.lostanddead.AmethystRealms.handlers;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import uk.co.lostanddead.AmethystRealms.AmethystRealmsCore;

public class KickHandler {

    private final AmethystRealmsCore core;

    public KickHandler(AmethystRealmsCore core){this.core=core;}

    public void kick(Player p, String reason){
        p.kickPlayer(ChatColor.translateAlternateColorCodes('&', "&cYou Were Kicked!\n\n&8Reason:\n\n&7") +reason);
    }
}
