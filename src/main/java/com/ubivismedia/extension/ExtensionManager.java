package com.ubivismedia.extension;

import com.ubivismedia.UGSMain;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


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

                // Finde die Klasse in der JAR, die ExtensionInterface implementiert
                for (String className : getClassNamesFromJar(file)) {
                    Class<?> clazz = classLoader.loadClass(className);
                    if (ExtensionInterface.class.isAssignableFrom(clazz)) {
                        ExtensionInterface extension = (ExtensionInterface) clazz.getDeclaredConstructor().newInstance();
                        extensions.put(extension.getName(), extension);
                        plugin.getLogger().info("[UGS] Geladene Extension: " + extension.getName() + " (" + className + ")");
                        break; // Falls eine gültige Klasse gefunden wurde, breche die Schleife ab
                    }
                }
            } catch (Exception e) {
                plugin.getLogger().severe("[UGS] Fehler beim Laden der Extension: " + file.getName());
                e.printStackTrace();
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

    public List<String> getClassNamesFromJar(File jarFile) {
        List<String> classNames = new ArrayList<>();

        try (JarFile jar = new JarFile(jarFile)) {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.getName().endsWith(".class")) {
                    // Konvertiere den JAR-Pfad in einen echten Klassennamen
                    String className = entry.getName()
                            .replace("/", ".")  // Pfadtrennzeichen zu Paketnamen ändern
                            .replace("\\", ".") // Für Windows
                            .replace(".class", ""); // Entferne ".class"
                    classNames.add(className);
                }
            }
        } catch (IOException e) {
            plugin.getLogger().severe("[UGS] Fehler beim Lesen der JAR-Datei: " + jarFile.getName());
            e.printStackTrace();
        }

        return classNames;
    }
}
