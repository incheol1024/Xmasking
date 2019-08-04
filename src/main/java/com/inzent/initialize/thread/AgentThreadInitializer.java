package com.inzent.initialize.thread;

import com.inzent.pool.thread.ExecutorServicePool;
import com.inzent.util.AppProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AgentThreadInitializer implements ThreadInitializer {

    private Logger logger = LoggerFactory.getLogger(AgentThreadInitializer.class);

    private final Properties properties = AppProperty.getProperties();

    private final ExecutorServicePool executorServicePool = ExecutorServicePool.getInstance();

    private static final AgentThreadInitializer agentThreadInitializer = new AgentThreadInitializer();

    private AgentThreadInitializer() {

    }

    public static AgentThreadInitializer getInstance() {
        return agentThreadInitializer;
    }

    @Override
    public void initialize() {

        logger.trace("Initialize AgentThreadInitializer");

        String prop = properties.getProperty("DOWN_AGENT_THREAD");
        int downloadThread = setThreadCount(prop);

        prop = properties.getProperty("REPLACE_AGENT_THREAD");
        int replaceThread = setThreadCount(prop);

        prop = properties.getProperty("LOOKUP_AGENT_THREAD");
        int lookUpThread = setThreadCount(prop);

        for( Action action : Action.values())
            System.out.println(action);

        ExecutorService downExecutorService = Executors.newFixedThreadPool(downloadThread);
        ExecutorService replaceExecutorService = Executors.newFixedThreadPool(replaceThread);

        configureExecutorServicePool(Action.DOWNLOAD, downExecutorService);
        configureExecutorServicePool(Action.REPLACE, replaceExecutorService);

        logger.trace("Complete initializing AgentThreadInitializer");

        printExecutorServices();

    }

    private void printExecutorServices() {
        ExecutorService executorService = null;
        for(Action action : Action.values()) {
           executorService = executorServicePool.getExecutorService(action);
           logger.debug(action.name() + " ExecutorService: {}" , executorService );
        }
    }

    private void configureExecutorServicePool(Action action, ExecutorService executorService) {
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
