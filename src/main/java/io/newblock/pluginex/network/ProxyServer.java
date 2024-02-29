package io.newblock.pluginex.network;

import io.newblock.pluginex.PluginEX;
import io.newblock.pluginex.util.EXPluginManager;
import io.newblock.pluginex.util.Util;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.List;

public class ProxyServer extends ProxySelector {
    ProxySelector default_proxy;
    PluginEX instance;

    public ProxyServer(ProxySelector default_proxy, PluginEX instance){
        this.default_proxy = default_proxy;
        this.instance = instance;
    }

    public ProxySelector getDefaultProxy() {
        return default_proxy;
    }

    @Override
    public List<Proxy> select(URI uri) {
        if(uri.toString().startsWith("socket")){
            return this.default_proxy.select(uri);
        }else {
            AsyncPluginNetworkEvent event = new AsyncPluginNetworkEvent(EXPluginManager.getOperatePlugin(),uri, Bukkit.isPrimaryThread());
            Bukkit.getPluginManager().callEvent(event);

            if(instance.isWhite()){
                if(!instance.getWhiteList().contains(uri.toString())){
                    throw new RuntimeException(Util.format("network.intercept"));
                }
            }else {
                for(String uri_str : instance.getDarkList()){
                    if(uri_str.contains(uri.toString()))
                        throw new RuntimeException(Util.format("network.intercept"));
                }
            }

            if(event.isCancelled()){
                throw new RuntimeException(Util.format("network.intercept"));
            }
            return this.default_proxy.select(uri);
        }
    }

    @Override
    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
        this.default_proxy.connectFailed(uri, sa, ioe);
    }
}
