package com.cron.modules;

public class CronJob {

    private final int lineNumber;
    private final String cronExpression;

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
