package io.newblock.pluginex.command;

import io.newblock.pluginex.PluginEX;
import io.newblock.pluginex.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.List;

public class EXCommandHandler implements TabExecutor {
    PluginEX instance;
    HelpModel help;
    ListModel list;
    LoadModel load;
    DisableModel disable;
    ReloadModel reload;
    InfoModel info;

    public EXCommandHandler(PluginEX instance){
        this.instance = instance;
        this.help = new HelpModel(this);
        this.list = new ListModel(this);
        this.load = new LoadModel(this);
        this.disable = new DisableModel(this);
        this.reload = new ReloadModel(this);
        this.info = new InfoModel(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender.hasPermission("pluginex.admin.command")){
            if(args.length == 0){
                this.help.onCommand(sender);
                return true;
            }

            switch (args[0]){
                case "help":
                    this.help.onCommand(sender);
                    return true;
                case "list":
                    this.list.onCommand(sender);
                    return true;
                case "load":
                    if(args.length < 2){
                        sender.sendMessage(ChatColor.RED+Util.format("command.length"));
                        return false;
                    }
                    this.load.onCommand(sender,args[1]);
                    return true;
                case "disable":
                    if(args.length < 2){
                        sender.sendMessage(ChatColor.RED+Util.format("command.length"));
                        return false;
                    }
                    this.disable.onCommand(sender,args[1]);
                    return true;
                case "reload":
                    if(args.length < 2){
                        sender.sendMessage(ChatColor.RED+Util.format("command.length"));
                        return false;
                    }
                    this.reload.onCommand(sender,args[1]);
                    return true;
                case "info":
                    if(args.length < 2){
                        sender.sendMessage(ChatColor.RED+Util.format("command.length"));
                        return false;
                    }
                    if(args.length == 3){
                        if(args[2].equals("simple"))
                            this.info.onCommandSimple(sender,args[1]);
                        else sender.sendMessage(ChatColor.RED+Util.format("command.unknown"));
                        return true;
                    }
                    this.info.onCommand(sender,args[1]);
                    return true;
                default:
                    sender.sendMessage(ChatColor.RED+Util.format("command.unknown"));
                    return false;
            }
        }else sender.sendMessage(ChatColor.RED+ Util.format("command.permission.no"));
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        return null;
    }
}
