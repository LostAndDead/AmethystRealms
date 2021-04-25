package uk.co.lostanddead.AmethystRealms;

import com.google.common.base.CharMatcher;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.Reaction;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Ban;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.Hashtable;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DiscordBot {

    private final AmethystRealmsCore core;
    private final DiscordApi api;

    public Hashtable<String, UUID> pendingLinks = new Hashtable<>();
    private final int deleteDelay = 5;
    private final int userDeleteDelay = 2;

    public DiscordApi getApi() {
        return api;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public DiscordBot(AmethystRealmsCore core){
        this.core = core;

        this.api = new DiscordApiBuilder()
                .setToken(core.getConfig().getString("DiscordBotToken")).setAllIntents()
                .login().join();

        //api.updateActivity(ActivityType.COMPETING, "Development");

        Bukkit.getLogger().info(api.createBotInvite());

        //api.addMessageCreateListener(event -> {
        //    if(event.getMessageAuthor().getId() == api.getClientId()) {
        //        return;
        //    }
        //    if (core.getSwearFilter().filterText(event.getMessageContent())){
        //        event.getMessage().delete();
        //    }
        //});

        api.addMessageCreateListener(event ->{
            if(event.getMessageAuthor().getId() == api.getClientId()) {
                return;
            }
            if (event.getMessageContent().equalsIgnoreCase("!setuphelp") && event.getMessageAuthor().getId() == core.getConfig().getLong("OwnerID")) {
                new MessageBuilder()
                        .setEmbed(new EmbedBuilder()
                                .setImage("https://lostanddead.co.uk/justtext.png")
                                .setColor(new java.awt.Color(141, 106, 204))
                        )
                        .send(event.getChannel());
                new MessageBuilder()
                        .setEmbed(new EmbedBuilder()
                                .setTitle("❓ HELP")
                                .setDescription("This channel is open to everyone, if you need help then drop a message here and a staff member will reach out to you shortly. This can be for anything related to the Discord or Minecraft server.")
                                .setColor(new java.awt.Color(141, 106, 204))
                        )
                        .send(event.getChannel());
            }
        });

        api.addMessageCreateListener(event ->{
            if(event.getMessageAuthor().getId() == api.getClientId()) {
                return;
            }
            if (event.getMessageContent().equalsIgnoreCase("!setupwaiting") && event.getMessageAuthor().getId() == core.getConfig().getLong("OwnerID")) {
                new MessageBuilder()
                        .setEmbed(new EmbedBuilder()
                                .setImage("https://lostanddead.co.uk/justtext.png")
                                .setColor(new java.awt.Color(141, 106, 204))
                        )
                        .send(event.getChannel());
                new MessageBuilder()
                        .setEmbed(new EmbedBuilder()
                                .setTitle("\uD83D\uDD50 WAITING")
                                .setDescription("We are still preparing to open, there is a small area for you to chill while we wait for the server to be ready. The server is currently set to release at 12:00-GMT on the 1st of May, the time may vary slightly but this is the current set time.")
                                .setColor(new java.awt.Color(141, 106, 204))
                        )
                        .send(event.getChannel());
            }
        });

        api.addMessageCreateListener(event ->{
            if(event.getMessageAuthor().getId() == api.getClientId()) {
                return;
            }
            if (event.getMessageContent().equalsIgnoreCase("!setuprules") && event.getMessageAuthor().getId() == core.getConfig().getLong("OwnerID")) {
                new MessageBuilder()
                        .setEmbed(new EmbedBuilder()
                                .setImage("https://lostanddead.co.uk/justtext.png")
                                .setColor(new java.awt.Color(141, 106, 204))
                        )
                        .send(event.getChannel());
                new MessageBuilder()
                        .setEmbed(new EmbedBuilder()
                                .setColor(new java.awt.Color(141, 106, 204))
                                .setTitle("play.amethystrealms.co.uk")
                                .setDescription("Amethyst Realms is a whitelisted invitational based server, it is built as a way to spend time with friends and make some amazing builds. Too many hours of my time has been poured into making this server as it is 100% custom coded, so please, enjoy the game and have fun. *-LostAndDead*")
                                .addField("1. :sparkling_heart: Respect", "> This server is a closed community so it is vital we respect the other players, regardless of the situation. Any arguments or differences in opinions should be settled through civil discussion.")
                                .addField("2. :shield: Inappropriate Content", "> We have a zero tolerance on inappropriate content (Swearing, pornographic and general offensive content) , all chats are moderated by humans and robots, robots (and humans) do make errors sometimes so be patient with them.")
                                .addField("3. :crossed_swords: Griefing and Stealing", "> As this is a closed community there is no Grief prevention system as it is assumed you respect the other players and their builds, There is however logs of everything so should something come up it can be dealt with.")
                                .addField("4. :exclamation: Punishments", "> Once again as this is a closed community there should be no need for punishments, you will be warned verbally when you break the rules, there are no official warnings or number of chances, Staff can and will ban/kick you when they choose to do so at their own desecration.")
                        )
                        .send(event.getChannel());
            }
        });

        api.addMessageCreateListener(event -> {
            if(event.getMessageAuthor().getId() == api.getClientId()) {
                return;
            }
            if (event.getMessageContent().equalsIgnoreCase("!setuplink") && event.getMessageAuthor().getId() == core.getConfig().getLong("OwnerID")) {
                new MessageBuilder()
                        .setEmbed(new EmbedBuilder()
                                .setImage("https://lostanddead.co.uk/justtext.png")
                                .setColor(new java.awt.Color(141, 106, 204))
                        )
                        .send(event.getChannel());
                new MessageBuilder()
                        .setEmbed(new EmbedBuilder()
                                .setTitle("\uD83D\uDD17 LINK ACCOUNTS")
                                .setDescription("To gain access to the Discord you must link your accounts, run `/link` in-game and send the code it gives you in this channel. Then the rest of the server will unlock for you.")
                                .setColor(new java.awt.Color(141, 106, 204))
                        )
                        .send(event.getChannel());
            }
        });

        api.addMessageCreateListener(event -> {
            if(event.getMessageAuthor().getId() == api.getClientId()) {
                return;
            }
            if (event.getMessageContent().equalsIgnoreCase("!setupwhitelist") && event.getMessageAuthor().getId() == core.getConfig().getLong("OwnerID")) {
                new MessageBuilder()
                        .setEmbed(new EmbedBuilder()
                                .setImage("https://lostanddead.co.uk/justtext.png")
                                .setColor(new java.awt.Color(141, 106, 204))
                        )
                        .send(event.getChannel());
                new MessageBuilder()
                        .setEmbed(new EmbedBuilder()
                                .setTitle("\uD83D\uDCC4 WHITELIST")
                                .setDescription("Before you can join the server you need to be whitelisted, send your Minecraft username here so a staff member can approve or deny you. This **does not** automatically link your accounts! This still has to be done in-game with `/link` this is just an extra conformation step.")
                                .setColor(new java.awt.Color(141, 106, 204))
                        )
                        .send(event.getChannel());
            }
        });

        api.addMessageCreateListener(event -> {
            if(event.getMessageAuthor().getId() == api.getClientId()) {
                return;
            }
            if (event.getMessageContent().equalsIgnoreCase("!setupnicks") && event.getMessageAuthor().getId() == core.getConfig().getLong("OwnerID")) {
                new MessageBuilder()
                        .setEmbed(new EmbedBuilder()
                                .setImage("https://lostanddead.co.uk/justtext.png")
                                .setColor(new java.awt.Color(141, 106, 204))
                        )
                        .send(event.getChannel());
                new MessageBuilder()
                        .setEmbed(new EmbedBuilder()
                                .setTitle("\uD83D\uDC8E NICKNAME")
                                .setDescription("Premium members can change their nickname, just send your desired nickname here and I will do my magic! Nickname changes only apply to Discord not Minecraft.")
                                .setColor(new java.awt.Color(141, 106, 204))
                        )
                        .send(event.getChannel());
            }
        });


        api.addMessageCreateListener(event -> {
            if(event.getMessageAuthor().getId() == api.getClientId()) {
                return;
            }
            if(event.getChannel().getId() == core.getConfig().getLong("WhitelistChannelID")){
                String removed = CharMatcher.anyOf("abcdefghijklmnoprstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_").removeFrom(event.getMessageContent());
                if ((event.getMessageContent().contains(" ") || event.getMessageContent().length() > 16) || removed.length() > 0){

                    Message msg = event.getChannel().sendMessage("<@"+ event.getMessageAuthor().getId() + "> The username you submitted is not valid, please try again.").join();

                    ScheduledExecutorService timer = api.getThreadPool().getScheduler();

                    timer.schedule((Runnable) msg::delete,deleteDelay, TimeUnit.SECONDS);

                    timer.schedule((Runnable) event.getMessage()::delete,userDeleteDelay, TimeUnit.SECONDS);

                    return;
                }
                event.getMessage().addReaction("\uD83D\uDEAB");
                event.getMessage().addReaction("✅");
            }
        });

        api.addMessageCreateListener(event -> {
            if(event.getMessageAuthor().getId() == api.getClientId()) {
                return;
            }
            if(event.getChannel().getId() == core.getConfig().getLong("LinkChannelID")){
                if(pendingLinks.containsKey(event.getMessageContent())){
                    Player p = Bukkit.getPlayer(pendingLinks.get(event.getMessageContent()));
                    User user = event.getMessageAuthor().asUser().get();
                    Server server = api.getServerById(core.getConfig().getLong("ServerID")).get();
                    Message msg;
                    ScheduledExecutorService timer = api.getThreadPool().getScheduler();
                    if (p == null){
                        msg = event.getChannel().sendMessage("<@" + event.getMessageAuthor().getId() + "> You don't seem to be online in Minecraft, please login and try again.").join();

                        timer.schedule((Runnable) msg::delete,deleteDelay, TimeUnit.SECONDS);
                        timer.schedule((Runnable) event.getMessage()::delete,userDeleteDelay, TimeUnit.SECONDS);

                        return;
                    }else{

                        msg = event.getChannel().sendMessage("<@" + event.getMessageAuthor().getId() + "> Linked to " + p.getName()).join();

                        timer.schedule((Runnable) msg::delete,deleteDelay, TimeUnit.SECONDS);
                        timer.schedule((Runnable) event.getMessage()::delete,userDeleteDelay, TimeUnit.SECONDS);

                        p.sendMessage("于 " + ChatColor.AQUA + "Linked to " + event.getMessageAuthor().getName() + ChatColor.RESET + " 于");

                        user.updateNickname(server, user.getName()+ " (" + p.getName() + ")");
                        Role role = api.getRoleById(core.getConfig().getLong("LinkedRoleID")).get();
                        user.addRole(role);
                        Role role2 = api.getRoleById(core.getConfig().getLong("WhitelistedRoleID")).get();
                        user.removeRole(role2);

                        if (p.hasPermission("smp.premium")){
                            Role rolePrem = api.getRoleById(core.getConfig().getLong("PremiumRoleID")).get();
                            user.addRole(rolePrem);
                        }
                        core.getSQL().linkDiscord(p, user.getId());
                        pendingLinks.remove(event.getMessageContent());
                    }
                }else{
                    Message msg = event.getChannel().sendMessage("<@"+ event.getMessageAuthor().getId() + "> That doesn't seem to be a valid code.").join();

                    ScheduledExecutorService timer = api.getThreadPool().getScheduler();

                    timer.schedule((Runnable) msg::delete,deleteDelay, TimeUnit.SECONDS);
                    timer.schedule((Runnable) event.getMessage()::delete,userDeleteDelay, TimeUnit.SECONDS);
                }


            }
        });

        api.addReactionAddListener(event -> {
            if(event.getUser().get().getId() == api.getClientId()) {
                return;
            }
            if(event.getChannel().getId() == core.getConfig().getLong("WhitelistChannelID")){
                long id = event.getUser().get().getId();
                if (Objects.requireNonNull(core.getConfig().getList("AllowedToWhitelist")).contains(id)){
                    Reaction reaction = event.getReaction().get();
                    Emoji emoji = reaction.getEmoji();
                    if (emoji.equalsEmoji("✅")){
                        Bukkit.getScheduler().runTaskLater(core, () -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "whitelist add " + event.getMessageContent().get()), 1);

                        Role role = api.getRoleById(core.getConfig().getLong("WhitelistedRoleID")).get();
                        event.getMessageAuthor().get().asUser().get().addRole(role);

                        event.getMessage().get().getUserAuthor().get().sendMessage("You have now been whitelisted with the username **" + event.getMessageContent().get() + "** if this is not your username then please contact staff.");
                        event.getMessage().get().delete();
                    }else if(emoji.equalsEmoji("\uD83D\uDEAB")){
                        event.getMessage().get().getUserAuthor().get().sendMessage("Your whitelist application has been declined.");
                        event.getMessage().get().delete();
                    }
                }else{
                    event.getReaction().get().remove();
                }
            }
        });

        api.addMessageCreateListener(event -> {
            if(event.getMessageAuthor().getId() == api.getClientId()) {
                return;
            }
            if(event.getChannel().getId() == core.getConfig().getLong("ChatChannelID")){
                ScheduledExecutorService timer = api.getThreadPool().getScheduler();
                if (!(core.getSQL().isLinked(event.getMessageAuthor().getId()))){
                    Message msg = event.getChannel().sendMessage("<@"+ event.getMessageAuthor().getId() + "> You must be linked the send messages.").join();

                    timer.schedule((Runnable) msg::delete,deleteDelay, TimeUnit.SECONDS);
                    event.getMessage().delete();
                    return;
                }
                UUID uuid = UUID.fromString(core.getSQL().getUUIDFromDiscordID(event.getMessageAuthor().getId()));
                Player p = Bukkit.getPlayer(uuid);
                if (p == null){
                    Message msg = event.getChannel().sendMessage("<@"+ event.getMessageAuthor().getId() + "> You must be online in Minecraft to chat.").join();

                    timer.schedule((Runnable) msg::delete,deleteDelay, TimeUnit.SECONDS);
                    event.getMessage().delete();
                    return;
                }
                if (core.getSwearFilter().filterText(event.getMessageContent())){
                    event.getMessage().delete();
                    return;
                }else{
                    Bukkit.getScheduler().runTaskLater(core, () -> p.chat(event.getMessageContent()), 0);
                }
                event.getMessage().delete();
            }
        });

        api.addMessageCreateListener(event -> {
            if(event.getMessageAuthor().getId() == api.getClientId()) {
                return;
            }
            if(event.getChannel().getId() == core.getConfig().getLong("HelpChannelID")){
                Channel channel = api.getChannelById(core.getConfig().getLong("HelpLogChannelID")).get();
                new MessageBuilder()
                        .setEmbed(new EmbedBuilder()
                                .setAuthor(event.getMessageAuthor().getName(), "https://lostanddead.co.uk", event.getMessageAuthor().getAvatar())
                                .setDescription(event.getMessageContent())
                        )
                        .send((TextChannel) channel);
                Message msg = event.getChannel().sendMessage("<@"+ event.getMessageAuthor().getId() + "> Staff will contact you shortly").join();

                ScheduledExecutorService timer = api.getThreadPool().getScheduler();

                timer.schedule((Runnable) msg::delete,deleteDelay, TimeUnit.SECONDS);
                timer.schedule((Runnable) event.getMessage()::delete,userDeleteDelay, TimeUnit.SECONDS);
            }
        });

        api.addMessageCreateListener(event -> {
            if(event.getMessageAuthor().getId() == api.getClientId()) {
                return;
            }
            if(event.getChannel().getId() == core.getConfig().getLong("NicknameChannelID")){
                if (!(core.getSQL().isLinked(event.getMessageAuthor().getId()))){
                    Message msg = event.getChannel().sendMessage("<@"+ event.getMessageAuthor().getId() + "> You must be linked to change your nickname.").join();

                    ScheduledExecutorService timer = api.getThreadPool().getScheduler();

                    timer.schedule((Runnable) msg::delete,deleteDelay, TimeUnit.SECONDS);
                    timer.schedule((Runnable) event.getMessage()::delete,userDeleteDelay, TimeUnit.SECONDS);
                    return;
                }
                String removed = CharMatcher.anyOf("abcdefghijklmnoprstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!\"£&$%^&*()-=_+\\,./|<>?[];'#{}:@~ ").removeFrom(event.getMessageContent());
                if (event.getMessageContent().length() > 10 || core.getSwearFilter().filterText(event.getMessageContent()) || removed.length() > 0){
                    Message msg = event.getChannel().sendMessage("<@"+ event.getMessageAuthor().getId() + "> Invalid nickname.").join();

                    ScheduledExecutorService timer = api.getThreadPool().getScheduler();

                    timer.schedule((Runnable) msg::delete,deleteDelay, TimeUnit.SECONDS);
                    timer.schedule((Runnable) event.getMessage()::delete,userDeleteDelay, TimeUnit.SECONDS);
                    return;
                }

                Player p = Bukkit.getPlayer(UUID.fromString(core.getSQL().getUUIDFromDiscordID(event.getMessageAuthor().getId())));

                if(p == null){
                    Message msg = event.getChannel().sendMessage("<@"+ event.getMessageAuthor().getId() + "> You must be online in Minecraft to change your nickname").join();

                    ScheduledExecutorService timer = api.getThreadPool().getScheduler();

                    timer.schedule((Runnable) msg::delete,deleteDelay, TimeUnit.SECONDS);
                    timer.schedule((Runnable) event.getMessage()::delete,userDeleteDelay, TimeUnit.SECONDS);
                    return;
                }

                //p.setDisplayName(event.getMessageContent());

                User user = event.getMessageAuthor().asUser().get();
                Server server = api.getServerById(core.getConfig().getLong("ServerID")).get();
                user.updateNickname(server, event.getMessageContent()+ " (" + p.getName() + ")");

                Message msg = event.getChannel().sendMessage("<@"+ event.getMessageAuthor().getId() + "> Nickname changed.").join();

                ScheduledExecutorService timer = api.getThreadPool().getScheduler();

                timer.schedule((Runnable) msg::delete,deleteDelay, TimeUnit.SECONDS);
                timer.schedule((Runnable) event.getMessage()::delete,userDeleteDelay, TimeUnit.SECONDS);
            }
        });
    }

    public void createPendingLink (String code, Player p){
        pendingLinks.put(code, p.getUniqueId());
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public void removeLink(Player p){
        User user = null;
        try {
            user = api.getUserById(core.getSQL().getDiscordIDFromUUID(p)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (user == null){
            p.sendMessage("也 " + ChatColor.RED + "There was an error, contact staff!" + ChatColor.RESET + " 也");
            return;
        }

        Role role = api.getRoleById(core.getConfig().getLong("LinkedRoleID")).get();
        Role role2 = api.getRoleById(core.getConfig().getLong("WhitelistedRoleID")).get();
        Role role3 = api.getRoleById(core.getConfig().getLong("PremiumRoleID")).get();
        Server server = api.getServerById(core.getConfig().getLong("ServerID")).get();

        user.removeRole(role);
        user.addRole(role2);
        user.removeRole(role3);
        user.resetNickname(server);

        user.sendMessage("Your link has been removed, if this was not you please contact and admin immediately");

        p.sendMessage("也 " + ChatColor.RED + "UnLink Confirmed" + ChatColor.RESET + " 也");

        core.getSQL().removeLink(p);
    }

    public String getDiscordUsername(Player p){
        User user = null;
        try {
            user = api.getUserById(core.getSQL().getDiscordIDFromUUID(p)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (user == null){
            return null;
        }

        return user.getName();
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public void sendDiscordChat(Player p, String message){
        Channel channel = api.getChannelById(core.getConfig().getLong("ChatChannelID")).get();
        String url = "https://crafatar.com/avatars/" + p.getUniqueId().toString() + "?overlay";
        new MessageBuilder()
                .setEmbed(new EmbedBuilder()
                        .setAuthor(p.getDisplayName(),"https://lostanddead.co.uk", url)
                        .setDescription(message)
                        .setColor(core.getColor(p))
                )
                .send((TextChannel) channel);
    }

    public void banDiscord(Player p){
        User user = null;
        try {
            user = api.getUserById(core.getSQL().getDiscordIDFromUUID(p)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        Server server = api.getServerById(core.getConfig().getLong("ServerID")).get();
        server.banUser(user);
    }
}
