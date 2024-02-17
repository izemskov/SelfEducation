package ru.develgame.springkafka.producer.workers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MessageScheduler {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(fixedDelay = 1000L)
    public void sendMessage() {
        kafkaTemplate.send("izemskov", UUID.randomUUID().toString());
    }
}
