package com.cron;

import com.cron.modules.CronJob;
import com.cron.modules.CrontabParser;
import com.cron.modules.PrintJob;
import com.cron.producer.KafkaMessageProducer;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Usage: provide a crontab file path, e.g. crontab.txt");
            return;   // stop cleanly
        }

        Path file = Path.of(args[0]);
        CrontabParser parser = new CrontabParser();
        List<CronJob> jobs = parser.parse(file);

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();

        String broker = System.getenv().getOrDefault("KAFKA_BROKER", "localhost:9093");
        KafkaMessageProducer producer = new KafkaMessageProducer(broker);
        scheduler.getContext().put("producer", producer);

        for (CronJob job : jobs) {
            JobDetail detail = JobBuilder.newJob(PrintJob.class)
                    .withIdentity("job-" + job.getLineNumber())
                    .usingJobData("lineNumber", job.getLineNumber())
                    .usingJobData("cluster", job.getCluster())
                    .usingJobData("cronExpression", job.getCronExpression())
                    .build();

            String[] parts = job.getCronExpression().trim().split("\\s+");
            if (parts[4].equals("*")) {
                parts[4] = "?";
            }
            String quartzExpr = "0 " + String.join(" ", parts);

            Trigger trigger = TriggerBuilder.newTrigger()
                            .withIdentity("trigger-" + job.getLineNumber())
                            .withSchedule(CronScheduleBuilder.cronSchedule(quartzExpr))
                            .build();

            scheduler.scheduleJob(detail, trigger);
        }

        System.out.println("Scheduler started with " + jobs.size() + " jobs. Waiting...");
    }
}
