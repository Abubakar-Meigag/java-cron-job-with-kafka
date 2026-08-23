package com.cron.consumer;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class Consumer {

    private final KafkaConsumer<String, String> consumer;

    public Consumer(String broker, String topic) {
        Properties props = new Properties();
        props.put("bootstrap.servers", broker);
        props.put("group.id", "runners");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());

        this.consumer = new KafkaConsumer<>(props);
        consumer.subscribe(List.of(topic));
    }

    public void start(){
        while (true) {
            var records = consumer.poll(Duration.ofSeconds(1));
            for (var record : records) {
                System.out.println("Received on " + record.topic() + ": " + record.value());
            }
        }
    }

    public void close() {
        consumer.close();
    }

}
