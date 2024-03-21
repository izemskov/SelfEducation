package ru.develgame.springkafka.consumer.listeners;

import org.apache.kafka.clients.consumer.ConsumerRecord;
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
    public void listenGroupId(ConsumerRecord<String, MetricDto> record) {
        System.out.println(record.offset()
                + " " + dateFormat.format(record.value().currentDate())
                + " CPU usage: " + record.value().cpuUsage());
    }
}
