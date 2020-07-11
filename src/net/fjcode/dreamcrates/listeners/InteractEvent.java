package net.fjcode.dreamcrates.listeners;

import net.fjcode.dreamcrates.classes.Crate;
import net.fjcode.dreamcrates.classes.Reward;
import net.fjcode.dreamcrates.util.GUI;
import net.fjcode.dreamcrates.util.Icon;
import net.fjcode.dreamcrates.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class InteractEvent implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        // Check if the user has permission.
        if (!e.getPlayer().hasPermission("dreamcrates.user")) {
            e.setCancelled(true);
            return;
        }

        if (e.getClickedBlock() == null) {
            return;
        }

        // Check if the interacted with block is a crate.
        if (Util.locationIsCrate(e.getClickedBlock().getLocation())) {

            Crate crate = Util.getCrate(e.getClickedBlock().getLocation());

            // Check if the crate was clicked with LEFT or RIGHT mouse.
            if (e.getAction() == Action.LEFT_CLICK_BLOCK) {

                // The user left clicked. Display the rewards for this crate.

                List<ItemStack> rewardItems = new ArrayList<ItemStack>();
                for (Reward r : crate.getRewards()) {
                    rewardItems.add(r.getItemStack());
                }

                // Create a GUI with the crates rewards and open it for the player.
                Inventory gui = GUI.create(27, GUI.getGUITitle(crate), rewardItems);
                e.getPlayer().openInventory(gui);

                e.setCancelled(true);

            } else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {

                e.setCancelled(true);

                // The user right clicked. Check for a key.
                if (Util.itemIsKey(e.getItem(), crate)) {

                    // Assume that the key in hand belongs to this crate.
                    // No extensive testing has been done on this if statement. More checks may be required to prevent exploits.

                    // Open the crate and give the user a reward.
                }
            }

        }
    }
}
