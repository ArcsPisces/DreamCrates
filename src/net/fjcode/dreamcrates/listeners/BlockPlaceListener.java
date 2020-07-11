package net.fjcode.dreamcrates.listeners;

import net.fjcode.dreamcrates.classes.Crate;
import net.fjcode.dreamcrates.util.Crates;
import net.fjcode.dreamcrates.util.Icon;
import net.fjcode.dreamcrates.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        ItemStack placed = e.getItemInHand();

        for (Crate c : Crates.getCrates()) {
            if (Util.itemIsKey(placed, c)) {
                e.setCancelled(true);
            }
        }
    }
}
