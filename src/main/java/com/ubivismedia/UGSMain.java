package com.ubivismedia;

import com.ubivismedia.command.UGSCommand;
import com.ubivismedia.command.ReloadCommand;
import com.ubivismedia.extension.ExtensionManager;

import org.bukkit.plugin.java.JavaPlugin;

public class UGSMain extends JavaPlugin {
    private ExtensionManager extensionManager;

    @Override
    public void onEnable() {
        this.extensionManager = new ExtensionManager(this);

        registerCommands();

        extensionManager.loadExtensions();

        getLogger().info("UGS Plugin aktiviert!");
    }

    @Override
    public void onDisable() {
        extensionManager.unloadExtensions();
        getLogger().info("UGS Plugin deaktiviert!");
    }

    private void registerCommands() {
        if (getCommand("ugs") != null) {
            getCommand("ugs").setExecutor(new UGSCommand(extensionManager));
        } else {
            getLogger().severe("Command 'ugs' was not found! Ensure it is registered in plugin.yml.");
        }

        if (getCommand("ugsreload") != null) {
            getCommand("ugsreload").setExecutor(new ReloadCommand(extensionManager));
        } else {
            getLogger().severe("Command 'ugsreload' was not found! Ensure it is registered in plugin.yml.");
        }
    }

}
