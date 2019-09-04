package com.inzent.intialize;

import com.inzent.initialize.database.DataSourcePoolInitializer;
import com.inzent.initialize.database.DatabaseConfigInitializer;
import com.inzent.initialize.database.QueryRunnerInitializer;
import com.inzent.pool.database.DatabaseName;
import com.inzent.pool.database.QueryRunnerPool;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Optional;

public class QueryRunnerInitializerTest {

    @Before
    public void setUp() {

        DatabaseConfigInitializer databaseConfigInitializer = DatabaseConfigInitializer.getInstance();
        databaseConfigInitializer.initialize();

        DataSourcePoolInitializer dataSourcePoolInitializer = DataSourcePoolInitializer.getInstance();
        dataSourcePoolInitializer.initialize();
    }

    @Test
    public void initializeTest() throws SQLException {

        QueryRunnerInitializer queryRunnerInitializer = QueryRunnerInitializer.getIntance();
        queryRunnerInitializer.initialize();

        QueryRunnerPool queryRunnerPool = QueryRunnerPool.getInstance();
        Optional<QueryRunner> optionalQueryRunner =  queryRunnerPool.getQueryRunner(DatabaseName.EDMS);

        Assertions.assertThat(optionalQueryRunner).containsInstanceOf(QueryRunner.class);

        QueryRunner queryRunner = optionalQueryRunner.orElseThrow(() -> new RuntimeException("Not Found QueryRunner database name " + DatabaseName.EDMS.name()));


        ResultSetHandler<String> resultSetHandler = new ScalarHandler<>(1);
        String elementid = queryRunner.query("select * from xtorm.element", resultSetHandler);

        System.out.println(elementid);





    }
}
