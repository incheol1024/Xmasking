package com.inzent.util;

import org.apache.commons.dbutils.QueryLoader;
import org.junit.Test;

import java.io.*;
import java.util.Map;
import java.util.Set;

public class QueryLoaderTest {

    @Test
    public void pathTest() throws IOException {

        File file = new File("./");
        System.out.println(file.getCanonicalPath());

    }

    @Test
    public void queryLoaderTest() throws IOException {

        QueryLoader queryLoader = QueryLoader.instance();
        Map<String, String> queryMap = queryLoader.load("sql.properties");
        Set<String> keySet = queryMap.keySet();
        keySet.iterator().forEachRemaining(System.out::println);
    }

    @Test
    public void loadTest() throws IOException {
        InputStream inputStream = QueryLoaderTest.class.getClassLoader().getResourceAsStream("sql.properties");

        InputStreamReader inputStreamReader =new InputStreamReader(inputStream);



    }
}
