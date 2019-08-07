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
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import static com.inzent.agent.download.DownloadAgent.*;

public class DownTargetCollector {

    private Logger logger = LoggerFactory.getLogger(DownTargetCollector.class);

    private Hashtable<ElementIdRange, ElementEntry> elementEntryHashtable;

    private volatile static boolean isRunning = false;


    private DownTargetCollector() {
        initializeElementEntry();
    }

    private void initializeElementEntry() {

        elementEntryHashtable = new Hashtable<>();
        for (ElementIdRange elementIdRange : ElementIdRange.values()) {
            elementEntryHashtable.put(elementIdRange, new ElementEntry(elementIdRange.getBeginElementId(), elementIdRange.getEndElementId()));
        }
    }

    public synchronized void doWork() {

        if (!ableToWork())
            return;

        QueryRunner queryRunner = CommonUtil.getQueryRunner(DatabaseName.MASK);
        ResultSetHandler<List<String>> resultSetHandler = null;
        List<String> targetDtos = null;

        try {
            for (ElementIdRange elementIdRange : ElementIdRange.values()) {
                resultSetHandler = new ColumnListHandler<>("elementid");
                logger.debug("Down Target Collector sql = {} ", DOWN_TARGET_SQL);
                //select elementid from xtorm.asyscontentelement where elementid > ? and elementid <= ? and rownum <= 1000
                ElementEntry elementEntry = elementEntryHashtable.get(elementIdRange);
                targetDtos = queryRunner.query(DOWN_TARGET_SQL, resultSetHandler, elementEntry.beginElementId, elementEntry.endElementId);

                Collections.sort(targetDtos);
                String lastElementId = "";

                if (targetDtos.isEmpty())
                    logger.warn("There are no query result elementid range is {} - {} ", elementEntry.beginElementId, elementEntry.endElementId);

                lastElementId = targetDtos.get(targetDtos.size() - 1);
                if (downTargetQueue.addAll(targetDtos)) {
                    elementEntryHashtable.put(elementIdRange, new ElementEntry(lastElementId, elementIdRange.getEndElementId()));
                }
                logger.debug("Target Queue size = {}", downTargetQueue.size());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            setIsRunning(false);
        }
    }

    private boolean ableToWork() {
        logger.debug("Down Targe Queue size max size {}, now {} ", DOWN_TARGET_QUEUE_SIZE, downTargetQueue.size());
        return this.isRunning() == false && downTargetQueue.size() < DOWN_TARGET_QUEUE_SIZE;
    }

    public synchronized boolean isRunning() {
        return isRunning;
    }

    private synchronized void setIsRunning(boolean runningStatus) {
        isRunning = runningStatus;
    }

    private synchronized void setElementidQueue() {

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


    public static DownTargetCollector getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final DownTargetCollector INSTANCE = new DownTargetCollector();
    }

    private static class ElementEntry {

        private String beginElementId;

        private String endElementId;

        private ElementEntry(String beginElementId, String endElementId) {
            this.beginElementId = beginElementId;
            this.endElementId = endElementId;
        }

    }
}
