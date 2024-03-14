package ru.develgame.springkafka.consumer.listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.develgame.springkafka.consumer.dto.MetricDto;

import java.text.SimpleDateFormat;

@Component
public class MyListener {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    @KafkaListener(topics = "metricsTopic",
            containerFactory = "kafkaListenerContainerFactory",
            groupId = "groupId")
    public void listenGroupId(MetricDto metricDto) {
        System.out.println(dateFormat.format(metricDto.currentDate()) + " CPU usage: " + metricDto.cpuUsage());
    }
}
