package net.fruchtlabor.fruchtlaborjobs.commands.items;

import net.fruchtlabor.fruchtlaborjobs.FruchtLaborJobs;
import net.fruchtlabor.fruchtlaborjobs.jobs.Job;
import net.fruchtlabor.fruchtlaborjobs.jobs.LevelItems;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// /jobadmin create item exp job // item from mainhand

public class AddItem implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player){
            Player player = (Player) commandSender;
            if (player.hasPermission("jobs.admin")){
                if (strings.length == 4 && command.getName().equalsIgnoreCase("jobadmin")){
                    if (strings[0].equalsIgnoreCase("create") && strings[1].equalsIgnoreCase("item")){
                        double exp = 0.0;
                        try {
                            exp = Double.parseDouble(strings[2]);
                        }catch (NumberFormatException e){
                            player.sendMessage("Wert für experience 0.0X");
                            return false;
                        }
                        Job job = FruchtLaborJobs.databaseManager.getJobByName(strings[4]);
                        if (job!=null){
                            Material material = player.getInventory().getItemInMainHand().getType();
                            if (material == Material.AIR){
                                player.sendMessage("Du musst das Item in der Hand halten!");
                                return false;
                            }
                            LevelItems save = new LevelItems(material, exp);
                            FruchtLaborJobs.databaseManager.addItem(save, job.getName());
                            player.sendMessage("Item wurde hinzugefügt!");
                            return true;
                        }else{
                            player.sendMessage("Gibt den Job nicht!");
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }
}
