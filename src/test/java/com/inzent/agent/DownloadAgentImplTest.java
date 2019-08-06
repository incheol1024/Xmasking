package com.inzent.agent;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadAgentImplTest {

    @Test
    public void runTestForTargetQueueIsZero() {

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Runnable runnable = new DownloadAgentImpl();
        executorService.execute(runnable);
        executorService.execute(runnable);
        executorService.execute(runnable);
        executorService.execute(runnable);
    }

    @Test
    public void runTestForTargetQueueIsExist() {

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        executorService.execute(new DownTargetCollector());

        System.out.println(DownloadAgent.downTargetQueue.size());

        Runnable runnable = new DownloadAgentImpl();
        executorService.execute(runnable);

    }
}
