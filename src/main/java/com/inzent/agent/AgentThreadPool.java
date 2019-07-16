package com.inzent.agent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AgentThreadPool {


    private static ExecutorService executorService;

    static {
        executorService = Executors.newFixedThreadPool(5);
    }

    static ExecutorService getExecutorService() {
        return executorService;
    }


}
