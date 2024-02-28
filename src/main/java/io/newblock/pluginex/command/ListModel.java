package io.newblock.pluginex.command;

import io.newblock.pluginex.util.EXPluginManager;
import org.bukkit.command.CommandSender;

public class ListModel {
    EXCommandHandler handler;

    public ListModel(EXCommandHandler handler){
        this.handler = handler;
    }

    public void onCommand(CommandSender sender){
        EXPluginManager.listPlugins(sender);
    }
}
