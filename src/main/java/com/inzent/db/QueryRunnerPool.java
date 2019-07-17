package com.inzent.db;

import com.inzent.util.AppProperty;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.util.Properties;

public class QueryRunnerPool {

    private static final QueryRunner MASK_QUERY_RUNNER;

    static QueryRunner edmsQueryRunner;


    static Properties properties = AppProperty.getProperties();

    static {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDefaultAutoCommit(true);
        basicDataSource.setDriverClassName(properties.getProperty("DB_DRIVER"));
        basicDataSource.setUrl(properties.getProperty("DB_URL"));
        basicDataSource.setUsername(properties.getProperty("DB_USER"));
        basicDataSource.setPassword(properties.getProperty("DB_PASSWORD"));

        DbUtils.loadDriver(properties.getProperty("DB_DRIVER"));

        MASK_QUERY_RUNNER = new QueryRunner(basicDataSource);

    }

    public QueryRunnerPool() {

    }

    public static QueryRunner getQueryRunner() {
        return MASK_QUERY_RUNNER;
    }

}
