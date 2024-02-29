package io.newblock.pluginex.command;

import io.newblock.pluginex.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpModel {
    EXCommandHandler handler;

    public HelpModel(EXCommandHandler handler){
        this.handler = handler;
    }

    public void onCommand(CommandSender sender){
        if(sender instanceof Player){
            sender.sendMessage(new String[]{
                    ChatColor.DARK_AQUA +"*** [PluginEX Command Helper] ***\n",
                    ChatColor.GOLD+"/pluginex load <file> "+ChatColor.GRAY+ Util.format("command.help.load"),
                    ChatColor.GOLD+"/pluginex disable <plugin|all> "+ChatColor.GRAY+ Util.format("command.help.disable"),
                    ChatColor.GOLD+"/pluginex reload <plugin|all> "+ChatColor.GRAY+ Util.format("command.help.reloadall"),
                    ChatColor.GOLD+"/pluginex info <plugin> [simple] "+ChatColor.GRAY+ Util.format("command.help.info"),
                    ChatColor.GOLD+"/pluginex gui "+ChatColor.GRAY+ Util.format("command.help.gui"),
            });
        }else {
            sender.sendMessage(new String[]{
                    ChatColor.DARK_AQUA +"*** [PluginEX Console Helper] ***\n",
                    ChatColor.GOLD+"/pluginex load <file> "+ChatColor.GRAY+ Util.format("command.help.load"),
                    ChatColor.GOLD+"/pluginex disable <plugin|all> "+ChatColor.GRAY+ Util.format("command.help.disable"),
                    ChatColor.GOLD+"/pluginex reload <plugin|all> "+ChatColor.GRAY+ Util.format("command.help.reloadall"),
                    ChatColor.GOLD+"/pluginex info <plugin> "+ChatColor.GRAY+ Util.format("command.help.info"),
                    ChatColor.GOLD+"/pluginex gui "+ChatColor.GRAY+ Util.format("command.help.gui"),
            });
        }
    }
}
