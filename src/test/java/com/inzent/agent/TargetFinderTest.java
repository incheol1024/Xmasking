package com.inzent.agent;

import com.inzent.initialize.database.DataSourcePoolInitializer;
import com.inzent.initialize.database.DatabaseConfigInitializer;
import com.inzent.initialize.database.QueryRunnerInitializer;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

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
        targetFinder.getQueryList();
    }
}
