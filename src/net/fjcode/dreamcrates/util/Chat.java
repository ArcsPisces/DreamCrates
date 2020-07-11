package net.fjcode.dreamcrates.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Chat {

    private static String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.RED + "DreamCrates" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY;
    private static String error_prefix = ChatColor.DARK_GRAY + "[" + ChatColor.RED + "DreamCrates Error" + ChatColor.DARK_GRAY + "] " + ChatColor.RED;

    // Send a message to an individual.
    public static void message(CommandSender cs, String message) {
        cs.sendMessage(prefix + message);
    }

    // Send an error message to an individual.
    public static void error(CommandSender cs, String message) {
        cs.sendMessage(error_prefix + message);
    }

    public static void noPermission(CommandSender cs) {
        cs.sendMessage(prefix + "You do not have the required permissions to do that.");
    }

    // Send the defined strings as raw messages with no prefix or colour.
    public static void raw(CommandSender cs, List<String> messages) {
        for(String message : messages) {
            cs.sendMessage(message);
        }
    }

    // Send a message to the entire server.
    public static void broadcast(String message) {
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(prefix + message);
        }
    }
}
