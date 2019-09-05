package com.inzent.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Hashtable;
import java.util.Set;

public final class Stoper {

    private volatile static boolean stopping = false;

    private volatile static Hashtable<String, Boolean> stopIdTable = new Hashtable();

    private static Logger logger = LoggerFactory.getLogger(Stoper.class);

    public static void stop() {
        stopping = true;
    }

    public static boolean isStop() {
        return stopping;
    }

    public static void addStopIdTable(String stopId, boolean status) {
        stopIdTable.put(stopId, status);
    }

    public static void modifyStatusInStopIdTable(String stopId, boolean status) {
        if(stopIdTable.get(stopId) == null) {
            logger.error("Cannot find stopId in stopIdTable , stopId is {} ", stopId);
            return;
        }
        stopIdTable.put(stopId, status);
    }

    public static Hashtable<String, Boolean> getStopIdTable() {
        return stopIdTable;
    }

    public static boolean isAllStop() {
        Set<String> keySet = stopIdTable.keySet();
        for (String key : keySet) {
            if (!stopIdTable.get(key))
                return false;
        }
        return true;
    }
}
