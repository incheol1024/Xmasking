package com.inzent.agent.scheduler;

import com.inzent.util.Stoper;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StopJob implements Job {

    private static Logger logger = LoggerFactory.getLogger(StopJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Stoper.stop(); // 전체 스레드 종료 신호
        while(true) {
            try {
                Thread.sleep(10000);
                if(Stoper.isAllStop()) {
                  break;
                }
                logger.debug("Waiting for stopping all thread.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        logger.debug("p");
        System.exit(0);
    }
}
