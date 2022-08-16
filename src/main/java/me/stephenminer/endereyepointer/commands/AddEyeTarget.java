package me.stephenminer.endereyepointer.commands;

import me.stephenminer.endereyepointer.EnderEyePointer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class AddEyeTarget implements CommandExecutor {

    private final EnderEyePointer plugin;

    public AddEyeTarget(EnderEyePointer plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (cmd.getName().equalsIgnoreCase("addEyeTarget")){
            if (sender instanceof Player player){
                if (!player.hasPermission("eep.commands.addeyetarget")){
                    player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                    return false;
                }
                List<String> current = plugin.locsFile.getConfig().getStringList("endereye-targets");
                current.add(plugin.fromBlockLoc(player.getLocation()));
                plugin.locsFile.getConfig().set("endereye-targets", current);
                plugin.locsFile.saveConfig();
                player.sendMessage(ChatColor.GREEN + "Set the new target for eyes of ender");
                return true;
            }else sender.sendMessage(ChatColor.RED + "You need to be a player to use this command!");
        }
        return false;
    }
}
