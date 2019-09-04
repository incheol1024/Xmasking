package com.inzent.agent.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Hashtable;
import java.util.Objects;

public class MainSchedulerAgent implements SchedulerAgent {

    private SchedulerFactory schedulerFactory = new StdSchedulerFactory();

    private Hashtable<String, JobDetail> jobDetails = new Hashtable<>();

    private Hashtable<String, Trigger> triggers = new Hashtable<>();

    public Scheduler scheduler = schedulerFactory.getScheduler();

    private Logger logger = LoggerFactory.getLogger(MainSchedulerAgent.class);

    public MainSchedulerAgent() throws SchedulerException {

    }

    @Override
    public void run() {

        if(!SCHEDULER.equalsIgnoreCase(Boolean.TRUE.toString())) {
            System.exit(0);
            return;
        }

        try {

            logger.debug("Initialize MainSheduler.");

            initialize();
            scheduleJob();
            scheduler.start();
            
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {

        try {
            storeJobAndTrigger();
        } catch (ParseException e) {
            new RuntimeException(e);
        }
    }

    private void storeJobAndTrigger() throws ParseException {

        String trueString = Boolean.TRUE.toString();

//        if (DOWNLOAD_TARGET_COLLECTOR.equalsIgnoreCase(trueString))
//            setUpJob(DownloadTargetCollectorJob.class, DOWNLOAD_TARGET_COLLECTOR_CRON);

        if (DOWNLOAD.equalsIgnoreCase(trueString))
            setUpJob(DownloadJob.class, DOWNLOAD_CRON);

        if (LOOKUP_ALL.equalsIgnoreCase(trueString))
            setUpJob(LookUpAllJob.class, LOOKUP_ALL_CRON);

        if (LOOKUP.equalsIgnoreCase(trueString))
            setUpJob(LookUpJob.class, LOOKUP_CRON);

        if (REPLACE.equalsIgnoreCase(trueString))
            setUpJob(ReplaceJob.class, REPLACE_CRON);
    }

    private <T extends Job> void setUpJob(Class<T> jobClass, String cronExpression) throws ParseException {
        String identity = jobClass.getName();

        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(identity, Scheduler.DEFAULT_GROUP)
                .build();

        Trigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity(identity, Scheduler.DEFAULT_GROUP)
                .withSchedule(CronScheduleBuilder.cronSchedule(new CronExpression(cronExpression)))
                .build();

        jobDetail = jobDetails.put(identity, jobDetail);
        cronTrigger = triggers.put(identity, cronTrigger);

        if (Objects.nonNull(jobDetail) && Objects.nonNull(cronTrigger))
            throw new RuntimeException("Fail to set job schedule : " + jobClass.getName());
    }

    private void scheduleJob() {

        this.jobDetails.keySet().stream()
                .forEach((key) -> {
                    JobDetail jobDetail = this.jobDetails.get(key);
                    Trigger trigger = this.triggers.get(key);
                    try {
                        printScheduleJobs(jobDetail, trigger);
                        scheduler.scheduleJob(jobDetail, trigger);
                    } catch (SchedulerException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void printScheduleJobs(JobDetail jobDetail, Trigger trigger) {
        logger.debug("Scheduling jobDetail {}, trigger {}",jobDetail.getJobClass().getName(), ((CronTrigger)trigger).getCronExpression() );
    }


}
