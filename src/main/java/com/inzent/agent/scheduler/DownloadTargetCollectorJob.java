package com.inzent.agent.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DownloadTargetCollectorJob implements Job {

    private Logger logger = LoggerFactory.getLogger(DownloadTargetCollectorJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        System.out.println("DownloadTargetCollectorJob Test");
        logger.debug("DownloadTargetCollectorJob Test");

    }
}

