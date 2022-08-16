package me.stephenminer.endereyepointer.events;

import me.stephenminer.endereyepointer.EnderEyePointer;
import org.bukkit.Location;
import org.bukkit.entity.EnderSignal;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.ArrayList;
import java.util.List;

public class SetEyeTarget implements Listener {
    private final EnderEyePointer plugin;

    public SetEyeTarget(EnderEyePointer plugin){
        this.plugin = plugin;
    }


    @EventHandler
    public void setTarget(EntitySpawnEvent event){
        if (event.getEntity() instanceof EnderSignal enderSignal){
            Location target = getNearestTarget(enderSignal);
            if (target == null) return;
            target.setY(enderSignal.getLocation().getY() + 15);
            enderSignal.setTargetLocation(target);
        }
    }

    /**
     * @param enderSignal used as the center location to get the closest target
     * @return returns the nearest ender-eye target location closest to enderSignal
     */
    public Location getNearestTarget(EnderSignal enderSignal){
        List<Location> targets = getEyeTargets();
        if (targets == null || targets.isEmpty()) return null;
        Location epicenter = enderSignal.getLocation();
        Location closest = targets.get(0);
        for (Location loc : targets){
            if (distance(epicenter, closest) > distance(epicenter, loc)) closest = loc;
        }
        return closest;
    }


    public List<Location> getEyeTargets(){
        List<String> stringTargets = plugin.locsFile.getConfig().getStringList("endereye-targets");
        List<Location> targetLocs = new ArrayList<>();
        for (String str : stringTargets){
            targetLocs.add(plugin.fromString(str));
        }
        return targetLocs;
    }

    /**
     * @return returns the distance between loc1 and loc2 using the x and z coordinates and a^2 + b^2 = c^2
     */
    public double distance(Location loc1, Location loc2){
        int a = Math.abs(loc1.getBlockX() - loc2.getBlockX());
        int b = Math.abs(loc1.getBlockZ() - loc2.getBlockZ());
        return Math.sqrt(a*a + b*b);
    }
}
