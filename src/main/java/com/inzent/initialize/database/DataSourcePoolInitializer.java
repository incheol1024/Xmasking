package com.inzent.initialize.database;

import com.inzent.entity.DatabaseEntity;
import com.inzent.pool.database.DatabaseConfigPool;
import com.inzent.pool.database.DatabaseName;
import com.inzent.pool.database.DataSourcePool;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.Optional;

public class DataSourcePoolInitializer implements DatabaseInitializer {

    private Logger logger = LoggerFactory.getLogger(DataSourcePoolInitializer.class);

    private static final DataSourcePoolInitializer databasePoolInitializer = new DataSourcePoolInitializer();

    private final DataSourcePool dataSourcePool = DataSourcePool.getInstance();

    private DataSourcePoolInitializer() {

    }

    public static DataSourcePoolInitializer getInstance() {
        return databasePoolInitializer;
    }


    @Override
    public void initialize() {

        logger.trace("Initialize {} ", getClass().getName());

        for (DatabaseName databaseName : DatabaseName.values()) {
            this.setDataSourcePool(databaseName);
        }

        logger.trace("Complete initializing {} ", getClass().getName());

    }

    private void setDataSourcePool(DatabaseName databaseName) {
        DataSource dataSource = configureDataSource(databaseName);
        dataSourcePool.putDataSource(databaseName, dataSource);
        logger.trace(databaseName.name() + " pooling DataSource {}", dataSourcePool.getDatasource(databaseName).get());
    }

    private DataSource configureDataSource(DatabaseName databaseName) {

        DatabaseConfigPool databaseConfigPool = DatabaseConfigPool.getInstance();
        Optional<DatabaseEntity> optionalDatabaseEntity = databaseConfigPool.getDatabaseEntity(databaseName);
        DatabaseEntity databaseEntity =
                optionalDatabaseEntity.orElseThrow(() -> new RuntimeException("Not Found DatabaseEntity of DatabaseName " + databaseName.name()));

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(databaseEntity.getDriver());
        basicDataSource.setUrl(databaseEntity.getUrl());
        basicDataSource.setUsername(databaseEntity.getUser());
        basicDataSource.setPassword(databaseEntity.getPassword());
        basicDataSource.setMaxTotal(databaseEntity.getConnectionPoolCount());

        return basicDataSource;
    }
}
