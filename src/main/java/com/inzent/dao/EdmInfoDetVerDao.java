package com.inzent.dao;

import com.inzent.pool.database.DatabaseName;
import com.inzent.pool.database.QueryRunnerPool;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import java.sql.SQLException;
import java.util.List;

public class EdmInfoDetVerDao {

    private static QueryRunnerPool queryRunnerPool = QueryRunnerPool.getIntance();

    private static QueryRunner queryRunner = getQueryRunner();

    public List<String> findElements(String sql) {

        ResultSetHandler<List<String>> resultSetHandler = new ColumnListHandler<>("dcm_id");
        List<String> elements = null;
        try {
            elements = queryRunner.query(sql, resultSetHandler);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return elements;
    }

    private static QueryRunner getQueryRunner() {
        return queryRunnerPool.getQueryRunner(DatabaseName.EDMS)
                .orElseThrow(() -> new RuntimeException("Not Found QueryRunner DatabaseName: " + DatabaseName.EDMS.name()));
    }

}
