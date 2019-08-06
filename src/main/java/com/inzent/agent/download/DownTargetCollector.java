package com.inzent.agent.download;

import com.inzent.dic.ElementIdRange;
import com.inzent.pool.database.DatabaseName;
import com.inzent.util.CommonUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Observable;

public class DownTargetCollector extends Observable implements DownloadAgent {

    private Logger logger = LoggerFactory.getLogger(DownTargetCollector.class);

    @Override
    public void run() {

        QueryRunner queryRunner = CommonUtil.getQueryRunner(DatabaseName.MASK);
        ResultSetHandler<List<String>> resultSetHandler = null;
        List<String> targetDtos = null;
        boolean addSuccess = false;

        if(downTargetQueue.size() >= DOWN_TARGET_QUEUE_SIZE) {
            logger.warn("Down Targe Queue size over max size {}, now {} ", DOWN_TARGET_QUEUE_SIZE, downTargetQueue.size() );
            setChanged();

            //notifiy Change Observer

            //            delayTime();
        }

        try {
            for (ElementIdRange elementIdRange : ElementIdRange.values()) {

                resultSetHandler = new ColumnListHandler<>("elementid");
                logger.debug("Down Target Collector sql = {} ", DOWN_TARGET_SQL);
                targetDtos = queryRunner.query(DOWN_TARGET_SQL, resultSetHandler, elementIdRange.getElementIdRange());

                if (targetDtos.size() > 0 && downTargetQueue.size() >= DOWN_TARGET_QUEUE_SIZE)
                    addSuccess = downTargetQueue.addAll(targetDtos);

                logger.debug("Target Queue size = {}", downTargetQueue.size());
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
