package io.newblock.pluginex.command;

import io.newblock.pluginex.util.EXPluginManager;
import io.newblock.pluginex.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class LoadModel {
    EXCommandHandler handler;

    public LoadModel(EXCommandHandler handler){
        this.handler = handler;
    }

    public void onCommand(CommandSender sender,String name){
        Plugin plugin = EXPluginManager.getPlugin(name);
        if(plugin != null){
            sender.sendMessage(ChatColor.RED+ Util.format("plugin.loaded"));
            return;
        }
        EXPluginManager.load(sender,name);
    }
}
