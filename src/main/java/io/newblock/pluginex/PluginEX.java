package io.newblock.pluginex;

import io.newblock.pluginex.command.EXCommandHandler;
import io.newblock.pluginex.util.Util;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public final class PluginEX extends JavaPlugin {

    File langFile;
    static PluginEX instance;

    @Override
    public void onLoad() {
        saveDefaultConfig();
        PluginEX.instance = this;
        if(!getDataFolder().exists()) getDataFolder().mkdir();
        this.langFile = new File(getDataFolder(),getConfig().getString("lang","zh-CN.lang"));
        Util.createLangFile(this.langFile,this);
        Util.loadLangInfo(langFile,this);
    }

    @Override
    public void onEnable() {
        EXCommandHandler handler = new EXCommandHandler(this);
        getCommand("pluginex").setExecutor(handler);
        getCommand("pluginex").setTabCompleter(handler);
        if(true){
            int a =1;
        }
        int a = 1;
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    public static PluginEX getInstance() {
        return instance;
    }
}
