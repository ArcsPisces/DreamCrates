package net.fjcode.dreamcrates.listeners;

import net.fjcode.dreamcrates.classes.Crate;
import net.fjcode.dreamcrates.util.Chat;
import net.fjcode.dreamcrates.util.Crates;
import net.fjcode.dreamcrates.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFadeEvent;

public class BlockBreakListener implements Listener {


    // Check if a player breaks the block.
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {

        // Check if the location of the block is a crate.

        // Get the location of the broken block.
        Location blockLocation = e.getBlock().getLocation();

        if (Util.locationIsCrate(blockLocation)) {

            if (e.getPlayer().hasPermission("dreamcrates.create"))
                Chat.message(e.getPlayer(), "You tried to break a crate. Use '/dc delete {id}' first.");

            e.setCancelled(true);
        }
    }

    // Check if the block burns naturally.
    @EventHandler
    public void onBlockBurn(BlockBurnEvent e) {

        // Check if the location of the block is a crate.

        // Get the location of the broken block.
        Location blockLocation = e.getBlock().getLocation();

        if (Util.locationIsCrate(blockLocation))
            e.setCancelled(true);
    }

    // Check if the block explodes.
    @EventHandler
    public void onBlockExplode(BlockExplodeEvent e) {

        // Check if the location of the block is a crate.

        // Get the location of the broken block.
        Location blockLocation = e.getBlock().getLocation();

        if (Util.locationIsCrate(blockLocation))
            e.setCancelled(true);
    }

    // Check if the block dissipates naturally.
    @EventHandler
    public void onBlockFade(BlockFadeEvent e) {

        // Check if the location of the block is a crate.

        // Get the location of the broken block.
        Location blockLocation = e.getBlock().getLocation();

        if (Util.locationIsCrate(blockLocation))
            e.setCancelled(true);
    }
}
