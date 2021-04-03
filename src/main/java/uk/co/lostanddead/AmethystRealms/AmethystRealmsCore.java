package uk.co.lostanddead.AmethystRealms;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import uk.co.lostanddead.AmethystRealms.commands.*;
import uk.co.lostanddead.AmethystRealms.handlers.shopInventoryHandler;
import uk.co.lostanddead.AmethystRealms.listeners.*;
import uk.co.lostanddead.AmethystRealms.handlers.ChatManager;
import uk.co.lostanddead.AmethystRealms.handlers.welcomeInventoryHandler;
import uk.co.lostanddead.AmethystRealms.handlers.KickHandler;
import uk.co.lostanddead.AmethystRealms.tabcomplete.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

public final class AmethystRealmsCore extends JavaPlugin {

    public MySQL SQL;
    public KickHandler Kicker;
    public SwearWordFinder Filter;
    public boolean shopOpen = false;

    public String defaultPack = "https://www.dropbox.com/s/pza073sh69zrc0f/AmethystRealms.zip?dl=1";
    public String defaultPackHash = "1B8D36CF94A2A68799C97D367EBCEBA8FAE59688";
    public String noPack = "https://www.dropbox.com/s/3m85p59gmky347d/Blank%20Pack.zip?dl=1";
    public String noPackHash = "E4245B80F7519424E3393B38733900BEBEE469DE";
    public String devPack = "";
    public String devPackHash = "";

    public List<Location> seatsTaken = new ArrayList<>();
    public Hashtable<UUID, ArmorStand> playerMessages = new Hashtable<>();
    public List<UUID> playersJoining = new ArrayList<>();
    public List<UUID> playersWithHud = new ArrayList<>();

