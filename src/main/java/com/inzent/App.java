package com.inzent;

import org.apache.commons.dbutils.QueryLoader;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Hello world!
 */
public class App extends QueryLoader{
    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");

        Boot boot = new Boot();
        boot.boot();

    }
}
