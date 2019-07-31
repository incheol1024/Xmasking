package com.inzent.intialize;

import com.inzent.initialize.thread.AgentThreadInitializer;
import com.inzent.initialize.thread.ThreadInitializer;
import com.inzent.pool.thread.ExecutorServicePool;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

public class AgentThreadInitializerTest {


    @Before
    public void setUp() {

        AgentThreadInitializer agentThreadInitializer = AgentThreadInitializer.getInstance();

        agentThreadInitializer.initialize();
    }


    @Test
    public void initializeTest() {

        AgentThreadInitializer agentThreadInitializer = AgentThreadInitializer.getInstance();

        agentThreadInitializer.initialize();
    }

    @Test
    public void runExecutorServiceTest() throws InterruptedException {

        ExecutorServicePool executorServicePool = ExecutorServicePool.getInstance();
        ExecutorService executorService = executorServicePool.getExecutorService(ThreadInitializer.Action.DOWNLOAD);
        for (int j = 0; j < 100; j++) {
            executorService.execute(() -> {
                int i = 0;
                while (i < 10) {
                    try {
                        Thread.sleep(1000);
                        i++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
        Logger logger = LoggerFactory.getLogger(AgentThreadInitializerTest.class);
        ;
        while (true) {
            Thread.sleep(500);
            logger.debug("{}", executorService);
        }
    }
}
