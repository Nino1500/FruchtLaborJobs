package net.fruchtlabor.fruchtlaborjobs.database;

import net.fruchtlabor.fruchtlaborjobs.jobs.Job;
import net.fruchtlabor.fruchtlaborjobs.jobs.JobPlayer;
import net.fruchtlabor.fruchtlaborjobs.jobs.LevelItems;
import net.fruchtlabor.fruchtlaborjobs.jobs.LevelMobs;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.units.qual.A;

import java.util.*;

public class DatabaseManager {
    public Plugin plugin;
    public Database database;
    public ArrayList<Job> jobs;
    private HashMap<Job, ArrayList<JobPlayer>> jobArrayListHashMap;

    public DatabaseManager(Plugin plugin) {
        this.plugin = plugin;
        this.database = new Database(plugin);
        initializeDB();
    }

    private void initializeDB(){
        for (String job : plugin.getConfig().getStringList("Jobs.jobnames")){
            database.createTable(job);
        }
        database.createItemsTable();
        database.createMobsTable();
        initializeJobs();
        initPlayers();
        pushDatabase();
    }

    private ArrayList<Job> initializeJobs(){
        ArrayList<Job> jobs = new ArrayList<>();
        jobs.add(new Job("Minenarbeiter", 100, Material.STONE_PICKAXE, database.getItems("Minenarbeiter"), database.getMobs("Minenarbeiter")));
        jobs.add(new Job("Fischer", 100, Material.FISHING_ROD, database.getItems("Fischer"), database.getMobs("Fischer")));
        jobs.add(new Job("Farmer", 100, Material.GOLDEN_HOE, database.getItems("Farmer"), database.getMobs("Farmer")));
        jobs.add(new Job("Foerster", 100, Material.IRON_AXE, database.getItems("Foerster"), database.getMobs("Foerster")));
        jobs.add(new Job("Schatzsucher", 100, Material.WOODEN_SHOVEL, database.getItems("Schatzsucher"), database.getMobs("Schatzsucher")));
        return jobs;
    }

    private void initPlayers(){
        for (Job job : jobs){
            jobArrayListHashMap.put(job, database.getJobPlayers(job.getName()));
        }
    }

    public JobPlayer getJobPlayer(UUID uuid, String job){
        for (Map.Entry<Job, ArrayList<JobPlayer>> entry : jobArrayListHashMap.entrySet()){
            if (entry.getKey().getName().equalsIgnoreCase(job)){
                for (JobPlayer jobPlayer : entry.getValue()){
                    if (jobPlayer.getJob().equalsIgnoreCase(job) && jobPlayer.getUuid().equals(uuid)){
                        return jobPlayer;
                    }
                }
            }
        }
        return null;
    }

    private void pushDatabase(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                for (Map.Entry<Job, ArrayList<JobPlayer>> entry : jobArrayListHashMap.entrySet()){
                    database.updateAllPlayers(entry.getValue(), entry.getKey());
                }
            }
        },1200L,1200L);
    }

    public void safeOnExit(){
        for (Map.Entry<Job, ArrayList<JobPlayer>> entry : jobArrayListHashMap.entrySet()){
            database.updateAllPlayers(entry.getValue(), entry.getKey());
        }
    }

    public ArrayList<Job> getJobs() {
        return jobs;
    }

    public Job getJobByName(String name){
        for (Job job : jobs){
            if (job.getName().equalsIgnoreCase(name)) return job;
        }
        return null;
    }

    public void addItem(LevelItems item, String job){
        if (getJobByName(job).getItems().contains(item)){
            return;
        }
        database.addItem(item, job);
        getJobByName(job).getItems().add(item);
    }
    public void addMob(LevelMobs mob, String job){
        if (getJobByName(job).getMobs().contains(mob)){
            return;
        }
        database.addMob(mob, job);
        getJobByName(job).getMobs().add(mob);
    }
    public void removeItem(LevelItems item, String job){
        if (getJobByName(job).getItems().contains(item)){
            getJobByName(job).getItems().remove(item);
            database.deleteItem(item);
        }
    }
    public void removeMob(LevelMobs mob, String job){
        if (getJobByName(job).getMobs().contains(mob)){
            getJobByName(job).getMobs().remove(mob);
            database.deleteMob(mob);
        }
    }
    public boolean addPlayer(UUID uuid, Job job){
        if (getJobPlayer(uuid, job.getName()) != null){
            jobArrayListHashMap.get(job).add(new JobPlayer(job.getName(), 1, 0, uuid, 1));
            return true;
        }
        return false;
    }
}
