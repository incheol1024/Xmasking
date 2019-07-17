package com.inzent.db;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class SampleDao {


    public int countProduct() {
        QueryRunner queryRunner = QueryRunnerPool.getQueryRunner();

        int size = 0;
        try {
            size = queryRunner.query("select count(*) from asyscontentelement", (rs) -> {
                rs.next();
                return rs.getInt(1);
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return size;
    }

    public void addProduct() {

        QueryRunner queryRunner = QueryRunnerPool.getQueryRunner();

        ResultSetHandler<Integer> resultSetHandler = new ScalarHandler<>();
        String sql = "insert into PRODUCT (PRODUCT_ID, PRODUCT_NAME) values (?,?)";

        try {
            Integer result = queryRunner.insert(sql, resultSetHandler, "3", "java");
            System.out.println(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public List<Object[]> findElementIds() {

        QueryRunner queryRunner = QueryRunnerPool.getQueryRunner();

        ResultSetHandler<List<Object[]>> resultSetHandler = new ArrayListHandler();
        List<Object[]> results = null;
        try {
            results = queryRunner.query("select elementid from asyscontentelement", resultSetHandler);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }


}
