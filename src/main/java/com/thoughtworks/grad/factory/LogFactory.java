package com.thoughtworks.grad.factory;

import java.util.HashMap;
import java.util.Map;

public class LogFactory {
    public static final Map<String, String> LOGGER_INFO = new HashMap<>();

    public static void logInfo(Class<?> clazz, String info) {
        LOGGER_INFO.put(clazz.getSimpleName(), info);
    }

    public static void clear() {
        LOGGER_INFO.clear();
    }

    public static String getInfoLog(String className) {
        return LOGGER_INFO.get(className);
    }
}
