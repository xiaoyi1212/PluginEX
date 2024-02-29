package io.newblock.pluginex.gui;

import io.newblock.pluginex.PluginEX;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

public class GuiListener implements Listener {
    PluginEX instance;
    List<PluginListGUI> guis;

    public GuiListener(PluginEX instance){
        this.instance = instance;
        this.guis = new ArrayList<>();
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        for(PluginListGUI gui : guis){
            gui.onEvent(event);
        }
    }
}
