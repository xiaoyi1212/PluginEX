package io.newblock.pluginex.command;

import io.newblock.pluginex.util.EXPluginManager;
import io.newblock.pluginex.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class ReloadModel {
    EXCommandHandler handler;

    public ReloadModel(EXCommandHandler handler){
        this.handler = handler;
    }

    public void onCommand(CommandSender sender,String name){
        if(name.equals("all")){
            EXPluginManager.reloadAll(sender);
            return;
        }

        Plugin plugin = EXPluginManager.getPlugin(name);
        if(plugin==null){
            sender.sendMessage(ChatColor.RED+ Util.format("plugin.no.found"));
            return;
        }
        EXPluginManager.reload(sender,plugin);
    }
}
