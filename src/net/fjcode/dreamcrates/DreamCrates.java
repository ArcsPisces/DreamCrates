package net.fjcode.dreamcrates;

import net.fjcode.dreamcrates.commands.CratesCommand;
import net.fjcode.dreamcrates.listeners.BlockBreakListener;
import net.fjcode.dreamcrates.util.Global;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class DreamCrates extends JavaPlugin {

    private File cratesFile = new File(getDataFolder(), "crates.yml");
    private FileConfiguration crates = YamlConfiguration.loadConfiguration(cratesFile);

    @Override
    public void onEnable() {

        // Check if crates.yml exists; if not then create it.
        if (!cratesFile.exists()) {
            saveResource("crates.yml", false);
        }

        // Set this class as a variable in Global so its accessible from anywhere.
        Global.setCoreInstance(this);

        // Initiate the plugin.
        Global.initiate();
    }

    @Override
    public void onDisable() {}

    public FileConfiguration getCrates() {
        return crates;
    }

    public void saveCrates() {
        try {
            crates.save(cratesFile);
        } catch (IOException e) {
            this.getLogger().log(Level.SEVERE, "An exception occurred when trying to save crates.yml");
            e.printStackTrace();
        }
    }
}
