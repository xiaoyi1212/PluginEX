package io.newblock.pluginex.command;

import io.newblock.pluginex.util.EXPluginManager;
import io.newblock.pluginex.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.*;

public class InfoModel {
    EXCommandHandler handler;

    public InfoModel(EXCommandHandler handler){
        this.handler = handler;
    }

    public void onCommand(CommandSender sender,String name){
        Plugin plugin = EXPluginManager.getPlugin(name);
        if(plugin == null){
            sender.sendMessage(ChatColor.RED+ Util.format("plugin.no.found"));
            return;
        }
        String plugin_name = plugin.getName(),
                authors = plugin.getDescription().getAuthors().toString(),
                version = plugin.getDescription().getVersion(),
                main = plugin.getDescription().getMain();

        if(sender instanceof Player){
            Player player = (Player) sender;
            ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
            BookMeta meta = (BookMeta) book.getItemMeta();
            meta.setTitle(ChatColor.GOLD+"["+plugin.getName()+"]");
            meta.setAuthor(ChatColor.GREEN+"PluginEX");

            meta.addPage(plugin_name+"\n" +
                    version+"\n"+
                    authors+"\n"+
                    main+"\n");


            meta.addPage("Commands\n"+ build(plugin.getDescription().getCommands().keySet()));
            List<String> string = new ArrayList<>();
            for(Permission permission:plugin.getDescription().getPermissions()){
                string.add(permission.getName());
            }
            meta.addPage("Permissions\n"+build(string));

            book.setItemMeta(meta);
            player.openBook(book);
        }else {

        }
    }

    public String build(Collection<String> keys){
        StringBuilder sb = new StringBuilder();
        Iterator<String> iterator = keys.iterator();
        int i = 0;
        while (iterator.hasNext()){
            String command = iterator.next();
            if(i % 3 == 1){
                sb.append(command).append('\n');
                continue;
            }
            sb.append(command).append(",");
            i++;
        }
        return sb.toString();
    }

    public void onCommandSimple(CommandSender sender,String name){
        Plugin plugin = EXPluginManager.getPlugin(name);
        if(plugin == null){
            sender.sendMessage(ChatColor.RED+ Util.format("plugin.no.found"));
            return;
        }
        PluginDescriptionFile description = plugin.getDescription();
        sender.sendMessage(ChatColor.DARK_AQUA+String.format("[%s]: Version: %s \n" +
                "Description: %s\n" +
                "Authors: %s\n" +
                "Main: %s\n",description.getName(),description.getVersion(),description.getDescription(),description.getAuthors(),description.getMain()));
    }
}
