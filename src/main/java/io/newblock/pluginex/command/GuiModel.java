package io.newblock.pluginex.command;

import io.newblock.pluginex.gui.PluginListGUI;
import io.newblock.pluginex.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuiModel {
    EXCommandHandler handler;

    public GuiModel(EXCommandHandler handler){
        this.handler = handler;
    }

    public void onCommand(CommandSender sender){
        if(sender.hasPermission("pluginex.admin.gui")){
            if(sender instanceof Player){
                PluginListGUI gui = new PluginListGUI((Player) sender);
                gui.showGUI();
            }else sender.sendMessage(ChatColor.RED + Util.format("command.no.console"));
        }else sender.sendMessage(ChatColor.RED+ Util.format("command.permission.no"));
    }
}
