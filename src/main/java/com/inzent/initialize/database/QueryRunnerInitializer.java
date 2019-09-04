package com.inzent.initialize.database;

import com.inzent.pool.database.DatabaseName;
import com.inzent.pool.database.QueryRunnerPool;

public class QueryRunnerInitializer implements DatabaseInitializer {

    private static final QueryRunnerInitializer queryRunnerInitializer = new QueryRunnerInitializer();

    private QueryRunnerInitializer() {

    }

    public static QueryRunnerInitializer getIntance() {
        return queryRunnerInitializer;
    }


    @Override
    public void initialize() {
        QueryRunnerPool queryRunnerPool = QueryRunnerPool.getInstance();
        queryRunnerPool.configureQueryRunner(DatabaseName.EDMS);
        queryRunnerPool.configureQueryRunner(DatabaseName.MASK);
    }
}
