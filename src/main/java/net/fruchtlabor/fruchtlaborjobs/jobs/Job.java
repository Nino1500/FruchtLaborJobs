package net.fruchtlabor.fruchtlaborjobs.jobs;

import org.bukkit.Material;

import java.util.ArrayList;

public class Job {
    private String name;
    private int maxLevel;
    private Material gui;
    private ArrayList<LevelItems> items;
    private ArrayList<LevelMobs> mobs;

    public Job(String name, int maxLevel, Material gui, ArrayList<LevelItems> items, ArrayList<LevelMobs> mobs) {
        this.name = name;
        this.maxLevel = maxLevel;
        this.gui = gui;
        this.items = items;
        this.mobs = mobs;
    }

    public String getName() {
        return name;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public Material getGui() {
        return gui;
    }

    public ArrayList<LevelItems> getItems() {
        return items;
    }

    public ArrayList<LevelMobs> getMobs() {
        return mobs;
    }
}
