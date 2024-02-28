package io.newblock.pluginex.command;

import io.newblock.pluginex.util.EXPluginManager;
import io.newblock.pluginex.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class DisableModel {
    EXCommandHandler handler;

    public DisableModel(EXCommandHandler handler){
        this.handler = handler;
    }

    public void onCommand(CommandSender sender, String name){
        if(name.equals("all")){
            EXPluginManager.disableAll();
            sender.sendMessage(ChatColor.GREEN+ Util.format("plugin.disable.done"));
        }else {
            Plugin plugin = EXPluginManager.getPlugin(name);
            if(plugin == null){
                sender.sendMessage(ChatColor.RED+Util.format("plugin.no.found"));
                return;
            }
            if(!plugin.isEnabled()){
                sender.sendMessage(ChatColor.RED+Util.format("plugin.disabled"));
                return;
            }
            EXPluginManager.disable(plugin);
        }
    }
}
