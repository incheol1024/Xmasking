package com.inzent.db;

import org.junit.Test;

public class TestDaoTest {

    @Test
    public void countProductTest() {
        TestDao testDao = new TestDao();
        int a = testDao.countProduct();
        System.out.println(a);
    }

    @Test
    public void addProductTest() {
        TestDao testDao = new TestDao();
        testDao.addProduct();

    }
}
