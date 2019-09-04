package com.inzent.agent.Replace;

import com.inzent.dic.ElementIdRange;
import com.inzent.dto.agent.ReplaceTargetDto;
import com.inzent.pool.database.DatabaseName;
import com.inzent.util.CommonUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;

import static com.inzent.agent.Replace.ReplaceAgent.replaceTargetQueue;

public class ReplaceTargetCollector {

    private Logger logger = LoggerFactory.getLogger(ReplaceTargetCollector.class);

    private Hashtable<ElementIdRange, ElementEntry> elementEntryHashtable;

    private volatile static boolean isRunning = false;

    private ReplaceTargetCollector() {
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
        ResultSetHandler<List<ReplaceTargetDto>> resultSetHandler = null;
        List<ReplaceTargetDto> targetDtos = null;

        try {
            for (ElementIdRange elementIdRange : ElementIdRange.values()) {
                resultSetHandler = new BeanListHandler<>(ReplaceTargetDto.class);

//              select /*+ index_desc(ezs_xif_tbl M_SYS_ID_IDX) */ m_sys_id, from xtorm.Ezs_XIF_TBL where if_stat = '22' and m_sys_id between ? and ?

                ElementEntry elementEntry = elementEntryHashtable.get(elementIdRange);
                targetDtos = queryRunner.query(ReplaceAgent.REPLACE_TARGET_SQL, resultSetHandler, elementIdRange.getBeginElementId(), elementEntry.endElementId);

                if (targetDtos.isEmpty()) {
                    logger.warn("There are no query result elementid range is {} - {} ", elementEntry.beginElementId, elementEntry.endElementId);
                    continue;
                }

                if (targetDtos.size() > 0) {
                    replaceTargetQueue.addAll(targetDtos);
                    String lastElementId = "";
                    lastElementId = targetDtos.get(targetDtos.size() - 1).getM_sys_id();
                    elementEntryHashtable.put(elementIdRange, new ElementEntry(elementIdRange.getBeginElementId(), lastElementId));
                }
                logger.debug("{}", elementEntryHashtable.get(elementIdRange));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            setIsRunning(false);
        }
    }

    private boolean ableToWork() {
        logger.debug("Replace Target Queue size max size {}, now {} ", 10000, replaceTargetQueue.size());
        return this.isRunning() == false && replaceTargetQueue.size() < 10000;
    }

    public synchronized boolean isRunning() {
        return isRunning;
    }

    private synchronized void setIsRunning(boolean runningStatus) {
        isRunning = runningStatus;
    }

    private synchronized void setElementidQueue() {

    }

    public static ReplaceTargetCollector getInstance() {
        return ReplaceTargetCollector.LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final ReplaceTargetCollector INSTANCE = new ReplaceTargetCollector();
    }

    private static class ElementEntry {

        private String beginElementId;

        private String endElementId;

        private ElementEntry(String beginElementId, String endElementId) {
            this.beginElementId = beginElementId;
            this.endElementId = endElementId;
        }


        @Override
        public String toString() {
            return "ElementEntry{" +
                    "beginElementId='" + beginElementId + '\'' +
                    ", endElementId='" + endElementId + '\'' +
                    '}';
        }
    }


}
