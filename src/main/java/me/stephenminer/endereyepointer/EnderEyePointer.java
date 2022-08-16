package me.stephenminer.endereyepointer;

import me.stephenminer.endereyepointer.commands.AddEyeTarget;
import me.stephenminer.endereyepointer.commands.RemoveEyeTarget;
import me.stephenminer.endereyepointer.events.SetEyeTarget;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class EnderEyePointer extends JavaPlugin {


    public ConfigFile locsFile;
    @Override
    public void onEnable() {
        this.locsFile = new ConfigFile(this, "locs");
        registerCommands();
        registerEvents();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerCommands(){
        getCommand("addEyeTarget").setExecutor(new AddEyeTarget(this));

        RemoveEyeTarget removeEyeTarget = new RemoveEyeTarget(this);
        getCommand("removeEyeTarget").setExecutor(removeEyeTarget);
        getCommand("removeEyeTarget").setTabCompleter(removeEyeTarget);

    }
    private void registerEvents(){
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new SetEyeTarget(this), this);
    }


    /**
     * @param loc the location that is to be turned into a string
     * @return returns the string form of a block-location (whole numbers) in world,x,y,z format
     */
    public String fromBlockLoc(Location loc){
        return loc.getWorld().getName() + "," + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ();
    }

    /**
     *
     * @param str - a string formatted in world,x,y,z
     * @return returns the location found in the string (undoes fromBlockLoc())
     * If the world found in the location isn't loaded or doesn't exist, the world will be loaded or created
     */
    public Location fromString(String str){
        String[] contents = str.split(",");
        String worldName = contents[0];
        World world = null;
        try{
            world = Bukkit.getWorld(worldName);
        }catch (Exception e){
            getLogger().warning("Attempted to get world " + worldName + ", but it was null. Attempting to load the world now.");
        }
        world = Bukkit.createWorld(new WorldCreator(worldName));
        int x = Integer.parseInt(contents[1]);
        int y = Integer.parseInt(contents[2]);
        int z = Integer.parseInt(contents[3]);
        return new Location(world, x, y, z);
    }


}
