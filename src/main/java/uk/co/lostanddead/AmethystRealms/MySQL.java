package uk.co.lostanddead.AmethystRealms;

import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

public class MySQL {

    private Connection connection;

    public boolean isConnected(){
        return(connection != null);
    }

    public void connect() throws ClassNotFoundException, SQLException {
        if (!isConnected()){
            String host = "node.lostanddead.co.uk";
            String port = "3306";
            String database = "s3_Testing";
            String username = "u3_6xGvLfx6LN";
            String password = "5nFxpwZH@Q1ElM.jdGuzHdye";
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=true", username, password);
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
                //ps.setBoolean(2, false);
                ps.executeUpdate();

            }
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
}
