package me.mrfishcakes.matrixbridge;

import net.md_5.bungee.api.plugin.Plugin;

public final class MatrixBridgeBungeeCord extends Plugin {

    @Override
    public void onEnable() {
        getProxy().registerChannel("MatrixBridge");
    }

    @Override
    public void onDisable() {
        getProxy().unregisterChannel("MatrixBridge");
    }
}
