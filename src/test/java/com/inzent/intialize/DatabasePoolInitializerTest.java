package com.inzent.intialize;

import com.inzent.initialize.database.DatabaseConfigInitializer;
import com.inzent.initialize.database.DataSourcePoolInitializer;
import com.inzent.pool.database.DatabaseName;
import com.inzent.pool.database.DataSourcePool;
import org.apache.commons.dbcp2.BasicDataSource;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.Optional;

public class DatabasePoolInitializerTest {

    private Logger logger = LoggerFactory.getLogger(DatabasePoolInitializerTest.class);


    @Before
    public void setUp() {
        DatabaseConfigInitializer databaseConfigInitializer = DatabaseConfigInitializer.getInstance();
        databaseConfigInitializer.initialize();
    }


    @Test
    public void initializeTest() {
        DataSourcePoolInitializer databasePoolInitializer = DataSourcePoolInitializer.getInstance();
        databasePoolInitializer.initialize();

        DataSourcePool databasePool = DataSourcePool.getInstance();
        Optional<DataSource> optionalDataSourceEDMS = databasePool.getDatasource(DatabaseName.EDMS);

        DataSourcePool databasePool1 = DataSourcePool.getInstance();
        Optional<DataSource> optionalDataSourceMASK = databasePool1.getDatasource(DatabaseName.MASK);


        Assertions.assertThat(optionalDataSourceEDMS)
                .isNotNull()
                .containsInstanceOf(DataSource.class)
                .containsInstanceOf(BasicDataSource.class);

        Assertions.assertThat(optionalDataSourceMASK)
                .isNotNull()
                .containsInstanceOf(DataSource.class)
                .containsInstanceOf(BasicDataSource.class);

        BasicDataSource basicDataSourceEDMS = (BasicDataSource)optionalDataSourceEDMS.get();
        BasicDataSource basicDataSourceMASK = (BasicDataSource)optionalDataSourceMASK.get();
        logger.debug("EDMS BasicDataSource.toString() {}", basicDataSourceEDMS.toString());
        logger.debug("MASK BasicDataSource.toString() {}", basicDataSourceMASK.toString());
    }
}
