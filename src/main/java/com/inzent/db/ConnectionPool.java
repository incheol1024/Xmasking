package com.inzent.db;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;

public class ConnectionPool {

    QueryRunner queryRunner;

    public ConnectionPool() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDefaultAutoCommit(false);
        basicDataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        basicDataSource.setUrl("jdbc:oracle:thin:@jpa.c4hnk07tn7ap.ap-northeast-2.rds.amazonaws.com:1521:ORCL");
        basicDataSource.setUsername("admin");
        basicDataSource.setPassword("frenchpie");

        DbUtils.loadDriver("oracle.jdbc.driver.OracleDriver");

        queryRunner = new QueryRunner(basicDataSource);
    }

    public QueryRunner getQueryRunner() {
        return queryRunner;
    }

}
