package com.cron.modules;

public class CronJob {

    private int lineNumber;
    private String cronExpression;

    public CronJob(int lineNumber, String cronExpression) {
        this.lineNumber = lineNumber;
        this.cronExpression = cronExpression;
    }


    public int getLineNumber() {
        return lineNumber;
    }

    public String getCronExpression() {
        return cronExpression;
    }
}
