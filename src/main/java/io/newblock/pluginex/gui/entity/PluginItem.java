package io.newblock.pluginex.gui.entity;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class PluginItem extends ItemStack {
    Plugin plugin;

    public PluginItem(Plugin plugin){
        super(Material.END_CRYSTAL);
        this.plugin = plugin;
        init(this.plugin.getDescription());
    }

    private void init(PluginDescriptionFile description){
        ItemMeta meta = getItemMeta();
        meta.setDisplayName(ChatColor.DARK_AQUA +description.getName());

        setItemMeta(meta);
    }
}
