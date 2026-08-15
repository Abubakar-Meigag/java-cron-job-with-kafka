package com.cron.modules;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class PrintJob implements Job {

    @Override
    public void execute(JobExecutionContext context) {
        int lineNumber = context.getJobDetail().getJobDataMap().getInt("lineNumber");
        System.out.println("Running job " + lineNumber);
    }
}

