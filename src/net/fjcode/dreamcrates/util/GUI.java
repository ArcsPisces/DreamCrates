package net.fjcode.dreamcrates.util;

import net.fjcode.dreamcrates.classes.Crate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GUI {

    public static Inventory create(int size, String title, List<ItemStack> contents) {

        // Create an inventory to act as the interface.
        Inventory gui = Bukkit.createInventory(null, size, ChatColor.translateAlternateColorCodes('&', title));

        // Add each item into the UI
        for (ItemStack item : contents) {
            gui.addItem(item);
        }

        // Return the created inventory.
        return gui;
    }

    public static String getGUITitle(Crate from) {
        return ChatColor.translateAlternateColorCodes('&', from.getTitle() + ChatColor.RESET + " Crate");
    }
}
