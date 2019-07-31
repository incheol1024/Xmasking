package com.inzent.agent;

import com.inzent.pool.database.DatabaseName;
import com.inzent.pool.database.QueryRunnerPool;
import org.apache.commons.dbutils.QueryRunner;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TargetFinder {

    private static QueryRunnerPool queryRunnerPool = QueryRunnerPool.getIntance();
    private static QueryRunner queryRunner = getQueryRunner();

    public Queue<String> getQueryList() {
// How ??..

        return new ConcurrentLinkedQueue<String>();
    }

    private static QueryRunner getQueryRunner() {
       return  queryRunnerPool.getQueryRunner(DatabaseName.MASK)
               .orElseThrow(() -> new RuntimeException("Not Found QueryRunner DatabaseName: "+ DatabaseName.MASK));
    }

}
