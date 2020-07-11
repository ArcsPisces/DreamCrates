package net.fjcode.dreamcrates.util;

import net.fjcode.dreamcrates.classes.Crate;
import net.fjcode.dreamcrates.classes.Reward;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Util {

    public static Location location(String from) {
        String[] components = from.split(",");
        World world = Bukkit.getWorld(components[0]);
        double x = Double.parseDouble(components[1]);
        double y = Double.parseDouble(components[2]);
        double z = Double.parseDouble(components[3]);
        Location location = new Location(world, x, y, z);

        return location;
    }

    public static String string(Location from) {
        String locationString = from.getWorld().getName() + "," + from.getX() + "," + from.getY() + "," + from.getZ();
        return locationString;
    }

    public static boolean locationIsCrate(Location location) {
        // Cycle through each crate and check if the locations match.
        for (Crate c : Crates.getCrates()) {

            if (locationsMatch(c.getLocation(), location)) {
                // The locations match; therefore, prevent the event from happening.

                return true;
            }
        }

        return false;
    }

    public static Crate getCrate(Location at) {

        for (Crate c : Crates.getCrates()) {
            if (locationsMatch(c.getLocation(), at)) {
                return c;
            }
        }

        return null;
    }

    public static boolean locationsMatch(Location loc1, Location loc2) {
        if ((loc1.getX() == loc2.getX()) && (loc1.getY() == loc2.getY()) && (loc1.getZ() == loc2.getZ())) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean itemIsKey(ItemStack item, Crate crate) {
        ItemMeta keyMeta = Icon.getKey(crate).getItemMeta();
        if ((item != null)
                && (item.getType() != Material.AIR)
                && (item.getItemMeta().getDisplayName().equalsIgnoreCase(keyMeta.getDisplayName()))
                && (item.getItemMeta().hasItemFlag(ItemFlag.HIDE_ENCHANTS))) {

            return true;

        } else {
            return false;
        }
    }

    public static void saveRewards(List<Reward> rewards, String onPath) {

        for (int i = 0; i < rewards.size(); i++) {
            Reward reward = rewards.get(i);
            Global.getCoreInstance().getCrates().set(onPath + "." + i + ".item", reward.getItemStack());
            Global.getCoreInstance().getCrates().set(onPath + "." + i + ".percentage", reward.getPercentage());
        }

        Global.getCoreInstance().saveCrates();
    }

    public static List<Reward> getRewards(String fromPath) {

        List<Reward> results = new ArrayList<Reward>();

        if (Global.getCoreInstance().getCrates().get(fromPath) == null) {
            return results;
        }

        ConfigurationSection rewardSection = Global.getCoreInstance().getCrates().getConfigurationSection(fromPath);
        for (String key : rewardSection.getKeys(false)) {
            ItemStack item = rewardSection.getItemStack(key + ".item");
            double percentage = rewardSection.getDouble(key + ".percentage");

            Reward reward = new Reward(item, percentage);
            results.add(reward);
        }

        return results;

    }

}
