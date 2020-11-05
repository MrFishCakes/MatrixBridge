/*
 MIT License

 Copyright (c) 2020 MrFishCakes
 Copyright (c) 2020 Other Contributors

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
package me.mrfishcakes.matrixbridge;

import com.google.common.collect.ImmutableSet;
import me.mrfishcakes.matrixbridge.version.VersionChecker;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;

import java.util.Set;

public final class MatrixBridgeSpigot extends JavaPlugin {

    private static final Set<String> SERVER_TYPES = ImmutableSet.of("Paper", "Spigot", "Tuinity");

    private Messenger messenger;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        final PluginManager pluginManager = getServer().getPluginManager();

        if (!validSpigotType()) {
            getLogger().severe("You aren't running Spigot or a Spigot fork!");
            getLogger().severe("Update your server type to use MatrixBridge!");

            pluginManager.disablePlugin(this);
            return;
        }

        if (!checkNMSVersion()) {
            pluginManager.disablePlugin(this);
            return;
        }

        if (!checkBungeeCordEnabled()) {
            getLogger().severe("This server doesn't have BungeeCord enabled! " +
                    "Please enable it in spigot.yml");
            getLogger().severe("Help: " +
                    "https://www.spigotmc.org/wiki/spigot-configuration/#main-ungrouped-settings");

            pluginManager.disablePlugin(this);
            return;
        }

        messenger = getServer().getMessenger();
        messenger.registerOutgoingPluginChannel(this, "mb:matrixbridge");
    }

    @Override
    public void onDisable() {
        if (messenger.isOutgoingChannelRegistered(this, "mb:matrixbridge")) {
            messenger.unregisterOutgoingPluginChannel(this, "mb:matrixbridge");
        }

        messenger = null;
    }

    /**
     * Check that the server type is Spigot, PaperSpigot or Tuinty
     *
     * @return True if the server type above is found otherwise false
     * @since 0.0.1
     */
    private boolean validSpigotType() {
        final String serverType = getServer().getVersion().split("-")[1];
        return SERVER_TYPES.contains(serverType);
    }

    /**
     * Check that BungeeCord support is enabled in spigot.yml
     *
     * @return File value otherwise false
     * @since 0.0.1
     */
    private boolean checkBungeeCordEnabled() {
        return getServer().spigot().getConfig().getBoolean("settings.bungeecord", false);
    }

    /**
     * Checks that the NMS version is correct to use
     * Matrix *SHOULD* handle this however
     *
     * @return True if NMS is valid otherwise false
     * @since 0.0.1
     */
    private boolean checkNMSVersion() {
        switch (VersionChecker.checkType()) {
            case VALID:
                return true;
            case INVALID:
                getLogger().severe("You are using an incompatible version (1.9.X-1.11.X)");
                getLogger().severe("To use MatrixBridge please use 1.8.X or 1.12.X+");
                return false;
            case UNKOWN:
                if (getConfig().getBoolean("BypassUnkownVersion", false)) {
                    getLogger().warning("You are using an unkown Minecraft version!");
                    getLogger().warning("MatrixBridge will continue to work anyway!");
                    return true;
                } else {
                    getLogger().severe("You are using an unkown Minecraft version!");
                    getLogger().severe("MatrixBridge will disable to prevent issues!");
                    return false;
                }
            default:
                getLogger().severe("There was an error enabling MatrixBridge at versioning!");
                return false;
        }
    }
}
