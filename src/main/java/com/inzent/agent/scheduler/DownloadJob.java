package com.inzent.agent.scheduler;

import com.inzent.agent.download.DownloadAgentImpl;
import com.inzent.initialize.thread.ThreadInitializer;
import com.inzent.pool.thread.ExecutorServicePool;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Objects;
import java.util.concurrent.ExecutorService;

public class DownloadJob implements Job {

    ExecutorService executorService;


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        if(Objects.isNull(executorService))
            executorService = getExecutorService();

//        for(executorService.)
        executorService.execute(new DownloadAgentImpl());

    }

    private ExecutorService getExecutorService() {
        ExecutorServicePool executorServicePool = ExecutorServicePool.getInstance();
        return executorServicePool.getExecutorService(ThreadInitializer.Action.DOWNLOAD);
    }
}
