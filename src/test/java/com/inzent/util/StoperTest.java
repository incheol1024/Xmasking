package com.inzent.util;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.util.Hashtable;
import java.util.Set;

public class StoperTest {

    @Before
    public void setUp() {
        Stoper.addStopIdTable("0001", false);
        Stoper.addStopIdTable("0002", false);
        Stoper.addStopIdTable("replace", false);
    }

    @Test
    public void stop() {
    }

    @Test
    public void isStop() {
    }

    @Test
    public void addStopIdTable() {

        Hashtable<String, Boolean> table = Stoper.getStopIdTable();

        Set<String> set =  table.keySet();

        for(String key : set ) {
            System.out.println(table.get(key));
        }
    }

    @Test
    public void modifyStatusInStopIdTable() {

        Stoper.modifyStatusInStopIdTable("0001", true);
        Stoper.modifyStatusInStopIdTable("0002", true);
        Stoper.modifyStatusInStopIdTable("replace", true);

        Hashtable<String, Boolean> table = Stoper.getStopIdTable();
        Set<String> set =  table.keySet();

        for(String key : set ) {
            System.out.println(table.get(key));
            Assertions.assertThat(table.get(key)).isTrue();
        }
    }

    @Test
    public void isAllStop() {
        Stoper.modifyStatusInStopIdTable("0001", true);
        Stoper.modifyStatusInStopIdTable("0002", true);
        Stoper.modifyStatusInStopIdTable("replace", true);

        boolean ret = Stoper.isAllStop();

        System.out.println(ret);
        Assertions.assertThat(ret).isTrue();

    }
}