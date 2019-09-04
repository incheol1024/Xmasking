/*
package com.inzent.agent.scheduler;

import com.inzent.agent.download.DownTargetCollector;
import com.inzent.agent.download.DownloadAgent;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DownloadTargetCollectorJob implements Job {

    private Logger logger = LoggerFactory.getLogger(DownloadTargetCollectorJob.class);

    @Override
    public void execute(JobExecutionContext context) {

        DownTargetCollector downTargetCollector = DownTargetCollector.getInstance();

        if (downTargetCollector.isRunning()) {
            logger.debug("DownTargetCollector is running");
            return;
        }

        if (DownloadAgent.downTargetQueue.size() == 0)
            downTargetCollector.doWork();
    }


    public void execute() {

        DownTargetCollector downTargetCollector = DownTargetCollector.getInstance();

        if (downTargetCollector.isRunning()) {
            logger.debug("DownTargetCollector is running");
            return;
        }

        if (DownloadAgent.downTargetQueue.size() == 0)
            downTargetCollector.doWork();
    }
}

*/
