package io.newblock.pluginex.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public abstract class BaseGUI {
    Player player;

    public BaseGUI(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public abstract void showGUI();

    public abstract void onEvent(InventoryClickEvent event);
}
