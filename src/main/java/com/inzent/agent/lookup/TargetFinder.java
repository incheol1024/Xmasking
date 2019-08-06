package com.inzent.agent.lookup;

import com.inzent.dto.edms.EdmsCommonSqlParamDto;
import com.inzent.dto.edms.EdmsDetSqlParamDto;
import com.inzent.dto.edms.EdmsDetVerSqlParamDto;
import com.inzent.pool.database.DatabaseName;
import com.inzent.pool.database.QueryRunnerPool;
import com.inzent.util.AppProperty;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class TargetFinder {

    private Logger logger = LoggerFactory.getLogger(TargetFinder.class);

    private Properties properties = AppProperty.getProperties();

    private static QueryRunnerPool queryRunnerPool = QueryRunnerPool.getIntance();

    private static QueryRunner queryRunner = getQueryRunner();

    public <T extends EdmsCommonSqlParamDto> Queue<String> getFindSqlQueue(Class<T> classOfEdmsParamDto) throws SQLException {
        List<T> edmsSqlParamDtoList = this.getEdmsSqlParamDtoList(classOfEdmsParamDto);
        List<String> sqlList = supplySqlList(edmsSqlParamDtoList);
        return new ConcurrentLinkedQueue<>(sqlList);
    }

    private <T extends EdmsCommonSqlParamDto> List<String> supplySqlList(List<T> edmsSqlParamDtoList) {

        String initialSql = "";

        if (edmsSqlParamDtoList.get(0) instanceof EdmsDetSqlParamDto) {
            initialSql = properties.getProperty("LOOKUP_SQL");

        } else if (edmsSqlParamDtoList.get(0) instanceof EdmsDetVerSqlParamDto) {
            initialSql = properties.getProperty("LOOKUP_VERSION_SQL");
        }

        return null;
    }

    private <T extends EdmsCommonSqlParamDto> List<String> convertToSQL(List<T> edmsSqlParamDtoList) {

        if (edmsSqlParamDtoList.size() == 0) {
            throw new RuntimeException("EdmsParammDtoList size is 0. EDMS PARAM Table is normal? ");
        }

        return edmsSqlParamDtoList.stream().map((edmsParamDto) ->
                new StringBuffer().append("select dcm_id from")
                        .append(" ").append(edmsParamDto.getTable_name())
                        .append(" ").append("where")
                        .append(" lctg_cd=").append("\'" + edmsParamDto.getLctg_cd() + "\'")
                        .append(" and mctg_cd=").append(edmsParamDto.getMctg_cd())
                        .append(" and form_cd=").append(edmsParamDto.getForm_cd())
                        .append(" and dcm_id between ").append(edmsParamDto.getDcm_id_begin()).append(" and ").append(edmsParamDto.getDcm_id_end())
                        .toString())
                .peek((sql) -> logger.debug("created sql = {}", sql))
                .collect(Collectors.toList());
    }

    private <T extends EdmsCommonSqlParamDto> List<T> getEdmsSqlParamDtoList(Class<T> edmsDto) throws SQLException {
        ResultSetHandler<List<T>> resultSetHandler = new BeanListHandler(edmsDto);

        String prop = "";
        if (edmsDto.getName().equals(EdmsDetSqlParamDto.class.getName()))
            prop = "DOWN_DET_PARAMS";

        if (edmsDto.getName().equals(EdmsDetVerSqlParamDto.class.getName()))
            prop = "DOWN_DET_VER_PARAMS";

        List<T> list =
                queryRunner.query(properties.getProperty(prop), resultSetHandler);

        for (T edmsDetSqlParamDto : list) {
            logger.debug(edmsDto.getName() + " Dto Param: {} ", edmsDetSqlParamDto);
        }
        return list;
    }


    private static QueryRunner getQueryRunner() {
        return queryRunnerPool.getQueryRunner(DatabaseName.MASK)
                .orElseThrow(() -> new RuntimeException("Not Found QueryRunner DatabaseName: " + DatabaseName.MASK));
    }

}
