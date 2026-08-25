package com.cron.consumer;

import com.cron.producer.JobMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class Consumer {

    private final KafkaConsumer<String, String> consumer;
    private final ObjectMapper mapper = new ObjectMapper();

    public Consumer(String broker, String topic) {
        Properties props = new Properties();
        props.put("bootstrap.servers", broker);
        props.put("group.id", "runners");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());

        this.consumer = new KafkaConsumer<>(props);
        consumer.subscribe(List.of(topic));
    }

    public void start() throws IOException, InterruptedException {

        while (true) {
            var records = consumer.poll(Duration.ofSeconds(1));

            for (var record : records) {
                if (record.value() == null) {
                    System.out.println("Skipping null message on " + record.topic());
                    continue;
                }
                try {

                JobMessage job = mapper.readValue(record.value(), JobMessage.class);
                System.out.println("Received on " + job.getJobId() + "for cluster " + job.getCluster());
                System.out.println("Running command " + job.getCommand());

                ProcessBuilder pb = new ProcessBuilder("sh", "-c", job.getCommand());
                pb.inheritIO();
                Process process = pb.start();
                process.waitFor();
                } catch (Exception e) {
                    System.out.println("Failed to process message: " + e.getMessage());
                }
            }
        }

    }

    public void close() {
        consumer.close();
    }

}
