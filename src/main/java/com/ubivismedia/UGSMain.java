package com.ubivismedia;

import com.ubivismedia.command.*;
import com.ubivismedia.extension.ExtensionManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class UGSMain extends JavaPlugin {
    private ExtensionManager extensionManager;
    
    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.extensionManager = new ExtensionManager(this);
        
        getCommand("ugs").setExecutor(new UGSCommand(extensionManager));
        getCommand("ugs reload").setExecutor(new ReloadCommand(extensionManager));
        
        extensionManager.loadExtensions();
        
        getLogger().info("UGS Plugin activated!");
    }
    
    @Override
    public void onDisable() {
        extensionManager.unloadExtensions();
        getLogger().info("UGS Plugin deactivated!");
    }
}

