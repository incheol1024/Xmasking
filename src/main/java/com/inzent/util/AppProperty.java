package com.inzent.util;

import java.util.Properties;

public class AppProperty {

    private static final Properties properties = PropertyLoader.getPropery("app.properties");

    /**
     * @return Properties Object of default name app.properties
    */
    public static Properties getProperties() {
        return properties;
    }

    public static Properties getProperties(String propertiesName) {
        return PropertyLoader.getPropery(propertiesName);
    }

    public static String getValue(String key) {
        return properties.getProperty(key);
    }
}
