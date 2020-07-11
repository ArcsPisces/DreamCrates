package net.fjcode.dreamcrates.listeners;

import net.fjcode.dreamcrates.classes.Crate;
import net.fjcode.dreamcrates.classes.Reward;
import net.fjcode.dreamcrates.util.Chat;
import net.fjcode.dreamcrates.util.Crates;
import net.fjcode.dreamcrates.util.GUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryInteract(InventoryInteractEvent e) {

        // Check if the inventory is for a crate.

        // Cycle through all crates and check if the title matches.
        for (Crate c : Crates.getCrates()) {

            // Check if the inventory's title is the same as the generated title for 'c'.
            if (e.getView().getTitle().equalsIgnoreCase(GUI.getGUITitle(c))) {

                // Check if the user has permission to modify the contents of the crate.
                if (!e.getWhoClicked().hasPermission("dreamcrates.modify")) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {

        // Check if the inventory is for a crate.

        // Cycle through all crates and check if the title matches.
        for (Crate c : Crates.getCrates()) {

            // Check if the inventory's title is the same as the generated title for 'c'.
            if (e.getView().getTitle().equalsIgnoreCase(GUI.getGUITitle(c))) {

                // Check if the user has permission to modify the contents of the crate.
                if (!e.getPlayer().hasPermission("dreamcrates.modify")) {
                    return;
                }

                // Update the contents of the closed inventory to the crates configuration file.
                List<Reward> availableRewards = new ArrayList<Reward>();
                int currentlyAdded = 0;

                // Cycle through the inventory to gauge how many items there are total.
                for (int i = 0; i < e.getInventory().getContents().length; i++) {

                    // Check each item in case it is empty.
                    if (e.getInventory().getContents()[i] != null &&
                            e.getInventory().getContents()[i].getType() != Material.AIR) {
                        currentlyAdded += 1;
                    }
                }

                // Cycle through the closed inventories items.
                for (int i = 0; i < e.getInventory().getContents().length; i++) {

                    // Check each item in case it is empty.
                    if (e.getInventory().getContents()[i] != null &&
                            e.getInventory().getContents()[i].getType() != Material.AIR) {

                        // If the item exists, add it to availableRewards.
                        Reward reward = new Reward(e.getInventory().getContents()[i],100 / currentlyAdded);
                        availableRewards.add(reward);
                    }
                }

                c.setRewards(availableRewards);
                Crates.update(c);
            }
        }
    }
}
