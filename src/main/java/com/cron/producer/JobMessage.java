package com.cron.producer;

public class JobMessage {

    // which job this is (the crontab line number)
    private int jobId;
    // which cluster/topic it belongs to
    private String cluster;
    // the command to run (real commands I'll use later)
    private String command;
    // when the producer decided this job was due (ISO timestamp string)
    private String scheduledTime;

    // Jackson needs a no-arg constructor to serialize/deserialize
    public JobMessage() {}

    public JobMessage(int jobId, String cluster, String command, String scheduledTime) {
        this.jobId = jobId;
        this.cluster = cluster;
        this.command = command;
        this.scheduledTime = scheduledTime;
    }

    public int getJobId() {
        return jobId;
    }
    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getCluster() {
        return cluster;
    }
    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getCommand() {
        return command;
    }
    public void setCommand(String command) {
        this.command = command;
    }

    public String getScheduledTime() {
        return scheduledTime;
    }
    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }


}
