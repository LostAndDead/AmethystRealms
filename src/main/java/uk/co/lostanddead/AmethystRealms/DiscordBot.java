package uk.co.lostanddead.AmethystRealms;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.yaml.snakeyaml.events.Event;

import java.awt.*;
import java.util.Hashtable;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class DiscordBot {

    private AmethystRealmsCore core;
    private DiscordApi api;

    public Hashtable<String, UUID> pendingLinks = new Hashtable<>();

    public DiscordApi getApi() {
        return api;
    }

    public DiscordBot(AmethystRealmsCore core){
        this.core = core;

        this.api = new DiscordApiBuilder()
                .setToken(core.getConfig().getString("DiscordBotToken"))
                .login().join();

        Bukkit.getLogger().info(api.createBotInvite());

        api.addMessageCreateListener(event -> {
            if(event.getMessageAuthor().getId() == api.getClientId()) {
                return;
            }
            if(event.getChannel().getId() == core.getConfig().getLong("LinkChannelID")){
                if(pendingLinks.containsKey(event.getMessageContent())){
                    Player p = Bukkit.getPlayer(pendingLinks.get(event.getMessageContent()));
                    User user = event.getMessageAuthor().asUser().get();
                    Server server = api.getServerById(core.getConfig().getLong("ServerID")).get();
                    if (p == null){
                        new MessageBuilder()
                                .setEmbed(new EmbedBuilder()
                                        .setTitle("Link Failed")
                                        .setDescription("I cant seem to find you in-game")
                                        .setColor(Color.RED)
                                        .setTimestampToNow()
                                )
                                .send(user);
                    }else{
                        new MessageBuilder()
                                .setEmbed(new EmbedBuilder()
                                        .setTitle("Link Successful")
                                        .setDescription("Linked to " + p.getName())
                                        .setColor(Color.GREEN)
                                        .setTimestampToNow()
                                )
                                .send(user);

                        p.sendMessage("于 " + ChatColor.AQUA + "Linked to " + event.getMessageAuthor().getName() + ChatColor.RESET + " 于");

                        user.updateNickname(server, p.getName());
                        Role role = api.getRoleById(core.getConfig().getLong("LinkedRoleID")).get();
                        user.addRole(role);
                        core.getSQL().linkDiscord(p, user.getId());
                        pendingLinks.remove(event.getMessageContent());
                    }
                }
                event.getMessage().delete();
            }
        });

        api.addMessageCreateListener(event -> {
            if (event.getMessageContent().equalsIgnoreCase("!setuplink") && event.getMessageAuthor().getId() == core.getConfig().getLong("OwnerID")) {
                new MessageBuilder()
                        .setEmbed(new EmbedBuilder()
                                .setTitle("Discord / Minecraft Linking")
                                .setDescription("To gain access to the Discord you must link your accounts, run `/link` in-game and send the code it gives you in this channel. Then the rest of the server will unlock for you.")
                                .setColor(new java.awt.Color(141, 106, 204))
                        )
                        .send(event.getChannel());
            }
        });

        api.addMessageCreateListener(event -> {
            if(event.getChannel().getId() == core.getConfig().getLong("ChatChannelID")){
                if (event.getMessageAuthor().getId() == api.getClientId()){ return; }
                event.getMessage().delete();
                if (!(core.getSQL().isLinked(event.getMessageAuthor().getId()))){
                    new MessageBuilder()
                            .setEmbed(new EmbedBuilder()
                                    .setTitle("Error")
                                    .setDescription("You must be linked to send messages.")
                                    .setColor(Color.RED)
                                    .setTimestampToNow()
                            )
                            .send(event.getMessageAuthor().asUser().get());
                    return;
                }
                UUID uuid = UUID.fromString(core.getSQL().getUUIDFromDiscordID(event.getMessageAuthor().getId()));
                Player p = Bukkit.getPlayer(uuid);
                if (p == null){
                    new MessageBuilder()
                            .setEmbed(new EmbedBuilder()
                                    .setTitle("Error")
                                    .setDescription("You must be online in Minecraft to chat.")
                                    .setColor(Color.RED)
                                    .setTimestampToNow()
                            )
                            .send(event.getMessageAuthor().asUser().get());
                    return;
                }
                Bukkit.getScheduler().runTaskLater(core, new Runnable() {
                    @Override
                    public void run() {
                        p.chat(event.getMessageContent());
                    }
                }, 0);

            }
        });
    }

    public void createPendingLink (String code, Player p){
        pendingLinks.put(code, p.getUniqueId());
    }

    public void removeLink(Player p){
        User user = null;
        try {
            user = api.getUserById(core.getSQL().getDiscordIDFromUUID(p)).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (user == null){
            p.sendMessage("也 " + ChatColor.RED + "There was an error, contact staff!" + ChatColor.RESET + " 也");
            return;
        }

        Role role = api.getRoleById(core.getConfig().getLong("LinkedRoleID")).get();
        Server server = api.getServerById(core.getConfig().getLong("ServerID")).get();

        user.removeRole(role);
        user.resetNickname(server);

        new MessageBuilder()
                .setEmbed(new EmbedBuilder()
                        .setTitle("Link Removed")
                        .setDescription("Your link has been removed, if this was not you please contact and admin immediately")
                        .setColor(Color.RED)
                        .setTimestampToNow()
                )
                .send(user);

        p.sendMessage("也 " + ChatColor.RED + "UnLink Confirmed" + ChatColor.RESET + " 也");

        core.getSQL().removeLink(p);
    }

    public String getDiscordUsername(Player p){
        User user = null;
        try {
            user = api.getUserById(core.getSQL().getDiscordIDFromUUID(p)).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (user == null){
            return null;
        }

        return user.getName();
    }

    public void sendDiscordChat(Player p, String message){
        Channel channel = api.getChannelById(core.getConfig().getLong("ChatChannelID")).get();
        String url = "https://crafatar.com/avatars/" + p.getUniqueId().toString();
        new MessageBuilder()
                .setEmbed(new EmbedBuilder()
                        .setAuthor(p.getName(),"https://lostanddead.co.uk", url)
                        .setDescription(message)
                        .setColor(core.getColor(p))
                )
                .send((TextChannel) channel);
    }
}
