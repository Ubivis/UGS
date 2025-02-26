package com.ubivismedia.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerUtil {
    private static final Logger LOGGER = Logger.getLogger("UGS");
    private static final String LOG_FILE_PATH = "plugins/UGS/logs/load-log.txt";

    static {
        // Stelle sicher, dass das Logs-Verzeichnis existiert
        File logFile = new File(LOG_FILE_PATH);
        logFile.getParentFile().mkdirs();
    }

    public static void logInfo(String message) {
        LOGGER.log(Level.INFO, message);
    }

    public static void logWarning(String message) {
        LOGGER.log(Level.WARNING, message);
        writeToFile("WARNING: " + message);
    }

    public static void logError(String message) {
        LOGGER.log(Level.SEVERE, message);
        writeToFile("ERROR: " + message);
    }

    private static void writeToFile(String message) {
        try (FileWriter writer = new FileWriter(LOG_FILE_PATH, true)) {
            writer.write("[" + java.time.LocalDateTime.now() + "] " + message + System.lineSeparator());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to write to log file: " + e.getMessage());
        }
    }
}
