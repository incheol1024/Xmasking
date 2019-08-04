package com.inzent.util;

import com.inzent.pool.database.DatabaseName;
import com.inzent.pool.database.QueryRunnerPool;
import org.apache.commons.dbutils.QueryRunner;

import javax.management.Query;
import java.util.Optional;

public class CommonUtil {


    public static QueryRunner getQueryRunner(DatabaseName databaseName) {
        QueryRunnerPool queryRunnerPool = QueryRunnerPool.getIntance();
        Optional<QueryRunner> queryRunnerOptional = queryRunnerPool.getQueryRunner(databaseName);
        return queryRunnerOptional.orElseThrow(() -> new RuntimeException("Not Found QueryRunner DatabaseName: " + databaseName.name()));
    }
}
