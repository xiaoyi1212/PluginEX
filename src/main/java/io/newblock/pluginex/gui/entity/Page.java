package io.newblock.pluginex.gui.entity;

import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Iterator;

public class Page implements Iterable<ItemStack> {
    ItemStack[] stacks;

    public Page(){
        this.stacks = new ItemStack[6*9];
    }

    public void setItem(int index,ItemStack item){
        this.stacks[index] = item;
    }

    @Override
    public Iterator<ItemStack> iterator() {
        return Arrays.stream(stacks).iterator();
    }
}
