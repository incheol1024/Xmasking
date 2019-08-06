package com.inzent.agent;

import com.inzent.dic.ElementIdRange;
import com.inzent.pool.database.DatabaseName;
import com.inzent.util.CommonUtil;
import org.apache.commons.dbutils.BaseResultSetHandler;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DownTargetCollector implements DownloadAgent {

    private Logger logger = LoggerFactory.getLogger(DownTargetCollector.class);

    @Override
    public void run() {

        QueryRunner queryRunner = CommonUtil.getQueryRunner(DatabaseName.MASK);
        ResultSetHandler<String> resultSetHandler = null;
        List<String> targetDtos = null;
        boolean addSuccess = false;
        try {
            for (ElementIdRange elementIdRange : ElementIdRange.values()) {

//                resultSetHandler = new ColumnListHandler<String>("elementid");
                logger.debug("Down Target Collector sql = {} ", DOWN_TARGET_SQL);
                targetDtos = queryRunner.execute(DOWN_TARGET_SQL, null, elementIdRange.getElementIdRange());
                logger.debug("{}", targetDtos.get(0));
                addSuccess = downTargetQueue.addAll(targetDtos);
                if (addSuccess == false) {
                    logger.warn("Fail to add down list on Down Target Queue.");
                    delayTime();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void delayTime() {

        int delayTime = 3600000;
        try {
            logger.warn("Delay adding down list on Down Target Queue for " + delayTime + "ms");
            Thread.sleep(delayTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
