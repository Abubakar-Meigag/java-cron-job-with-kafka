package com.cron.modules;

import com.cron.producer.JobMessage;
import com.cron.producer.KafkaMessageProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.Instant;
import java.util.UUID;

public class PrintJob implements Job {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        try {
            // read what was stashed when the job was scheduled
            int lineNumber = context.getJobDetail().getJobDataMap().getInt("lineNumber");
            String cluster = context.getJobDetail().getJobDataMap().getString("cluster");
            // get the shared producer from the scheduler context
            KafkaMessageProducer producer = (KafkaMessageProducer) context.getScheduler().getContext().get("producer");
            // build the message
            JobMessage msg = new JobMessage(
                    lineNumber,
                    cluster,
                    "echo job " + lineNumber,
                    Instant.now().toString()
            );

            // convert to JSON
            String json = mapper.writeValueAsString(msg);
            // random UUID as the key — spreads messages across partitions
            String key = UUID.randomUUID().toString();
            // send to the cluster's topic
            producer.send(cluster, key, json);

        } catch (Exception e) {
            throw new JobExecutionException(e);
        }


    }

}

