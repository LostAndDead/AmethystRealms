package uk.co.lostanddead.AmethystRealms;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class DiscordBot {

    private AmethystRealmsCore core;

    public DiscordBot(AmethystRealmsCore core){
        this.core = core;

        DiscordApi = new DiscordApiBuilder().setToken(core.Token)
    }
}
