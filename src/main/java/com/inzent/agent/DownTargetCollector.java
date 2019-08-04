package com.inzent.agent;

import com.inzent.dic.ElementIdRange;
import com.inzent.dto.mask.DownTargetDto;
import com.inzent.pool.database.DatabaseName;
import com.inzent.util.CommonUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class DownTargetCollector implements DownloadAgent {

    private Logger logger = LoggerFactory.getLogger(DownTargetCollector.class);

    @Override
    public void run() {

        QueryRunner queryRunner = CommonUtil.getQueryRunner(DatabaseName.MASK);
        ResultSetHandler<DownTargetDto> resultSetHandler = null;
        List<DownTargetDto> targetDtos = null;
        boolean addSuccess = false;
        try {

            for (ElementIdRange elementIdRange : ElementIdRange.values()) {
                resultSetHandler = new BeanListHandler(DownTargetDto.class);
                targetDtos = queryRunner.execute(downTargetSql, resultSetHandler, elementIdRange.getElementIdRange());
                addSuccess = downTargetQueue.addAll(targetDtos);
                if(addSuccess == false) {
                    logger.warn("Fail to add down list on Down Target Queue.");
                    delayTime();
                }

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void delayTime() {

        try {
            Thread.sleep(3600000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
