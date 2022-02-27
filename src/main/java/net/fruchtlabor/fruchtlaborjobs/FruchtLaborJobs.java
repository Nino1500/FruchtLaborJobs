package net.fruchtlabor.fruchtlaborjobs;

import net.fruchtlabor.fruchtlaborjobs.commands.items.AddItem;
import net.fruchtlabor.fruchtlaborjobs.commands.jobs.AddJob;
import net.fruchtlabor.fruchtlaborjobs.database.DatabaseManager;
import org.bukkit.plugin.java.JavaPlugin;

public class FruchtLaborJobs extends JavaPlugin {

    public static DatabaseManager databaseManager;

    @Override
    public void onEnable() {

        databaseManager = new DatabaseManager(this);

        this.getCommand("job").setExecutor(new AddJob());
        this.getCommand("jobs").setExecutor(new AddJob());
        this.getCommand("job").setExecutor(new AddItem());
        this.getCommand("jobs").setExecutor(new AddItem());

    }

    @Override
    public void onDisable() {
        databaseManager.safeOnExit();
    }
}
