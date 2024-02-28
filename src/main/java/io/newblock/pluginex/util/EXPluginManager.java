package io.newblock.pluginex.util;

import io.newblock.pluginex.PluginEX;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.Event;
import org.bukkit.plugin.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLClassLoader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EXPluginManager {

    public static Plugin getPlugin(String name) {
        Plugin[] plugins = Bukkit.getPluginManager().getPlugins();
        for (Plugin plugin : plugins) {
            if (name.equalsIgnoreCase(plugin.getName())) {
                return plugin;
            }
        }
        return null;
    }

    public static void load(CommandSender sender, String name) {
        Plugin target = null;
        File pluginDir = new File("plugins");
        if (!pluginDir.isDirectory()) {
            sender.sendMessage(ChatColor.RED + Util.format("plugin.no.directory"));
            return;
        } else {
            File pluginFile = new File(pluginDir, name + ".jar");
            if (!pluginFile.isFile()) {
                File[] files = pluginDir.listFiles();
                for (File f : files) {
                    if (f.getName().endsWith(".jar")) {
                        try {
                            PluginDescriptionFile desc = PluginEX.getInstance().getPluginLoader().getPluginDescription(f);
                            if (desc.getName().equalsIgnoreCase(name)) {
                                pluginFile = f;
                                break;
                            }
                        } catch (InvalidDescriptionException var11) {
                            sender.sendMessage(ChatColor.RED + Util.format("plugin.illegal.failed"));
                            return;
                        }
                    }
                }
            }

            try {
                target = Bukkit.getPluginManager().loadPlugin(pluginFile);
            } catch (InvalidDescriptionException var9) {
                var9.printStackTrace();
                sender.sendMessage(ChatColor.RED + Util.format("plugin.illeagl.description"));
                return;
            } catch (InvalidPluginException var10) {
                var10.printStackTrace();
                sender.sendMessage(ChatColor.RED + Util.format("plugin.illegal.danger"));
                return;
            }

            target.onLoad();
            Bukkit.getPluginManager().enablePlugin(target);
            sender.sendMessage(ChatColor.GREEN + Util.format("plugin.load"));
        }
    }

    public static void listPlugins(CommandSender sender) {
        PluginManager manager = Bukkit.getPluginManager();
        int all = 0, enabled = 0;
        for (Plugin plugin : manager.getPlugins()) {
            boolean isEnable = plugin.isEnabled();
            if (isEnable) enabled++;
            sender.sendMessage((isEnable ? ChatColor.GREEN : ChatColor.RED) +
                    plugin.getDescription().getName() + ", ");
            all++;
        }
        sender.sendMessage(new String[]{
                "\n",
                ChatColor.GRAY + String.format("%d plugins | %d enabled", all, enabled)
        });
    }

    public static void disable(Plugin plugin) {
        if (plugin != null && plugin.isEnabled()) {
            Bukkit.getPluginManager().disablePlugin(plugin);
        }
    }

    public static void disableAll() {
        Plugin[] plugins = Bukkit.getPluginManager().getPlugins();
        for (Plugin plugin : plugins) {
            disable(plugin);
        }
    }

    public static void unload(CommandSender sender,Plugin plugin) {
        String name = plugin.getName();
        PluginManager pluginManager = Bukkit.getPluginManager();
        SimpleCommandMap commandMap = null;
        List<Plugin> plugins = null;
        Map<String, Plugin> names = null;
        Map<String, Command> commands = null;
        Map<Event, SortedSet<RegisteredListener>> listeners = null;
        boolean reloadlisteners = true;
        if (pluginManager != null) {
            pluginManager.disablePlugin(plugin);

            try {
                Field pluginsField = Bukkit.getPluginManager().getClass().getDeclaredField("plugins");
                pluginsField.setAccessible(true);
                plugins = (List)pluginsField.get(pluginManager);
                Field lookupNamesField = Bukkit.getPluginManager().getClass().getDeclaredField("lookupNames");
                lookupNamesField.setAccessible(true);
                names = (Map)lookupNamesField.get(pluginManager);

                Field commandMapField;
                try {
                    commandMapField = Bukkit.getPluginManager().getClass().getDeclaredField("listeners");
                    commandMapField.setAccessible(true);
                    listeners = (Map)commandMapField.get(pluginManager);
                } catch (Exception var14) {
                    reloadlisteners = false;
                }

                commandMapField = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
                commandMapField.setAccessible(true);
                commandMap = (SimpleCommandMap)commandMapField.get(pluginManager);
                Field knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");
                knownCommandsField.setAccessible(true);
                commands = (Map)knownCommandsField.get(commandMap);
            } catch (NoSuchFieldException var15) {
                var15.printStackTrace();
                sender.sendMessage(ChatColor.RED+Util.format("plugin.unload.failed"));
                return;
            } catch (IllegalAccessException var16) {
                var16.printStackTrace();
                sender.sendMessage(ChatColor.RED+Util.format("plugin.unload.failed"));
                return;
            }
        }

        pluginManager.disablePlugin(plugin);
        if (plugins != null && plugins.contains(plugin)) {
            plugins.remove(plugin);
        }

        if (names != null && names.containsKey(name)) {
            names.remove(name);
        }

        Iterator it;
        if (listeners != null && reloadlisteners) {
            it = listeners.values().iterator();

            while(it.hasNext()) {
                SortedSet<RegisteredListener> set = (SortedSet)it.next();
                Iterator<RegisteredListener> itt = set.iterator();

                while(itt.hasNext()) {
                    RegisteredListener value = (RegisteredListener)itt.next();
                    if (value.getPlugin() == plugin) {
                        itt.remove();
                    }
                }
            }
        }

        if (commandMap != null) {
            it = commands.entrySet().iterator();

            while(it.hasNext()) {
                Map.Entry<String, Command> entry = (Map.Entry)it.next();
                if (entry.getValue() instanceof PluginCommand) {
                    PluginCommand c = (PluginCommand)entry.getValue();
                    if (c.getPlugin() == plugin) {
                        c.unregister(commandMap);
                        it.remove();
                    }
                }
            }
        }

        ClassLoader cl = plugin.getClass().getClassLoader();
        if (cl instanceof URLClassLoader) {
            try {
                ((URLClassLoader)cl).close();
            } catch (IOException var13) {
                Logger.getLogger(PluginEX.class.getName()).log(Level.SEVERE, (String)null, var13);
            }
        }

        System.gc();
        sender.sendMessage(ChatColor.GREEN+Util.format("plugin.unload.success"));
    }

    public static void reload(CommandSender sender,Plugin plugin) {
        if (plugin != null) {
            unload(sender,plugin);
            load(sender,plugin.getName());
        }
    }

    public static void reloadAll(CommandSender sender) {
        Plugin[] plugins = Bukkit.getPluginManager().getPlugins();
        for (Plugin plugin : plugins) {
            reload(sender,plugin);
        }
    }
}
