package me.stephenminer.endereyepointer.commands;

import me.stephenminer.endereyepointer.EnderEyePointer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RemoveEyeTarget implements CommandExecutor, TabCompleter {
    private final EnderEyePointer plugin;

    public RemoveEyeTarget(EnderEyePointer plugin){
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("removeEyeTarget")) {
            if (sender instanceof Player player){
                if (!player.hasPermission("eep.commands.removeeyetarget")){
                    player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                    return false;
                }
            }
            int size = args.length;
            if (size < 1){
                sender.sendMessage(ChatColor.RED + "Not enough arguments",
                        ChatColor.RED + "You need to say which target-location you would like to remove");
                return false;
            }
            List<String> current = plugin.locsFile.getConfig().getStringList("endereye-targets");
            if (!current.contains(args[0])){
                sender.sendMessage(ChatColor.RED + "Inputted location, " + args[0] + ", isn't an ender eye target already!");
                return false;
            }
            current.remove(args[0]);
            sender.sendMessage(ChatColor.GREEN + "Removed inputted location from ender eye target list");
            return true;
        }
        return false;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args){
        if (cmd.getName().equalsIgnoreCase("removeEyeTarget")){
            int size = args.length;
            if (size == 1) return savedTargets(args[0]);
        }
        return null;
    }


    /**
     *
     * @param base The collection of strings you want to be filtered
     * @param match The whitelist keyword for what stays in the new list
     * @return returns a filtered list containing contents that only contain match
     */
    private List<String> filter(Collection<String> base, String match){
        List<String> filtered = new ArrayList<>();
        match = match.toLowerCase();
        for (String entry : base){
            String temp = entry.toLowerCase();
            if (temp.contains(match)) filtered.add(entry);
        }
        return filtered;
    }

    /**
     * @param match Whitelist keyword for filter()
     * @return returns the filtered list of saved targets
     */
    private List<String> savedTargets(String match){
        return filter(plugin.locsFile.getConfig().getStringList("endereye-targets"), match);
    }
}
