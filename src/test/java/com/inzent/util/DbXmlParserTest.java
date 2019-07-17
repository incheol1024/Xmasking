package com.inzent.util;

import com.inzent.entity.DatabaseEntity;
import org.junit.Test;

public class DbXmlParserTest {

    @Test
    public void parseTest() {
        DbXmlParser dbXmlParser = new DbXmlParser();
        DatabaseEntity databaseEntity = dbXmlParser.paser();
        System.out.println(databaseEntity.toString());
    }
}
