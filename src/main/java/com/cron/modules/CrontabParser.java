package com.cron.modules;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CrontabParser {

    public List<CronJob> parse(Path file) throws IOException {
        List<String> lines = Files.readAllLines(file);
        List<CronJob> jobs = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if(!line.trim().isEmpty()) {
                String[] parts = line.trim().split("\\s+");
                String cluster = parts[0];
                String cronExpression = String.join(" ",
                        java.util.Arrays.copyOfRange(parts, 1, 6 ));
                jobs.add(new CronJob(i, cluster, cronExpression));
            }
        }
        return jobs;
    }
}
