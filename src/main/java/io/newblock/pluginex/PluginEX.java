package io.newblock.pluginex;

import io.newblock.pluginex.command.EXCommandHandler;
import io.newblock.pluginex.gui.GuiListener;
import io.newblock.pluginex.network.NetworkManager;
import io.newblock.pluginex.util.Util;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.List;

public final class PluginEX extends JavaPlugin {

    File langFile;
    List<String> white_list_uri,dark_list_uri;
    NetworkManager networkManager;
    GuiListener guiListener;
    boolean isWhite;
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
        this.networkManager = new NetworkManager(this);
        this.guiListener = new GuiListener(this);
        this.white_list_uri = getConfig().getStringList("network.white_list");
        this.dark_list_uri = getConfig().getStringList("network.dark_list");
        this.isWhite = getConfig().getString("network.which","white_list").equals("white_list");
        if(getConfig().getBoolean("network.enable",true)){
            this.networkManager.launch();
        }
    }

    @Override
    public void onDisable() {
        this.networkManager.disable();
        saveConfig();
    }

    public boolean isWhite() {
        return isWhite;
    }

    public List<String> getDarkList() {
        return dark_list_uri;
    }

    public List<String> getWhiteList() {
        return white_list_uri;
    }

    public static PluginEX getInstance() {
        return instance;
    }
}
