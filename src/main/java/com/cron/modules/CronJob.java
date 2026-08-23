package com.cron.modules;

public class CronJob {

    private final int lineNumber;
    private final String cronExpression;
    private final String cluster;

    public CronJob(int lineNumber, String cluster, String cronExpression) {
        this.lineNumber = lineNumber;
        this.cluster = cluster;
        this.cronExpression = cronExpression;
    }


    public int getLineNumber() {
        return lineNumber;
    }

    public String getCluster() {
        return cluster;
    }

    public String getCronExpression() {
        return cronExpression;
    }
}
