package com.ubivismedia;

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
        
        getLogger().info("UGS Plugin aktiviert!");
    }
    
    @Override
    public void onDisable() {
        extensionManager.unloadExtensions();
        getLogger().info("UGS Plugin deaktiviert!");
    }
}

// UGSCommand.java
package com.ubivismedia.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import com.ubivismedia.extension.ExtensionManager;

public class UGSCommand implements CommandExecutor {
    private final ExtensionManager extensionManager;

    public UGSCommand(ExtensionManager extensionManager) {
        this.extensionManager = extensionManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("§cBitte eine Extension angeben.");
            return true;
        }

        String extensionName = args[0];
        String[] parameters = new String[args.length - 1];
        System.arraycopy(args, 1, parameters, 0, args.length - 1);

        String result = extensionManager.executeExtension(extensionName, parameters);
        sender.sendMessage(result);
        return true;
    }
}

// ReloadCommand.java
package com.ubivismedia.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import com.ubivismedia.extension.ExtensionManager;

public class ReloadCommand implements CommandExecutor {
    private final ExtensionManager extensionManager;

    public ReloadCommand(ExtensionManager extensionManager) {
        this.extensionManager = extensionManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        extensionManager.reloadExtensions();
        sender.sendMessage("§aAlle Extensions wurden neu geladen.");
        return true;
    }
}
