package com.cron.consumer;

public class ConsumerMain {

    public static void main(String[] args) throws Exception{
        String broker = System.getenv().getOrDefault("KAFKA_BROKER", "localhost:9093");
        String topic = System.getenv().getOrDefault("CLUSTER_TOPIC", "cluster-a");

        Consumer consumer = new Consumer(broker, topic);
        consumer.start();
    }
}
