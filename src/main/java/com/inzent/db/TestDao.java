package com.inzent.db;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestDao {

    private static ConnectionPool connectionPool = new ConnectionPool();
    private static QueryRunner queryRunner = connectionPool.getQueryRunner();

    public int countProduct() {

        int size = 0;
        try {
            size = queryRunner.query("select count(*) from PRODUCT", (rs) -> {
                rs.next();
                return rs.getInt(1);
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return size;
    }

    public void addProduct() {

        ResultSetHandler<Integer> resultSetHandler = new ScalarHandler<>();
        String sql = "insert into PRODUCT (PRODUCT_ID, PRODUCT_NAME) values (?,?)";

        try {
            Integer result = queryRunner.insert(sql, resultSetHandler,  "3", "java");
            System.out.println(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}
