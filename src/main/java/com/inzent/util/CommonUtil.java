package com.inzent.util;

import com.inzent.pool.database.DatabaseName;
import com.inzent.pool.database.QueryRunnerPool;
import org.apache.commons.dbutils.QueryRunner;

import java.util.Optional;

public final class CommonUtil {

    public static QueryRunner getQueryRunner(DatabaseName databaseName) {
        QueryRunnerPool queryRunnerPool = QueryRunnerPool.getInstance();
        Optional<QueryRunner> queryRunnerOptional = queryRunnerPool.getQueryRunner(databaseName);
        return queryRunnerOptional.orElseThrow(() -> new RuntimeException("Not Found QueryRunner DatabaseName: " + databaseName.name()));
    }

    public static boolean isPastElementId(String elementId) {
        if (elementId.startsWith("ED"))
            return true;

        int date = Integer.valueOf(elementId.substring(2, 8));
        if (date <= 190401)
            return true;

        return false;
    }
}
