package com.inzent.agent.scheduler;

import com.inzent.agent.download.DownloadAgentImpl;
import com.inzent.initialize.thread.ThreadInitializer;
import com.inzent.pool.thread.ExecutorServicePool;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public class DownloadJob implements Job {


    private ExecutorService executorService;

    private Logger logger = LoggerFactory.getLogger(DownloadJob.class);

    @Override
    public void execute(JobExecutionContext context) {

        if (Objects.isNull(executorService))
            executorService = getExecutorService();


        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorService;

        System.out.println("DownloadJob " +threadPoolExecutor.getActiveCount());

//        logger.debug("threadPoolExecutor.getActiveCount() {}", threadPoolExecutor.getActiveCount());
        logger.debug("threadPoolExecutor.getActiveCount() {}", executorService.getClass());
        logger.debug(this.getClass().getName() + " is start");


//        for(executorService.)
        executorService.execute(new DownloadAgentImpl());

    }

    private ExecutorService getExecutorService() {
        ExecutorServicePool executorServicePool = ExecutorServicePool.getInstance();
        return executorServicePool.getExecutorService(ThreadInitializer.Action.DOWNLOAD);
    }
}
