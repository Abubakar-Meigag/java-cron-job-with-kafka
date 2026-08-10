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
            if(!line.isEmpty()) {
                jobs.add(new CronJob(i, line));
            }
        }
        return jobs;
    }
}
