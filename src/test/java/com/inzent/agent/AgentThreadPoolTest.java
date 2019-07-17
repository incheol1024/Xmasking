package com.inzent.agent;

import com.inzent.util.XtormUtil;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.function.Predicate;

public class AgentThreadPoolTest {


    @Test
    public void notNullExecutors() {
        ExecutorService executorService = AgentThreadPool.getExecutorService();
        Predicate<ExecutorService> executorServicePredicate = executor -> executor != null;
        Assertions.assertThat(executorServicePredicate).accepts(executorService);
    }

    @Test
    public void isThreadSafeForExecutorTest() {

        ExecutorService executorService = AgentThreadPool.getExecutorService();
        executorService.execute(() -> {
            XtormUtil.downloadElement("2019071607125100", "D:\\app\\xtorm-test\\download\\2019071607125100");
        });

//        executorService.execute(() -> {
//            int max = 100;
//                while (max >= 0) {
//                    System.out.println(Thread.currentThread().getName());
//                    XtormUtil.downloadElement("2019071605594800", "D:\\app\\xtorm-test\\download\\2019071605594800");
//                    --max;
//                }
//        });
    }

    @Test
    public void getProcessNumber() {


        ExecutorService executorService = AgentThreadPool.getExecutorService();

        executorService.execute(() -> {
            for (int i = 0; i < 10000; i++)
                System.out.println(i);
        });

        for (int i = 0; i < 10000; i++) {
            int num = Runtime.getRuntime().availableProcessors();
            System.out.println(num);
        }
    }

}
