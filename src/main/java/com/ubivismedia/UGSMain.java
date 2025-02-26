package com.ubivismedia;

import com.ubivismedia.command.UGSCommand;
import com.ubivismedia.command.ReloadCommand;
import com.ubivismedia.extension.ExtensionManager;

import org.bukkit.command.PluginCommand;
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
        getServer().getCommandMap().register("ugs", new UGSCommand(extensionManager));
        getServer().getCommandMap().register("ugsreload", new ReloadCommand(extensionManager));
    }
    if (getCommand("ugsreload") != null) {
        getCommand("ugsreload").setExecutor(new ReloadCommand(extensionManager));
    }
}
}
