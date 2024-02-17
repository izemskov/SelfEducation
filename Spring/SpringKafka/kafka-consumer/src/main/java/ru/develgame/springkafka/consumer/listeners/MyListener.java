package ru.develgame.springkafka.consumer.listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MyListener {
    @KafkaListener(topics = "izemskov", groupId = "groupId")
    public void listenGroupId(String message) {
        System.out.println("Received Message in group groupId: " + message);
    }
}
