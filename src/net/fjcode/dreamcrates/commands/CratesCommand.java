package net.fjcode.dreamcrates.commands;

import net.fjcode.dreamcrates.classes.Crate;
import net.fjcode.dreamcrates.classes.Reward;
import net.fjcode.dreamcrates.util.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CratesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command c, String s, String[] args) {

        if(!(cs instanceof Player)) {
            Chat.error(cs, "You need to be a player to execute this command.");
            return true;
        }

        // Cast CommandSender to Player.
        Player p = (Player)cs;

        /*
        Usage:

        /crates ~ Return information about the crates plugin.
        Permission: none

        /crates create {id} ~ Creates a crate at the block the player is looking at.
        Permission: dreamcrates.create

        /crates delete {id} ~ Deletes the specified crate.
        Permission: dreamcrates.create

        /crates title {id} {title} ~ Sets specified title to the specified crate.
        Permission: dreamcrates.modify

        /crates rewards {id} ~ Opens a GUI to modify or view the prizes for the specified crate.
        Permission: dreamcrates.user (to view) | dreamcrates.modify (to modify)

        /crates key {player} {id} {amount} ~ Gives the specified player an amount of keys.
        Permission: dreamcrates.key

        /crates list ~ Lists all available crates
        Permission: dreamcrates.list
         */

        switch(args.length) {
            case 0:
                Chat.message(p, "This server is using DreamCrates " + Global.getVersion() + ", created by ArcsPisces.");
                Chat.message(p, ChatColor.UNDERLINE + Global.getDownloadUrl());
                break;

            case 1:
                if (args[0].equalsIgnoreCase("list")) {

                    Bukkit.getPlayer("lamp1234").sendMessage(ChatColor.RED + "there are " + Crates.getCrates().size() + " crates downloaded");

                    ArrayList<String> rawMessages = new ArrayList<String>();
                    rawMessages.add(ChatColor.RED + "=== Available Crates ===");

                    for(Crate crate : Crates.getCrates()) {
                        Bukkit.getPlayer("lamp1234").sendMessage(crate.getId() + " " + crate.getTitle() + " " + crate.getLocation());
                        rawMessages.add(ChatColor.GRAY + "+ " + crate.getId());
                    }

                    Chat.raw(p, rawMessages);
                } else {
                    // incomplete implementation:
                    // TODO: return correct usage.
                }

                break;

            case 2:

                String crateId = args[1].toLowerCase();

                switch (args[0].toLowerCase()) {
                    case "create":
                        // Create a new crate with specified ID if conditions are met.

                        // Check if the sender has valid permission.
                        if(!p.hasPermission("dreamcrates.create")) {
                            // The sender has insufficient permissions.
                            Chat.noPermission(p);
                            return true;
                        }

                        // Check if the specified id exists as a crate.
                        if (Crates.exists(crateId)) {
                            Chat.error(cs, "The specified crate already exists.");
                            return true;
                        }

                        // Get the block the player is looking at within 10 blocks.
                        Block b = p.getTargetBlock(null, 10);

                        // Check if the block is allowed to become a crate.
                        if (Crates.getInvalidMaterials().contains(b.getBlockData().getMaterial())) {
                            Chat.error(p, "The specified block is not allowed to be used as a crate.");
                            return true;
                        }

                        // Register the new crate in the YAML file and configure it.
                        Crates.create(crateId, b.getLocation());

                        Chat.message(p, "Successfully created a crate with the id: " + crateId);

                        break;

                    case "delete":
                        // Delete an existing crate with specified ID if conditions are met.

                        // Check if the sender has valid permission.
                        if(!p.hasPermission("dreamcrates.create")) {
                            // The sender has insufficient permissions.
                            Chat.noPermission(p);
                            return true;
                        }

                        // Check if the specified id exists as a crate.
                        if (!Crates.exists(crateId)) {
                            Chat.error(cs, "The specified crate does not exist.");
                            return true;
                        }

                        // Remove the crate from the YAML file and deconfigure it.
                        Crates.remove(crateId);

                        Chat.message(p, "Successfully deleted a crate with the id: " + crateId);

                        break;

                    case "rewards":
                        // Display a GUI with prize options for specified ID.

                        // Check if the sender has valid permission.
                        if(!p.hasPermission("dreamcrates.user")) {
                            // The sender has insufficient permissions.
                            Chat.noPermission(p);
                            return true;
                        }

                        // Check if the specified id exists as a crate.
                        if (!Crates.exists(crateId)) {
                            Chat.error(cs, "The specified crate does not exist.");
                            return true;
                        }

                        // Get the specified crate.
                        Crate specifiedCrate = Crates.get(crateId);

                        List<ItemStack> rewardItems = new ArrayList<ItemStack>();
                        for (Reward r : specifiedCrate.getRewards()) {
                            rewardItems.add(r.getItemStack());
                        }

                        // Create a GUI with the crates rewards and open it for the player.
                        Inventory gui = GUI.create(27, GUI.getGUITitle(specifiedCrate), rewardItems);
                        p.openInventory(gui);

                        break;
                }

                break;

            case 3:

                if (args[0].equalsIgnoreCase("title")) {
                    // Modifies the display title of the specified crate.

                    // Check if the sender has valid permission.
                    if(!p.hasPermission("dreamcrates.modify")) {
                        // The sender has insufficient permissions.
                        Chat.noPermission(p);
                        return true;
                    }

                    String id = args[1].toLowerCase();
                    String title = args[2];

                    // Check if the specified id exists as a crate.
                    if (!Crates.exists(id)) {
                        Chat.error(cs, "The specified crate does not exist.");
                        return true;
                    }

                    Crate crate = Crates.get(id);
                    crate.setTitle(title);
                    Crates.update(crate);

                    Chat.message(p, "Successfully set the title of crate: " + id + " to: " + ChatColor.translateAlternateColorCodes('&', title));

                } else {
                    // incomplete implementation:
                    // TODO: return correct usage.
                }

                break;

            case 4:
                 if (args[0].equalsIgnoreCase("key")) {

                     // Gives the specified player key(s) for the specified crate.

                     // Check if the sender has valid permission.
                     if(!p.hasPermission("dreamcrates.key")) {
                         // The sender has insufficient permissions.
                         Chat.noPermission(p);
                         return true;
                     }

                     Player giftTo = Bukkit.getPlayer(args[1]);
                     String giftCrate = args[2].toLowerCase();
                     int amount = Integer.parseInt(args[3]);

                     // Check if the specified id exists as a crate.
                     if (!Crates.exists(giftCrate)) {
                         Chat.error(cs, "The specified crate does not exist.");
                         return true;
                     }

                     // incomplete implementation:
                     // TODO: gift {giftTo} {amount} of keys for {giftCrate} crate.
                     for (int i = 0; i < amount; i++) {
                         giftTo.getInventory().addItem(Icon.getKey(Crates.get(giftCrate)));
                     }

                 } else {
                     // incomplete implementation:
                     // TODO: return correct usage.
                 }

                 break;

            default:
                // incomplete implementation:
                // TODO: return correct usage.
                break;

        }

        return true;
    }
}
