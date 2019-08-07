package com.inzent;

import com.inzent.agent.scheduler.MainSchedulerAgent;
import com.inzent.initialize.database.DataSourcePoolInitializer;
import com.inzent.initialize.database.DatabaseConfigInitializer;
import com.inzent.initialize.database.QueryRunnerInitializer;
import com.inzent.initialize.thread.AgentThreadInitializer;
import com.inzent.initialize.thread.ThreadInitializer;
import com.inzent.pool.thread.ExecutorServicePool;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.ExecutorService;

public class Boot {

    private DatabaseConfigInitializer databaseConfigInitializer = DatabaseConfigInitializer.getInstance();
    private DataSourcePoolInitializer dataSourcePoolInitializer = DataSourcePoolInitializer.getInstance();
    private QueryRunnerInitializer queryRunnerInitializer = QueryRunnerInitializer.getIntance();

    private AgentThreadInitializer agentThreadInitializer = AgentThreadInitializer.getInstance();

    Logger logger = LoggerFactory.getLogger(Boot.class);

    public void boot() {

        try {
        logger.debug("BOOT START" );
        checkNull();

        databaseConfigInitializer.initialize();
        dataSourcePoolInitializer.initialize();
        queryRunnerInitializer.initialize();
        agentThreadInitializer.initialize();

//        ExecutorService executorService = Executors.newFixedThreadPool(4);
//        executorService.execute(new DownTargetCollector());

        ExecutorServicePool executorServicePool = ExecutorServicePool.getInstance();
        ExecutorService executorService = executorServicePool.getExecutorService(ThreadInitializer.Action.SCHEDULER);
            executorService.execute(new MainSchedulerAgent());
        } catch (SchedulerException e) {
            throw new RuntimeException("Boot Fail. ", e);
        }

    }

    private void checkNull() {

        if(Objects.isNull(databaseConfigInitializer))
            throw new NullPointerException(DatabaseConfigInitializer.class.getName() + " is null reference");

        if(Objects.isNull(dataSourcePoolInitializer))
            throw new NullPointerException(DataSourcePoolInitializer.class.getName() + " is null reference");

        if(Objects.isNull(queryRunnerInitializer))
            throw new NullPointerException(QueryRunnerInitializer.class.getName() + " is null reference");

        if(Objects.isNull(agentThreadInitializer))
            throw new NullPointerException(AgentThreadInitializer.class.getName() + " is null reference");

    }



}
