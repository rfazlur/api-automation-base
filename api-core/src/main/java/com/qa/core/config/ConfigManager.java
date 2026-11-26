
package com.qa.core.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public final class ConfigManager {

    private static final Properties PROPERTIES = new Properties();
    private static final String ENVIRONMENT;

    static {
        ENVIRONMENT = System.getProperty("env", "dev");
        loadProperties();
    }

    private ConfigManager() {
    }

    private static void loadProperties() {

        String configDir = System.getProperty("config.dir", "config");
        Path configPath = Paths.get(configDir, ENVIRONMENT + ".properties");

        try (InputStream input = Files.newInputStream(configPath)) {
            PROPERTIES.load(input);
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Failed to load config file: "
                            + configPath.toAbsolutePath(), e);
        }
    }

    public static String get(String key) {

        // Priority 1: Java System Properties
        String systemValue = System.getProperty(key);

        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue.trim();
        }

        // Priority 2: Environment Variables
        String envValue = getEnvironmentVariable(key);

        if (envValue != null && !envValue.isBlank()) {
            return envValue.trim();
        }

        // Priority 3: Properties File
        String propertyValue = PROPERTIES.getProperty(key);

        if (propertyValue == null || propertyValue.isBlank()) {
            throw new IllegalArgumentException(
                    "Configuration not found: " + key);
        }

        return propertyValue.trim();
    }

    public static String get(String key, String defaultValue) {
        try {
            return get(key);
        } catch (IllegalArgumentException e) {
            return defaultValue;
        }
    }

    public static int getInt(String key) {
        return parseInteger(key, get(key));
    }

    public static int getInt(String key, int defaultValue) {
        return parseInteger(
                key,
                get(key, String.valueOf(defaultValue))
        );
    }

    private static int parseInteger(String key, String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Configuration '" + key
                            + "' must be a valid integer, but received: '"
                            + value + "'",
                    e
            );
        }
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static String getEnvironment() {
        return ENVIRONMENT;
    }

    private static String getEnvironmentVariable(String key) {
        String envKey = key.replace(".", "_")
                .replace("-", "_")
                .toUpperCase();

        return System.getenv(envKey);
    }
}
