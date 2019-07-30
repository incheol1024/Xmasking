package com.inzent.pool.database;

import com.inzent.entity.DatabaseEntity;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class DatabaseConfigPool {

    private static final Map<DatabaseName, DatabaseEntity> DB_CONFIG_POOL = new ConcurrentHashMap<>();

    private static final DatabaseConfigPool databaseConfigPool = new DatabaseConfigPool();

    private DatabaseConfigPool() {
    }

    public void putDatabaseEntity(DatabaseName databaseName, DatabaseEntity databaseEntity) {
        DB_CONFIG_POOL.put(databaseName, databaseEntity);
    }

    public Optional<DatabaseEntity> getDatabaseEntity(DatabaseName databaseName) {
        return Optional.ofNullable(DB_CONFIG_POOL.get(databaseName));
    }

    public static DatabaseConfigPool getInstance() {
        return databaseConfigPool;
    }


}
