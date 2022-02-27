package net.fruchtlabor.fruchtlaborjobs.commands.jobs;

import net.fruchtlabor.fruchtlaborjobs.FruchtLaborJobs;
import net.fruchtlabor.fruchtlaborjobs.jobs.Job;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddJob implements CommandExecutor {
    // /job add Minenarbeiter Ninoo
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("jobs") || command.getName().equalsIgnoreCase("job")){
            if (strings.length == 3 && commandSender.hasPermission("jobs.admin")){
                if (strings[0].equalsIgnoreCase("add")){
                    Job job = FruchtLaborJobs.databaseManager.getJobByName(strings[1]);
                    if (job != null){
                        Player player = Bukkit.getPlayer(strings[2]);
                        if (player!=null){
                            if (FruchtLaborJobs.databaseManager.addPlayer(player.getUniqueId(), job)){
                                player.sendMessage("Du bist dem Job: "+job.getName()+" beigetreten!");
                                return true;
                            }else{
                                player.sendMessage("Du hast den Job schon!");
                            }
                        }
                    }
                }
                commandSender.sendMessage("Etwas ist beim Adden schiefgelaufen!");
                return false;
            }
        }
        return false;
    }
}
