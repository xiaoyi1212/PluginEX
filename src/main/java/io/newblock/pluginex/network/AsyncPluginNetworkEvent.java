package io.newblock.pluginex.network;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.server.ServerEvent;
import org.bukkit.plugin.Plugin;

import java.net.URI;

public class AsyncPluginNetworkEvent extends ServerEvent implements Cancellable {
    HandlerList handler = new HandlerList();
    Plugin plugin;
    URI uri;
    boolean isPrimaryThread;
    boolean cancel;

    public AsyncPluginNetworkEvent(Plugin plugin,URI uri,boolean isPrimaryThread){
        this.plugin = plugin;
        this.uri = uri;
        this.isPrimaryThread = isPrimaryThread;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public URI getUri() {
        return uri;
    }

    public boolean isPrimaryThread() {
        return isPrimaryThread;
    }

    @Override
    public HandlerList getHandlers() {
        return handler;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}
