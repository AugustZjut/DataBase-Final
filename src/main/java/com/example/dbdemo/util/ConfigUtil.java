package com.example.dbdemo.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {
    private static final Properties props = new Properties();
    static {
        try (InputStream in = ConfigUtil.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in != null) {
                props.load(in);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String get(String key) {
        return props.getProperty(key);
    }
    public static String getCurrentSemester() {
        return get("current.semester");
    }
    public static String getSemesterList() {
        return get("semester.list");
    }
}
