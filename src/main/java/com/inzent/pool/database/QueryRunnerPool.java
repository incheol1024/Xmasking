package com.inzent.pool.database;

import org.apache.commons.dbutils.QueryRunner;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class QueryRunnerPool {

    private static final QueryRunnerPool queryRunnerPool = new QueryRunnerPool();

    private final Map<DatabaseName, QueryRunner> queryRunnerMap = new ConcurrentHashMap<>();

    private QueryRunnerPool() {

    }

    public static QueryRunnerPool getIntance() {
        return queryRunnerPool;
    }

    public void configureQueryRunner(DatabaseName databaseName) {
        DataSource dataSource = getDatasource(databaseName);
        queryRunnerMap.put(databaseName, new QueryRunner(dataSource));
    }

    private DataSource getDatasource(DatabaseName databaseName) {
        Optional<DataSource> optionalDataSource = DataSourcePool.getInstance().getDatasource(databaseName);
        return optionalDataSource.orElseThrow(() -> new RuntimeException("Not Found DataSource, database name " + databaseName.name()));
    }

    public Optional<QueryRunner> getQueryRunner(DatabaseName databaseName) {
        return Optional.ofNullable(queryRunnerMap.get(databaseName));
    }

}
