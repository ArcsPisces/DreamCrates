package net.fjcode.dreamcrates.classes;

import org.bukkit.inventory.ItemStack;

public class Reward {

    ItemStack reward;
    double percentage;

    public Reward(ItemStack reward, double percentage) {
        this.reward = reward;
        this.percentage = percentage;
    }

    public ItemStack getItemStack() {
        return reward;
    }

    public double getPercentage() {
        return percentage;
    }
}
