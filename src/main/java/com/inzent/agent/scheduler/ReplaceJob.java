package com.inzent.agent.scheduler;

import com.inzent.agent.Replace.ReplaceAgentImpl;
import com.inzent.initialize.thread.ThreadInitializer;
import com.inzent.pool.thread.ExecutorServicePool;
import com.inzent.util.AppProperty;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.concurrent.ExecutorService;

public class ReplaceJob implements Job {

    private String orders = AppProperty.getValue("REPLACE_ORDER");

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String[] orderArr = orders.split(",");

        for (String order : orderArr) {
            ExecutorService executorService = getExecutorService();
            executorService.execute(new ReplaceAgentImpl(order));
        }
    }

    private ExecutorService getExecutorService() {
        ExecutorServicePool executorServicePool = ExecutorServicePool.getInstance();
        return executorServicePool.getExecutorService(ThreadInitializer.Action.REPLACE);
    }

}
