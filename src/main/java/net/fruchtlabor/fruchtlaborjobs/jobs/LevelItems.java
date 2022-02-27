package net.fruchtlabor.fruchtlaborjobs.jobs;

import org.bukkit.Material;

public class LevelItems {
    private Material material;
    private double exp;

    public LevelItems(Material material, double exp) {
        this.material = material;
        this.exp = exp;
    }

    public Material getMaterial() {
        return material;
    }

    public double getExp() {
        return exp;
    }
}
