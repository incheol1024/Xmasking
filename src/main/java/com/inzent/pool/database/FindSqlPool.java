package com.inzent.pool.database;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.inzent.initialize.database.DatabaseInitializer.EdmsTable;

public class FindSqlPool {

    private static Map<EdmsTable, ConcurrentLinkedQueue<String>> concurrentHashMap = new ConcurrentHashMap<>();

    public void putFindSqlQueue(EdmsTable edmsTable, ConcurrentLinkedQueue<String> concurrentLinkedQueue) {
        concurrentHashMap.put(edmsTable, concurrentLinkedQueue);
    }

    public ConcurrentLinkedQueue<String> getFindSqlQueue(EdmsTable edmsTable) {
        return concurrentHashMap.get(edmsTable);
    }

    public boolean cleanAllMap() {
       concurrentHashMap.clear();

       boolean success = true;
       for(EdmsTable edmsTable : EdmsTable.values()) {
           if(concurrentHashMap.get(edmsTable) != null )
               success = false;
       }
       return success;
    }





}
