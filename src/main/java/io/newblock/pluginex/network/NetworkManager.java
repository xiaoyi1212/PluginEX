package io.newblock.pluginex.network;

import io.newblock.pluginex.PluginEX;
import io.newblock.pluginex.util.Util;
import org.bukkit.ChatColor;

import java.net.ProxySelector;

public class NetworkManager {
    PluginEX instance;
    public NetworkManager(PluginEX instance){
        this.instance = instance;
    }

    public void launch(){
        this.instance.getLogger().info(ChatColor.GOLD+ Util.format("network.register"));
        ProxySelector.setDefault(new ProxyServer(ProxySelector.getDefault(), instance));
    }

    public void disable(){
        ProxySelector cur = ProxySelector.getDefault();
        if (cur instanceof ProxyServer) {
            ProxySelector.setDefault(((ProxyServer)cur).getDefaultProxy());
        }
    }
}
