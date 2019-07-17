package com.inzent.util;

import java.io.IOException;
import java.util.Properties;

class PropertyLoader {

    private PropertyLoader() {
        throw new AssertionError();
    }

    static Properties getPropery(String propertyName) {

        Properties properties = new Properties();
        try {
            properties.load(PropertyLoader.class.getClassLoader().getResourceAsStream(propertyName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }

}
