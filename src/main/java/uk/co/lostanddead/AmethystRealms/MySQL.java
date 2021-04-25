package uk.co.lostanddead.AmethystRealms;

import org.apache.commons.lang.ObjectUtils;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

public class MySQL {

    private Connection connection;
    private AmethystRealmsCore core;

    public MySQL(AmethystRealmsCore core){
        this.core = core;
    }

    public boolean isConnected(){
        return(connection != null);
    }

    public void connect() throws ClassNotFoundException, SQLException {
        if (!isConnected()){
            String host = core.getConfig().getString("SQLHost");
            String port = core.getConfig().getString("SQLPort");
            String database = core.getConfig().getString("SQLDatabase");
            String username = core.getConfig().getString("SQLUsername");
            String password = core.getConfig().getString("SQLPassword");
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=true&autoReconnect=true", username, password);
        }
    }

    public void disconnect(){
        if (isConnected()){
            try{
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection(){
        return connection;
    }

    public void createPlayer(Player p){
        try{
            UUID uuid = p.getUniqueId();
            if(!exists(uuid)){
                PreparedStatement ps = getConnection().prepareStatement("INSERT IGNORE INTO playerdata " +
                        "(UUID) VALUES (?)");
                ps.setString(1, uuid.toString());
                ps.executeUpdate();

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void ping(){
        try{
            PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM playerdata");
            ResultSet result = ps.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean exists(UUID uuid){
        try{
            PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM playerdata WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet result = ps.executeQuery();
            return result.next();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean getWelcomed(Player p) {
        try{
            PreparedStatement ps = getConnection().prepareStatement("SELECT Welcomed FROM playerdata WHERE UUID=?");
            ps.setString(1, p.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            boolean Welcomed = false;
            if(rs.next()){
                Welcomed = rs.getBoolean("Welcomed");
            }
            return Welcomed;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public void setWelcomed(Player p, Boolean value){
        try{
            PreparedStatement ps = getConnection().prepareStatement("UPDATE playerdata SET Welcomed=? WHERE UUID=?");
            ps.setBoolean(1, value);
            ps.setString(2, p.getUniqueId().toString());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean getMuted(Player p){
        try{
            PreparedStatement ps = getConnection().prepareStatement("SELECT Muted FROM playerdata WHERE UUID=?");
            ps.setString(1, p.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            boolean Muted = false;
            if(rs.next()){
                Muted = rs.getBoolean("Muted");
            }
            return Muted;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public void setMuted(Player p, Boolean value){
        try{
            PreparedStatement ps = getConnection().prepareStatement("UPDATE playerdata SET Muted=? WHERE UUID=?");
            ps.setBoolean(1, value);
            ps.setString(2, p.getUniqueId().toString());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String getLastMessaged(Player p){
        try{
            PreparedStatement ps = getConnection().prepareStatement("SELECT LastMessaged FROM playerdata WHERE UUID=?");
            ps.setString(1, p.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            String recipient = null;
            if(rs.next()){
                recipient = rs.getString("LastMessaged");
            }
            return recipient;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void setLastMessaged(Player p, String value){
        try{
            PreparedStatement ps = getConnection().prepareStatement("UPDATE playerdata SET LastMessaged=? WHERE UUID=?");
            ps.setString(1, value);
            ps.setString(2, p.getUniqueId().toString());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean getHudEnabled(Player p) {
        try{
            PreparedStatement ps = getConnection().prepareStatement("SELECT HudEnabled FROM playerdata WHERE UUID=?");
            ps.setString(1, p.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            boolean Welcomed = false;
            if(rs.next()){
                Welcomed = rs.getBoolean("HudEnabled");
            }
            return Welcomed;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public void setHudEnabled(Player p, Boolean value){
        try{
            PreparedStatement ps = getConnection().prepareStatement("UPDATE playerdata SET HudEnabled=? WHERE UUID=?");
            ps.setBoolean(1, value);
            ps.setString(2, p.getUniqueId().toString());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String getDiscordIDFromUUID(Player p){
        try{
            PreparedStatement ps = getConnection().prepareStatement("SELECT ID FROM discordLinks WHERE UUID=?");
            ps.setString(1, p.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            String id = null;
            if(rs.next()){
                id = rs.getString("ID");
            }
            return id;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public String getUUIDFromDiscordID(long ID){
        try{
            PreparedStatement ps = getConnection().prepareStatement("SELECT UUID FROM discordLinks WHERE ID=?");
            ps.setLong(1, ID);
            ResultSet rs = ps.executeQuery();
            String id = null;
            if(rs.next()){
                id = rs.getString("UUID");
            }
            return id;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void linkDiscord(Player p, long ID){
        try{
            PreparedStatement ps = getConnection().prepareStatement("INSERT IGNORE INTO discordLinks " +
                    "(UUID, ID) VALUES (?, ?)");
            ps.setString(1, p.getUniqueId().toString());
            ps.setLong(2, ID);
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean isLinked(Player p){
        try{
            PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM discordLinks WHERE UUID=?");
            ps.setString(1, p.getUniqueId().toString());
            ResultSet result = ps.executeQuery();
            return result.next();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean isLinked(long ID){
        try{
            PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM discordLinks WHERE ID=?");
            ps.setLong(1, ID);
            ResultSet result = ps.executeQuery();
            return result.next();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public void removeLink(Player p){
        try{
            PreparedStatement ps = getConnection().prepareStatement("DELETE FROM discordLinks WHERE UUID=?");
            ps.setString(1, p.getUniqueId().toString());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
