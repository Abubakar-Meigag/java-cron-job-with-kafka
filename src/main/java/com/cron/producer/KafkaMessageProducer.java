package com.cron.producer;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaMessageProducer {

    private final Producer<String, String> producer;

    public KafkaMessageProducer(String broker){
        Properties props = new Properties();
        // where the broker is (e.g. kafka:9092)
        props.put("bootstrap.servers", broker);
        // how to turn the key and value into bytes for the wire
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());
        this.producer = new KafkaProducer<>(props);
    }

    // send one message: which topic, a key (for partitioning), the JSON body
    public void send(String topic, String key, String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, message);
        producer.send(record);
        System.out.println("Produced to " + topic + " [key=" + key + "]: " + message);
    }

    // flush any buffered messages and close the connection cleanly
    public void close() {
        producer.close();
    }


}
