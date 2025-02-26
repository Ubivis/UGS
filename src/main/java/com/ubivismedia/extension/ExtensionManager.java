package com.ubivismedia.extension;

import com.ubivismedia.UGSMain;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class ExtensionManager {
    private final UGSMain plugin;
    private final Map<String, ExtensionInterface> extensions = new HashMap<>();
    private final File extensionFolder;

    public ExtensionManager(UGSMain plugin) {
        this.plugin = plugin;
        this.extensionFolder = new File(plugin.getDataFolder(), "extensions");
        if (!extensionFolder.exists()) {
            extensionFolder.mkdirs();
        }
    }

    public void loadExtensions() {
        File[] files = extensionFolder.listFiles((dir, name) -> name.endsWith(".jar"));
        if (files == null) return;

        for (File file : files) {
            try {
                URLClassLoader classLoader = new URLClassLoader(new URL[]{file.toURI().toURL()}, plugin.getClass().getClassLoader());
                Class<?> clazz = classLoader.loadClass("com.ubivismedia.extension.ExtensionImplementation");
                if (ExtensionInterface.class.isAssignableFrom(clazz)) {
                    ExtensionInterface extension = (ExtensionInterface) clazz.getDeclaredConstructor().newInstance();
                    extensions.put(extension.getName(), extension);
                    plugin.getLogger().info("Geladene Extension: " + extension.getName());
                }
            } catch (Exception e) {
                plugin.getLogger().severe("Fehler beim Laden der Extension: " + file.getName());
            }
        }
    }

    public void unloadExtensions() {
        extensions.clear();
        plugin.getLogger().info("Alle Extensions wurden entladen.");
    }

    public void reloadExtensions() {
        unloadExtensions();
        loadExtensions();
    }

    public String executeExtension(String name, String[] params) {
        ExtensionInterface extension = extensions.get(name);
        if (extension == null) {
            return "§cExtension nicht gefunden: " + name;
        }
        return extension.execute(params);
    }
}
