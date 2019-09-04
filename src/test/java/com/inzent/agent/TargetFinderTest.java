package com.inzent.agent;

import com.inzent.agent.lookup.TargetFinder;
import com.inzent.dto.edms.EdmsDetSqlParamDto;
import com.inzent.initialize.database.DataSourcePoolInitializer;
import com.inzent.initialize.database.DatabaseConfigInitializer;
import com.inzent.initialize.database.QueryRunnerInitializer;
import com.inzent.pool.database.DatabaseName;
import com.inzent.pool.database.QueryRunnerPool;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

public class TargetFinderTest {

    @Before
    public void setUp() {

        DatabaseConfigInitializer databaseConfigInitializer = DatabaseConfigInitializer.getInstance();
        DataSourcePoolInitializer dataSourcePoolInitializer = DataSourcePoolInitializer.getInstance();
        QueryRunnerInitializer queryRunnerInitializer = QueryRunnerInitializer.getIntance();

        databaseConfigInitializer.initialize();
        dataSourcePoolInitializer.initialize();
        queryRunnerInitializer.initialize();
    }


    @Test
    public void getQueryListTest() throws SQLException {

        TargetFinder targetFinder = new TargetFinder();
        Queue<String> sqlQueue = targetFinder.getFindSqlQueue(EdmsDetSqlParamDto.class);

        for (String sql : sqlQueue) {
            System.out.println(sql);
        }
    }

    @Test
    public void sqlListExecuteTest() throws SQLException {

        TargetFinder targetFinder = new TargetFinder();
        Queue<String> sqlQueue = targetFinder.getFindSqlQueue(EdmsDetSqlParamDto.class);


        Optional<QueryRunner> optionalQueryRunner = QueryRunnerPool.getInstance().getQueryRunner(DatabaseName.EDMS);
        QueryRunner queryRunner = optionalQueryRunner.orElseThrow(RuntimeException::new);

        sqlQueue.parallelStream()
                .map((sql) -> {
                    List<String> elementIds = null;
                    try {
                        elementIds = queryRunner.execute(sql, new ScalarHandler<String>());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return elementIds;
                })
        .parallel().forEach((elementIds) -> {
            for(String element : elementIds)
                System.out.println(element);
        });

    }
}
