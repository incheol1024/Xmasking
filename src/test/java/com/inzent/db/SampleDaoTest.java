package com.inzent.db;

import org.junit.Test;

import java.util.List;

public class SampleDaoTest {

    @Test
    public void findElementsTest() {
        SampleDao sampleDao = new SampleDao();
        List<Object[]> list = sampleDao.findElementIds();
        list.stream().forEach(objects -> System.out.println(objects[0]));
    }
}
