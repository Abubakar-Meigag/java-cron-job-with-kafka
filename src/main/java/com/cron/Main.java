package com.cron;

import com.cron.modules.CronJob;
import com.cron.modules.CrontabParser;

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

        for (CronJob j : jobs) {
            System.out.println("Job " + j.getLineNumber() + ": " + j.getCronExpression());
        }
    }
}
