package com.inzent.agent.scheduler;

import com.inzent.agent.Agent;

public interface SchedulerAgent extends Agent {


    String SCHEDULER = properties.getProperty("SCHEDULER");

    String LOOKUP_CRON = properties.getProperty("LOOKUP_CRON");

    String LOOKUP_ALL_CRON = properties.getProperty("LOOKUP_ALL_CRON");

    String DOWNLOAD = properties.getProperty("DOWNLOAD");

    String DOWNLOAD_CRON = properties.getProperty("DOWNLOAD_CRON");

    String DOWNLOAD_TARGET_COLLECTOR = properties.getProperty("DOWNLOAD_TARGET_COLLECTOR");

    String DOWNLOAD_TARGET_COLLECTOR_CRON = properties.getProperty("DOWNLOAD_TARGET_COLLECTOR_CRON");

    String REPLACE = properties.getProperty("REPLACE");

    String REPLACE_CRON = properties.getProperty("REPLACE_CRON");

}
