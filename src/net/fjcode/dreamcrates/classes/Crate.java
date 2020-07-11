package net.fjcode.dreamcrates.classes;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Crate {

    // Crates object.

    String id;
    Location location;
    String title;
    List<Reward> rewards;

    public Crate(String id, Location location, String title, List<Reward> rewards) {
        this.id = id;
        this.location = location;
        this.title = title;
        this.rewards = rewards;
    }

    public String getId() { return id; }
    public String getTitle() {
        if (title.isEmpty()) {
            return id.toUpperCase();
        }
        return title;
    }
    public Location getLocation() { return location; }
    public List<Reward> getRewards() {

        if (rewards == null) {
            return new ArrayList<Reward>();
        } else {
            return rewards;
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRewards(List<Reward> rewards) {
        this.rewards = rewards;
    }
}
