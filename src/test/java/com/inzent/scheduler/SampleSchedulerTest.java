package com.inzent.scheduler;

import com.inzent.agent.scheduler.SampleScheduler;
import org.junit.Before;
import org.junit.Test;
import org.quartz.SchedulerException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SampleSchedulerTest {


    private CountDownLatch lock;

    @Before
    public void setUp() {
        lock = new CountDownLatch(1);
    }

    @Test
    public void schedulerTest() throws SchedulerException, InterruptedException {

        SampleScheduler sampleScheduler = new SampleScheduler();

        sampleScheduler.initialize();

        lock.await(5, TimeUnit.MINUTES);


    }
}
