package com.inzent.initialize.thread;

import com.inzent.pool.thread.ExecutorServicePool;
import com.inzent.util.AppProperty;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AgentThreadInitializer implements ThreadInitializer {


    private final Properties properties = AppProperty.getProperties();

    private final ExecutorServicePool executorServicePool = ExecutorServicePool.getInstance();

    @Override
    public void initialize() {

        String prop = properties.getProperty("DOWN_AGENT_THREAD");
        int downloadThread = setThreadCount(prop);

        prop = properties.getProperty("REPLACE_AGENT_THREAD");
        int replaceThread = setThreadCount(prop);

        ExecutorService downExecutorService = Executors.newFixedThreadPool(downloadThread);
        ExecutorService replaceExecutorService = Executors.newFixedThreadPool(replaceThread);

        configureExecutorServicePool(Action.DOWNLOAD, downExecutorService);
        configureExecutorServicePool(Action.REPLACE, replaceExecutorService);
    }

    private void configureExecutorServicePool(Action action,ExecutorService executorService) {
        executorServicePool.putExecutorService(action, executorService);
    }

    private int setThreadCount(String propThread) {
        int threadCount = 0;
        if (propThread == null || propThread.equals(""))
            threadCount = ThreadInitializer.DEFAULT_THRED_COUNT;
        else
            threadCount = Integer.valueOf(properties.getProperty("DOWN_AGENT_THREAD"));

        return threadCount;
    }


}
