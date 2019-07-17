package com.inzent.util;

import java.util.Properties;

public class AppProperty {

    private static final Properties properties = PropertyLoader.getPropery("app.properties");

    public static Properties getProperties() {
        return properties;
    }
}
