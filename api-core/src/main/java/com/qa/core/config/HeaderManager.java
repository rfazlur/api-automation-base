package com.qa.core.config;

import java.util.HashMap;
import java.util.Map;

public class HeaderManager {
    private HeaderManager() {
    }

    public static Map<String, String> getDefaultHeaders() {
        Map<String, String> headers = new HashMap<>();

        headers.put("device", "2");
        headers.put("key", "");
        headers.put("version", "");
        String token = ConfigManager.get("auth.token", "");
        if (!token.isBlank()) {
            headers.put("Authorization", token);
        }

        return headers;
    }
}
