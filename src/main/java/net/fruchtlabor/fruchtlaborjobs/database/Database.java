package net.fruchtlabor.fruchtlaborjobs.database;

import net.fruchtlabor.fruchtlaborjobs.jobs.Job;
import net.fruchtlabor.fruchtlaborjobs.jobs.JobPlayer;
import net.fruchtlabor.fruchtlaborjobs.jobs.LevelItems;
import net.fruchtlabor.fruchtlaborjobs.jobs.LevelMobs;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.units.qual.A;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Database {

    Plugin plugin;

    public Database(Plugin plugin) {
        this.plugin = plugin;
    }

    public Connection connect() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://"+plugin.getConfig().getString("MySQL.host")+":"+plugin.getConfig().getInt("MySQL.port")+"/"+plugin.getConfig().getString("MySQL.name"), plugin.getConfig().getString("MySQL.user"), plugin.getConfig().getString("MySQL.password"));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void createTable(String jobname){
        String sql = "CREATE TABLE IF NOT EXISTS " + jobname +
                "(uuid VARCHAR(255) not NULL," +
                "lvl int," +
                "exp double," +
                "plvl int," +
                "PRIMARY KEY (uuid))";
        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            conn.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createItemsTable(){
        String sql = "CREATE TABLE IF NOT EXISTS items (material VARCHAR(64) PRIMARY KEY, exp double, job VARCHAR(32))";
        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            conn.close();
            stmt.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void createMobsTable(){
        String sql = "CREATE TABLE IF NOT EXISTS mobs (entity VARCHAR(64) PRIMARY KEY, exp double, job VARCHAR(32))";
        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            conn.close();
            stmt.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateAllPlayers(ArrayList<JobPlayer> list, Job job){
        try {
            String sql = "INSERT INTO "+job.getName()+" VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE lvl= ?, exp= ?, plvl= ?";
            Connection connection = connect();
            PreparedStatement statement = connection.prepareStatement(sql);
            for (JobPlayer jobPlayer : list) {
                statement.setString(1, jobPlayer.getUuid().toString());
                statement.setInt(2, jobPlayer.getLevel());
                statement.setDouble(3, jobPlayer.getExp());
                statement.setInt(4, jobPlayer.getPerk_level());
                statement.setInt(5, jobPlayer.getLevel());
                statement.setDouble(6, jobPlayer.getExp());
                statement.setInt(7, jobPlayer.getPerk_level());
                statement.addBatch();
            }
            int[] numUpdates = statement.executeBatch();
            System.out.println("Saved: "+numUpdates.length+" Players! Job: "+job.getName());
            connection.close();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public ArrayList<JobPlayer> getJobPlayers(String job){
        ArrayList<JobPlayer> jobPlayers = new ArrayList<>();
        String sql = "SELECT * FROM "+job;
        Connection connection = connect();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()){
                jobPlayers.add(new JobPlayer(job, rs.getInt("lvl"), rs.getDouble("exp"), UUID.fromString(rs.getString("uuid")), rs.getInt("plvl")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return jobPlayers;
    }

    public ArrayList<LevelItems> getItems(String job){
        ArrayList<LevelItems> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM items WHERE job = "+job;
            Connection connection = connect();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()){
                list.add(new LevelItems(Material.matchMaterial(rs.getString("material")), rs.getDouble("exp")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<LevelMobs> getMobs(String job){
        ArrayList<LevelMobs> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM mobs WHERE job = "+job;
            Connection connection = connect();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()){
                list.add(new LevelMobs(EntityType.valueOf(rs.getString("entity")), rs.getDouble("exp")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    public void addItem(LevelItems item, String job){
        try {
            String sql = "INSERT INTO items VALUES (?,?,?)";
            Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, item.getMaterial().name());
            preparedStatement.setDouble(2, item.getExp());
            preparedStatement.setString(3, job);
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void addMob(LevelMobs mob, String job){
        try {
            String sql = "INSERT INTO mobs VALUES (?,?,?)";
            Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, mob.getEntity().name());
            preparedStatement.setDouble(2, mob.getExp());
            preparedStatement.setString(3, job);
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteItem(LevelItems item){
        try {
            String sql = "DELETE FROM items WHERE material = "+item.getMaterial().name();
            Connection connection = connect();
            Statement statement = connection.createStatement();
            statement.execute(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteMob(LevelMobs mob){
        try {
            String sql = "DELETE FROM mobs WHERE entity = "+mob.getEntity().name();
            Connection connection = connect();
            Statement statement = connection.createStatement();
            statement.execute(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


}
