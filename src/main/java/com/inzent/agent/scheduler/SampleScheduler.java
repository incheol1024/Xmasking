package com.inzent.agent.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;
import java.util.Hashtable;
import java.util.Objects;

public class SampleScheduler implements SchedulerAgent{

    SchedulerFactory schedulerFactory = new StdSchedulerFactory();

    Hashtable<String, JobDetail> jobDetails = new Hashtable<>();

    Hashtable<String, Trigger> triggers = new Hashtable<>();

    public Scheduler scheduler = schedulerFactory.getScheduler();

    public SampleScheduler() throws SchedulerException {

    }

    @Override
    public void run() {

        try {
            initialize();
            scheduleJob();
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void initialize() throws SchedulerException {

        try {
            storeJobAndTrigger();
        } catch (ParseException e) {
            new RuntimeException(e);
        }
    }

    private void storeJobAndTrigger() throws SchedulerException, ParseException {

        setUpJob(DownloadTargetCollectorJob.class, DOWNLOAD_TARGET_COLLECTOR_CRON);
        setUpJob(DownloadJob.class, DOWNLOAD_CRON);

        setUpJob(LookUpAllJob.class, LOOKUP_ALL_CRON);
        setUpJob(LookUpJob.class, LOOKUP_CRON);

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

         if(Objects.nonNull(jobDetail) && Objects.nonNull(cronTrigger))
             throw new RuntimeException("Fail to set job schedule : " + jobClass.getName());
    }

    private void scheduleJob() {

        this.jobDetails.keySet().stream()
            .forEach((key) -> {
                JobDetail jobDetail = this.jobDetails.get(key);
                Trigger trigger = this.triggers.get(key);
                try {
                    scheduler.scheduleJob(jobDetail, trigger);
                } catch (SchedulerException e) {
                    e.printStackTrace();
                }
            });
    }



}
