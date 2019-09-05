package com.inzent.agent.scheduler;

import com.inzent.agent.download.DownloadAgentImpl;
import com.inzent.initialize.thread.ThreadInitializer;
import com.inzent.pool.thread.ExecutorServicePool;
import com.inzent.util.AppProperty;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public class DownloadJob implements Job {

    private Properties properties = AppProperty.getProperties();

    private ExecutorService executorService;

    private Logger logger = LoggerFactory.getLogger(DownloadJob.class);

    private String orders = AppProperty.getValue("DOWNLOAD_ORDER");

    @Override
    public void execute(JobExecutionContext context) {

/*
        if (Objects.isNull(executorService))
            executorService = getExecutorService();

        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorService;

        System.out.println("DownloadJob " + threadPoolExecutor.getActiveCount());

        int maxThread = Integer.valueOf(properties.getProperty("DOWN_AGENT_THREAD"));

        if (threadPoolExecutor.getActiveCount() < maxThread && !DownloadAgentImpl.isCollecting())
            executorService.execute(new DownloadAgentImpl("0001"));

*/

    String[] orderArr = orders.split(",");

    for(String order : orderArr) {
        ExecutorService executorService = getExecutorService();
        executorService.execute(new DownloadAgentImpl(order));
    }

    }

    private ExecutorService getExecutorService() {
        ExecutorServicePool executorServicePool = ExecutorServicePool.getInstance();
        return executorServicePool.getExecutorService(ThreadInitializer.Action.DOWNLOAD);
    }
}
