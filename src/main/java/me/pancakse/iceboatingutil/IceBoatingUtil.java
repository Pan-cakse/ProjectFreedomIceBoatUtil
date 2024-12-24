package me.pancakse.iceboatingutil;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Boat;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class IceBoatingUtil extends JavaPlugin implements Listener {

    // Plugin enable stuff.
    @Override
    public void onEnable() {
        getLogger().info("IceBoatingUtil enabled :D Made by Pancakse");
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    // Plugin disable stuff.
    @Override
    public void onDisable() {
        getLogger().info("IceBoatingUtil plugin disabled, sad to see you go :(");
    }

    // The thing that does the thingy.
    @EventHandler
    public void onBoatMove(VehicleMoveEvent event) {
        // Check if the vehicle is a boat.
        if (!(event.getVehicle() instanceof Boat)) {
            return;
        }

        Boat boat = (Boat) event.getVehicle();
        World world = boat.getWorld();

        // Get the block below the boat.
        Material blockBelow = world.getBlockAt(boat.getLocation().subtract(0, 1, 0)).getType();

        // Check if the block below is ice.
        if (isIce(blockBelow)) {
            Vector velocity = boat.getVelocity();
            double speed = velocity.length();

            // If the boat is moving fast enough, give it upward momentum.
            if (speed > 0.5) { // Threshold for "climbing" effect
                boat.setVelocity(velocity.add(new Vector(0, 0.5, 0)));
            }
        }
    }

    // To show what is considered as ice.
    private boolean isIce(Material material) {
        return material == Material.ICE || material == Material.PACKED_ICE || material == Material.BLUE_ICE;
    }
}
