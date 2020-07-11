package net.fjcode.dreamcrates.util;

import net.fjcode.dreamcrates.classes.Crate;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class Icon {

    public static ItemStack getKey(Crate crate) {
        ItemStack is = new ItemStack(Material.TRIPWIRE_HOOK, 1);
        ItemMeta im = is.getItemMeta();

        String displayname = ChatColor.translateAlternateColorCodes('&', crate.getTitle()) + ChatColor.RESET + " Key";
        im.setDisplayName(displayname);

        List<String> lore = Arrays.asList(ChatColor.GRAY + "This is a crate key.", ChatColor.GRAY + "Right-click it on a crate to use.");
        im.setLore(lore);

        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        is.setItemMeta(im);

        return is;
    }
}
