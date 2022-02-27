package net.fruchtlabor.fruchtlaborjobs.jobs;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public class LevelMobs {
    private EntityType entity;
    private double exp;


    public LevelMobs(EntityType entity, double exp) {
        this.entity = entity;
        this.exp = exp;
    }

    public EntityType getEntity() {
        return entity;
    }

    public double getExp() {
        return exp;
    }
}
