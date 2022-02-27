package net.fruchtlabor.fruchtlaborjobs.jobs;

import org.bukkit.Bukkit;

import java.util.UUID;

public class JobPlayer {
    private String job;
    private int level;
    private double exp;
    private UUID uuid;
    private int perk_level;


    public JobPlayer(String job, int level, double exp, UUID uuid, int perk_level) {
        this.job = job;
        this.level = level;
        this.exp = exp;
        this.uuid = uuid;
        this.perk_level = perk_level;
    }

    public String getJob() {
        return job;
    }

    public int getLevel() {
        return level;
    }

    public double getExp() {
        return exp;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getPerk_level() {
        return perk_level;
    }

    public void addExp(double experience){
        this.exp += experience;
        int needed = 150 * level + (level*level*4);
        while (this.exp >= needed){
            addLevel(1);
            this.exp = this.exp - needed;
            needed = 150 * level + (level*level*4);
        }
    }

    public void addLevel(int lvl){
        this.level += lvl;
        addPlvl(lvl);
        Bukkit.getPlayer(uuid).sendMessage("["+job+"] LevelUp");
    }

    public void addPlvl(int plvl){
        this.perk_level += plvl;
    }

    public boolean removePlvl(int points) {
        if (this.perk_level - points >= 0) {
            this.perk_level = this.perk_level - points;
            return true;
        } else {
            return false;
        }
    }
}
