package io.newblock.pluginex.gui;

import io.newblock.pluginex.gui.entity.Page;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

public class PluginListGUI extends BaseGUI{
    int page_index;
    List<Page> pages;

    public PluginListGUI(Player player) {
        super(player);
        this.page_index = 0;
        this.pages = new ArrayList<>();
    }

    public void addPage(Page page){
        this.pages.add(page);
    }

    @Override
    public void showGUI() {

    }

    @Override
    public void onEvent(InventoryClickEvent event) {

    }
}
