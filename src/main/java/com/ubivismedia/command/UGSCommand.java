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
