package com.inzent.dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

public class DbUtilsTest {

    QueryRunner queryRunner;

    @Before
    public void setUp() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDefaultAutoCommit(false);
        basicDataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        basicDataSource.setUrl("jdbc:oracle:thin:@jpa.c4hnk07tn7ap.ap-northeast-2.rds.amazonaws.com:1521:ORCL");
        basicDataSource.setUsername("admin");
        basicDataSource.setPassword("frenchpie");

        DbUtils.loadDriver("oracle.jdbc.driver.OracleDriver");

        queryRunner = new QueryRunner(basicDataSource);
    }


    @Test
    public void addProductTest() {
        String sql =  "insert into PRODUCT (PRODUCT_ID, PRODUCT_NAME) values(4,'java')";
        try {
//            queryRunner.insert(sql,new ScalarHandler<Integer>(), 3, "java");
            int acutual = queryRunner.update(sql);
            IsEqual.equalTo(acutual).matches(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
