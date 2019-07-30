package com.inzent.pool.database;

import com.inzent.entity.DatabaseEntity;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class DataSourcePool {

    private static final DataSourcePool databasePool = new DataSourcePool();

    private DatabaseConfigPool databaseConfigPool = DatabaseConfigPool.getInstance();

    private final Map<DatabaseName, DataSource> connectionPoolMap = new ConcurrentHashMap();

    private DataSourcePool() {
    }

    public static DataSourcePool getInstance() {
        return databasePool;
    }


    public void putDataSource(DatabaseName databaseName, DataSource dataSource) {
        connectionPoolMap.put(databaseName, dataSource);
    }

    public Optional<DataSource> getDatasource(DatabaseName databaseName) {
        return Optional.ofNullable(connectionPoolMap.get(databaseName));
    }




}
