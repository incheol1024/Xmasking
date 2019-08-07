package com.inzent.agent.scheduler;

import com.inzent.agent.download.DownTargetCollector;
import com.inzent.initialize.thread.ThreadInitializer;
import com.inzent.pool.thread.ExecutorServicePool;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public class DownloadTargetCollectorJob implements Job {

    private ThreadPoolExecutor executorService;

    private Logger logger = LoggerFactory.getLogger(DownloadTargetCollectorJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

/*

        if(Objects.isNull(executorService))
            executorService = (ThreadPoolExecutor)getExecutorService();

        logger.debug(this.getClass().getName() + " is start {} current active thread {}", executorService.getActiveCount());
        executorService.execute(DownTargetCollector.);
*/

        DownTargetCollector downTargetCollector = DownTargetCollector.getInstance();
        if (downTargetCollector.isRunning()) {
            logger.debug("DownTargetCollector is running");
            return;
        }
        downTargetCollector.doWork();
    }

    private ExecutorService getExecutorService() {
        ExecutorServicePool executorServicePool = ExecutorServicePool.getInstance();
        return executorServicePool.getExecutorService(ThreadInitializer.Action.DOWNLOAD);
    }
}

