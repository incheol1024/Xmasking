package com.inzent.pool.thread;

import com.inzent.initialize.thread.ThreadInitializer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

public class ExecutorServicePool {

    private static ExecutorServicePool executorServicePool = new ExecutorServicePool();

    private static Map<ThreadInitializer.Action, ExecutorService> executorServiceMap = new ConcurrentHashMap<>();

    private ExecutorServicePool() {
    }

    public static ExecutorServicePool getInstance() {
        return executorServicePool;
    }

    public void putExecutorService(ThreadInitializer.Action key, ExecutorService value) {
        executorServiceMap.put(key, value);
    }

    public ExecutorService getExecutorService(ThreadInitializer.Action key) {
        return executorServiceMap.get(key);
    }

}
