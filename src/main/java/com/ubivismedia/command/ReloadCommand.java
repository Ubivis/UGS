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
        sender.sendMessage("§aAll extensions have been loaded.");
        return true;
    }
}
