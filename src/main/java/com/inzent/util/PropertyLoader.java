package com.inzent.util;

import java.io.IOException;
import java.util.Properties;

public class PropertyLoader {

    private static Properties prop = new Properties();

    static {
        try {
            prop.load(PropertyLoader.class.getClassLoader().getResourceAsStream("app.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private PropertyLoader() {
        throw new AssertionError();
    }

    public static Properties getAppProperty() {
        return prop;
    }


/*
    public static PropertyLoader getInstance() {
        return PropertyLoaderSingleton.getInstance();
    }

    private static class PropertyLoaderSingleton {

        private static PropertyLoader propertyLoader = new PropertyLoader();

        private static PropertyLoader getInstance() {
            return propertyLoader;
        }
    }
*/

}
