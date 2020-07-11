package net.fjcode.dreamcrates.util;

import net.fjcode.dreamcrates.DreamCrates;
import net.fjcode.dreamcrates.commands.CratesCommand;
import net.fjcode.dreamcrates.listeners.BlockBreakListener;
import net.fjcode.dreamcrates.listeners.BlockPlaceListener;
import net.fjcode.dreamcrates.listeners.InteractEvent;
import net.fjcode.dreamcrates.listeners.InventoryListener;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class Global {

    // Call this function in the plugins onEnable().
    public static void initiate() {

        // Download any crates from crates.yml
        Crates.download();

        // Register commands.
        coreInstance.getCommand("dreamcrates").setExecutor(new CratesCommand());

        // Register listeners.
        coreInstance.getServer().getPluginManager().registerEvents(new BlockBreakListener(), coreInstance);
        coreInstance.getServer().getPluginManager().registerEvents(new BlockPlaceListener(), coreInstance);
        coreInstance.getServer().getPluginManager().registerEvents(new InventoryListener(), coreInstance);
        coreInstance.getServer().getPluginManager().registerEvents(new InteractEvent(), coreInstance);

    }

    private static String version = "v1.0";

    public static String getVersion() {
        return version;
    }

    public static String getDownloadUrl() { return "https://spigot.com/DreamCrates/"; }

    private static DreamCrates coreInstance;

    public static DreamCrates getCoreInstance() { return coreInstance; }

    public static void setCoreInstance(DreamCrates coreInstance) { Global.coreInstance = coreInstance; }
}
