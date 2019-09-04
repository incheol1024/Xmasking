package com.inzent.agent;

import com.inzent.Boot;
import com.inzent.agent.download.DownloadAgent;
import com.inzent.agent.download.DownloadAgentImpl;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadAgentImplTest {

    @Test
    public void runTestForTargetQueueIsZero() {

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Runnable runnable = new DownloadAgentImpl("0001");
        executorService.execute(runnable);
        executorService.execute(runnable);
        executorService.execute(runnable);
        executorService.execute(runnable);
    }

    @Test
    public void runTestForTargetQueueIsExist() throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Boot boot = new Boot();
        boot.boot();


        ExecutorService executorService = Executors.newFixedThreadPool(4);
//        executorService.execute(new DownTargetCollector());



    }
}
