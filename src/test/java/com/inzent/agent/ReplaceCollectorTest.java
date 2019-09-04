package com.inzent.agent;

import com.inzent.Boot;
import com.inzent.agent.Replace.ReplaceAgent;
import com.inzent.agent.Replace.ReplaceTargetCollector;
import org.junit.Before;
import org.junit.Test;

public class ReplaceCollectorTest {

    @Before
    public void setUp() {
        Boot boot = new Boot();
        boot.boot();
    }


    @Test
    public void doWorkTest() throws InterruptedException {

        ReplaceTargetCollector replaceTargetCollector = ReplaceTargetCollector.getInstance();

        replaceTargetCollector.doWork();

        replaceTargetCollector.doWork();

        System.out.println(ReplaceAgent.replaceTargetQueue.size());

//        while(true) {
//
//            Thread.sleep(1000);
//
//            int size = ReplaceAgent.replaceTargetQueue.size();
//
//            System.out.println(size);
//        }
    }
}
