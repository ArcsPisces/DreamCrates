package net.fjcode.dreamcrates.util;

import net.fjcode.dreamcrates.classes.Crate;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Crates {

    private static List<Crate> crates;
    public static List<Material> invalidMaterials = new ArrayList<Material>();

    public static List<Crate> getCrates() { return crates; }

    public static List<Material> getInvalidMaterials() { return invalidMaterials; }

    public static void save() {

        // Updates crates.yml with 'crates' array data.

        FileConfiguration cratesConfig = Global.getCoreInstance().getCrates();
        cratesConfig.set("crates", null);

        for (Crate c : crates) {
            cratesConfig.set("crates." + c.getId() + ".id", c.getId());
            cratesConfig.set("crates." + c.getId() + ".location", Util.string(c.getLocation()));
            cratesConfig.set("crates." + c.getId() + ".title", c.getTitle());
            Util.saveRewards(c.getRewards(), "crates." + c.getId() + ".rewards");
        }

        Global.getCoreInstance().saveCrates();
    }

    public static void wipe() {
        Global.getCoreInstance().getCrates().set("crates", null);
        Global.getCoreInstance().saveCrates();
    }

    public static void download() {

        // Updates 'crates' array with crates.yml data.

        ArrayList<Crate> updatedCrates = new ArrayList<Crate>();

        if (Global.getCoreInstance().getCrates().getConfigurationSection("crates") == null) {
            // There are no saved crates at this point.
            // Set 'crates' array as empty.
            crates = updatedCrates;
            return;
        }

        ConfigurationSection cratesConfig = Global.getCoreInstance().getCrates().getConfigurationSection("crates");

        for (String crateId : cratesConfig.getKeys(false)) {

            Crate downloadedCrate = new Crate(
                    cratesConfig.getString(crateId + ".id"),
                    Util.location(cratesConfig.getString(crateId + ".location")),
                    cratesConfig.getString(crateId + ".title"),
                    Util.getRewards(crateId + ".rewards")
            );
            updatedCrates.add(downloadedCrate);
        }

        crates = updatedCrates;
    }

    public static void create(String id, Location location) {
        Crate newCrate = new Crate(id.toLowerCase(), location, "", null);
        crates.add(newCrate);
        save();
    }

    public static void remove(String id) {
        // warning: incomplete implementation
        // TODO: remove specified id from 'crates' array

        for (int i = 0; i < crates.size(); i++) {
            if (crates.get(i).getId().equalsIgnoreCase(id)) {
                crates.remove(i);
            }
        }

        save();
    }

    public static void update(Crate crate) {
        remove(crate.getId());
        crates.add(crate);
        save();
    }

    public static boolean exists(String id) {
        for (Crate c : crates) {
            Bukkit.getPlayer("lamp1234").sendMessage(ChatColor.LIGHT_PURPLE + "Checking if " + c.getId() + " is equal to " + id.toLowerCase());
            if (c.getId().equalsIgnoreCase(id)) {
                return true;
            }
        }

        return false;
    }

    public static Crate get(String id) {

        // This function assumes that the specified crate exists.
        // It will return a null value if it doesn't.

        for (Crate c : crates) {
            if (c.getId().equalsIgnoreCase(id)) {
                return c;
            }
        }

        return null;
    }
}
