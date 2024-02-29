package io.newblock.pluginex.command;

import io.newblock.pluginex.PluginEX;
import io.newblock.pluginex.util.EXPluginManager;
import io.newblock.pluginex.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EXCommandHandler implements TabExecutor {
    PluginEX instance;
    HelpModel help;
    ListModel list;
    LoadModel load;
    DisableModel disable;
    ReloadModel reload;
    InfoModel info;
    GuiModel gui;

    public EXCommandHandler(PluginEX instance){
        this.instance = instance;
        this.help = new HelpModel(this);
        this.list = new ListModel(this);
        this.load = new LoadModel(this);
        this.disable = new DisableModel(this);
        this.reload = new ReloadModel(this);
        this.info = new InfoModel(this);
        this.gui = new GuiModel(this);
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
                case "gui":
                    this.gui.onCommand(sender);
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
        if(sender.hasPermission("pluginex.admin.command")){
            if(args.length == 0) {
                List<String> ret = new ArrayList<>(Arrays.asList("help", "load", "reload", "info", "disable", "list"));
                if (sender.hasPermission("pluginex.admin.gui")) ret.add("gui");
                return ret;
            }

            switch (args[0]){
                case "disable":
                case "reload":
                case "info":
                    List<String> ret = new ArrayList<>();
                    for(Plugin plugin : EXPluginManager.getAllPlugins()){
                        if(plugin.isEnabled()){
                            ret.add(plugin.getName());
                        }
                    }
                    return ret;
                case "load":
                    File pluginDir = new File("plugins");
                    List<String> files = new ArrayList<>(),names = new ArrayList<>();
                    if (!pluginDir.isDirectory()) {
                        return Collections.emptyList();
                    }
                    for(File file : pluginDir.listFiles()){
                        if(!file.isDirectory())
                            files.add(file.getName());
                    }
                    for(Plugin plugin:EXPluginManager.getAllPlugins()) {
                        try {
                            Field file_f = Util.getClassField((JavaPlugin) plugin, "file");
                            if (file_f == null) continue;
                            File file = (File) file_f.get(plugin);
                            if(file != null) names.add(file.getName());
                        }catch (Exception ignored){
                        }
                    }
                    files.removeAll(names);
                    return files;
            }
        }
        return null;
    }
}
