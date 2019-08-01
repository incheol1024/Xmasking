package com.inzent.agent;

import com.inzent.dto.SqlParamDto;
import com.inzent.pool.database.DatabaseName;
import com.inzent.pool.database.QueryRunnerPool;
import org.apache.commons.dbutils.BaseResultSetHandler;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TargetFinder {

    private static QueryRunnerPool queryRunnerPool = QueryRunnerPool.getIntance();
    private static QueryRunner queryRunner = getQueryRunner();

    public Queue<String> getQueryList() throws SQLException {
// How ??..

//        ResultSetHandler<SqlParamDto> resultSetHandler = new BeanHandler<>(SqlParamDto.class);
        ResultSetHandler<SqlParamDto> resultSetHandler = (resultSet) -> {
            if(resultSet.next()) {
                SqlParamDto sqlParamDto = new SqlParamDto();
                sqlParamDto.setMaskOrder(resultSet.getString("mask_order"));
                sqlParamDto.setLctgCd(resultSet.getString("lctg_cd"));
                sqlParamDto.setMctgCd(resultSet.getString("mctg_cd"));
                sqlParamDto.setFormCd(resultSet.getString("form_cd"));
                sqlParamDto.setDcmIdBegin(resultSet.getString("dcm_id_begin"));
                sqlParamDto.setDcmIdEnd(resultSet.getString("dcm_id_end"));
                sqlParamDto.setVersion(resultSet.getString("version"));
                return sqlParamDto;
            }
            else {
                return null;
            }
        };


//        queryRunner.update("insert into xtorm.DOWN_RANGE values(?, ?, ?, ?, ?, ?, ?);", new Object(),"CF","02","06","4018","4019",1);

        SqlParamDto sqlParamDto = queryRunner.query("select * from xtorm.DOWN_RANGE", resultSetHandler);

        System.out.println(sqlParamDto);

        return new ConcurrentLinkedQueue<String>();
    }

    private static QueryRunner getQueryRunner() {
       return  queryRunnerPool.getQueryRunner(DatabaseName.MASK)
               .orElseThrow(() -> new RuntimeException("Not Found QueryRunner DatabaseName: "+ DatabaseName.MASK));
    }

}