    @Override
    public void onEnable() {
        this.SQL = new MySQL();
        this.Kicker = new KickHandler(this);
        this.Filter = new SwearWordFinder();
        Filter.loadConfigs();

        try {
            SQL.connect();
        } catch (ClassNotFoundException | SQLException e) {
            //e.printStackTrace();
            Bukkit.getLogger().warning("Database not connected");
        }

        if(SQL.isConnected()){
            Bukkit.getLogger().info("Database connected");
        }

        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(new playerSit(this), this);
        Bukkit.getPluginManager().registerEvents(new playerUnSit(this), this);
        Bukkit.getPluginManager().registerEvents(new onJoin(this), this);
        Bukkit.getPluginManager().registerEvents(new welcomeInventoryHandler(this), this);
        Bukkit.getPluginManager().registerEvents(new shopInventoryHandler(this), this);
        Bukkit.getPluginManager().registerEvents(new ChatManager(this), this);
        Bukkit.getPluginManager().registerEvents(new anvilRename(this), this);
        Bukkit.getPluginManager().registerEvents(new onLeave(this), this);
        Bukkit.getPluginManager().registerEvents(new serverPing(this), this);

        //tabPackets tp = new tabPackets(this);

        this.getCommand("mute").setExecutor(new Mute(this));
        this.getCommand("mute").setTabCompleter(new MuteTabComplete(this));

        this.getCommand("unmute").setExecutor(new UnMute(this));
        this.getCommand("unmute").setTabCompleter(new UnMuteTabComplete(this));

        this.getCommand("broadcast").setExecutor(new Broadcast(this));
        this.getCommand("broadcast").setTabCompleter(new BroadcastTabComplete(this));

        this.getCommand("pack").setExecutor(new Pack(this));
        this.getCommand("pack").setTabCompleter(new PackTabComplete(this));

        this.getCommand("menu").setExecutor(new Menus(this));
        this.getCommand("menu").setTabCompleter(new MenuTabComplete(this));

        this.getCommand("help").setExecutor(new Help(this));
        this.getCommand("help").setTabCompleter(new HelpTabComplete(this));

        this.getCommand("plugins").setExecutor(new Plugins(this));
        this.getCommand("plugins").setTabCompleter(new BlankTabComplete(this));

        this.getCommand("kick").setExecutor(new Kick(this));
        this.getCommand("kick").setTabCompleter(new KickTabComplete(this));

        this.getCommand("msg").setExecutor(new Msg(this));
        this.getCommand("msg").setTabCompleter(new MsgTabComplete(this));

        this.getCommand("reply").setExecutor(new Reply(this));
        this.getCommand("reply").setTabCompleter(new ReplyTabComplete(this));

        this.getCommand("shop").setExecutor(new Shop(this));
        this.getCommand("shop").setTabCompleter(new BlankTabComplete(this));

        this.getCommand("openshop").setExecutor(new OpenShop(this));
        this.getCommand("openshop").setTabCompleter(new BlankTabComplete(this));

        this.getCommand("togglehud").setExecutor(new ToggleHud(this));
        this.getCommand("togglehud").setTabCompleter(new BlankTabComplete(this));

        this.getCommand("hat").setExecutor(new Hat(this));
        this.getCommand("hat").setTabCompleter(new BlankTabComplete(this));

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (UUID uuid : playersWithHud){
                    Player p = Bukkit.getPlayer(uuid);
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§6XYZ §f" + (int) p.getLocation().getX() + " " + (int) p.getLocation().getY() + " " + (int) p.getLocation().getZ()));
                }
            }
        }, 10L, 5l);
    }

    @Override
    public void onDisable() {
        SQL.disconnect();
        // Plugin shutdown logic
        for (UUID key : playerMessages.keySet()){
            ArmorStand stand = playerMessages.get(key);
            stand.remove();
        }
    }

    public MySQL getSQL(){
        return SQL;
    }

    public KickHandler getKicker(){return Kicker;}

    public SwearWordFinder getSwearFilter(){return Filter;}

    public String getPrefix(Player p){
        String prefix;
        if(p.hasPermission("smp.owner")){
            prefix = "선 " + ChatColor.RED;
        }else if(p.hasPermission("smp.admin")){
            prefix = "我 " + ChatColor.DARK_PURPLE;
        }else if(p.hasPermission("smp.mod")){
            prefix = "有 " + ChatColor.AQUA;
        }else if(p.hasPermission("smp.premium")){
            prefix = "到 " + ChatColor.LIGHT_PURPLE;
        }else{
            prefix = "섚 " + ChatColor.GOLD;
        }

        return prefix;
    }

    public byte[] decodeHexString(String hexString) {
        if (hexString.length() % 2 == 1) {
            throw new IllegalArgumentException(
                    "Invalid hexadecimal String supplied.");
        }

        byte[] bytes = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i += 2) {
            bytes[i / 2] = hexToByte(hexString.substring(i, i + 2));
        }
        return bytes;
    }

    private byte hexToByte(String hexString) {
        int firstDigit = toDigit(hexString.charAt(0));
        int secondDigit = toDigit(hexString.charAt(1));
        return (byte) ((firstDigit << 4) + secondDigit);
    }

    private int toDigit(char hexChar) {
        int digit = Character.digit(hexChar, 16);
        if(digit == -1) {
            throw new IllegalArgumentException(
                    "Invalid Hexadecimal Character: "+ hexChar);
        }
        return digit;
    }

    //@SuppressWarnings("unchecked")
    //public void changeName(String name, Player player) {
    //    try {
    //        Method getHandle = player.getClass().getMethod("getHandle");
    //        Object entityPlayer = getHandle.invoke(player);
    //        /*
    //         * These methods are no longer needed, as we can just access the
    //         * profile using handle.getProfile. Also, because we can just use
    //         * the method, which will not change, we don't have to do any
    //         * field-name look-ups.
    //         */
    //        boolean gameProfileExists = false;
    //        // Some 1.7 versions had the GameProfile class in a different package
    //        try {
    //            Class.forName("net.minecraft.util.com.mojang.authlib.GameProfile");
    //            gameProfileExists = true;
    //        } catch (ClassNotFoundException ignored) {
    //
    //        }
    //        try {
    //            Class.forName("com.mojang.authlib.GameProfile");
    //            gameProfileExists = true;
    //        } catch (ClassNotFoundException ignored) {
    //
    //        }
    //        if (!gameProfileExists) {
    //            /*
    //             * Only 1.6 and lower servers will run this code.
    //             *
    //             * In these versions, the name wasn't stored in a GameProfile object,
    //             * but as a String in the (final) name field of the EntityHuman class.
    //             * Final (non-static) fields can actually be modified by using
    //             * {@link java.lang.reflect.Field#setAccessible(boolean)}
    //             */
    //            Field nameField = entityPlayer.getClass().getSuperclass().getDeclaredField("name");
    //            nameField.setAccessible(true);
    //            nameField.set(entityPlayer, name);
    //        } else {
    //            // Only 1.7+ servers will run this code
    //            Object profile = null;
    //            try {
    //                profile = entityPlayer.getClass().getMethod("getProfile").invoke(entityPlayer);
    //            } catch (InvocationTargetException e) {
    //                e.printStackTrace();
    //            }
    //            Field ff = profile.getClass().getDeclaredField("name");
    //            ff.setAccessible(true);
    //            ff.set(profile, name);
    //        }
    //        // In older versions, Bukkit.getOnlinePlayers() returned an Array instead of a Collection.
    //        if (Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).getReturnType() == Collection.class) {
    //            Collection<? extends Player> players = (Collection<? extends Player>) Bukkit.class.getMethod("getOnlinePlayers").invoke(null);
    //            for (Player p : players) {
    //                p.hidePlayer(this, player);
    //                p.showPlayer(this, player);
    //            }
    //        } else {
    //            Player[] players = ((Player[]) Bukkit.class.getMethod("getOnlinePlayers").invoke(null));
    //            for (Player p : players) {
    //                p.hidePlayer(this, player);
    //                p.showPlayer(this, player);
    //            }
    //        }
    //    } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
    //        /*
    //         * Merged all the exceptions. Less lines
    //         */
    //        e.printStackTrace();
    //    }
    //}
}
