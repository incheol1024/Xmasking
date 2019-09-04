package com.inzent.dao;

import com.inzent.dto.agent.DownloadResultParamDto;
import com.inzent.dto.agent.ReplaceTargetDto;
import com.inzent.pool.database.DatabaseName;
import com.inzent.pool.database.QueryRunnerPool;
import com.inzent.util.AppProperty;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.security.krb5.internal.APOptions;

import java.sql.SQLException;
import java.util.List;

public final class MaskDao {

    private static QueryRunner queryRunner = QueryRunnerPool.getInstance().getQueryRunnerImmediately(DatabaseName.MASK);

    private static String DOWN_TARGET_SQL = AppProperty.getValue("DOWN_TARGET_SQL");

    private static String DOWN_SUCCESS_SQL = AppProperty.getValue("DOWN_SUCCESS_SQL");

    private static String DOWN_FAIL_SQL = AppProperty.getValue("DOWN_FAIL_SQL");

    private static String DOWN_DATE_SELECT_SQL = AppProperty.getValue("DOWN_DATE_SELECT_SQL");

    private static String DOWN_DATE_SUCCESS_SQL = AppProperty.getValue("DOWN_DATE_SUCCESS_SQL");

    private static ResultSetHandler<List<String>> elementIdListHandler = new ColumnListHandler<>(1);

    private static ResultSetHandler<String> downDateHandler = new ScalarHandler<>(1);

    private static ResultSetHandler<String> replaceDateHandler = new ScalarHandler<>(1);

    private static ResultSetHandler<List<ReplaceTargetDto>> replaceTargetDtoResultSetHandler = new BeanListHandler<>(ReplaceTargetDto.class);

    private static String REPLACE_DATE_SELECT_SQL = AppProperty.getValue("REPLACE_DATE_SELECT_SQL");

    private static String REPLACE_TARGET_SQL = AppProperty.getValue("REPLACE_TARGET_SQL");

    private static String REPLACE_SUCCESS_SQL = AppProperty.getValue("REPLACE_SUCCESS_SQL");

    private static String REPLACE_FAIL_SQL = AppProperty.getValue("REPLACE_FAIL_SQL");

    private static String REPLACE_DATE_SUCCESS_SQL = AppProperty.getValue("REPLACE_DATE_SUCCESS_SQL");

    private static Logger logger = LoggerFactory.getLogger(MaskDao.class);


    public static List<String> getElementIdListOfDownload(String order, String date) {
        List<String> list = null;
        try {
            list = queryRunner.query(DOWN_TARGET_SQL, elementIdListHandler, date, order);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static int updateDownSuccess(DownloadResultParamDto downloadResultParamDto) {
        int result = 0;
        try {
            result = queryRunner.update(DOWN_SUCCESS_SQL, downloadResultParamDto.toParamArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int updateDownFail(DownloadResultParamDto downloadResultParamDto) {
        int result = 0;
        try {
            result = queryRunner.update(DOWN_FAIL_SQL, downloadResultParamDto.getElementId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String findDownDate() {
        String result = "";
        try {
            result = queryRunner.query(DOWN_DATE_SELECT_SQL, downDateHandler);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int updateDownDateSuccess(String order, String date) {
        int result = 0;
        try {
            result = queryRunner.update(DOWN_DATE_SUCCESS_SQL, order, date);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String findReplaceDate() {
        String result = "";
        try {
            result = queryRunner.query(REPLACE_DATE_SELECT_SQL, replaceDateHandler);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<ReplaceTargetDto> getElementIdListOfReplace(String order, String date) {
        List<ReplaceTargetDto> result = null;
        try {
            result = queryRunner.query(REPLACE_TARGET_SQL, replaceTargetDtoResultSetHandler, date, order);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int updateReplaceSuccess(String elementId) {
        int result = 0;
        try {
            queryRunner.update(REPLACE_SUCCESS_SQL, elementId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int updateReplaceFail(String elementId) {
        int result = 0;
        try {
            queryRunner.update(REPLACE_FAIL_SQL, elementId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int updateReplaceDateSuccess(String order, String date) {
        int result = 0;
        try {
            result = queryRunner.update(REPLACE_DATE_SUCCESS_SQL, date, order);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
