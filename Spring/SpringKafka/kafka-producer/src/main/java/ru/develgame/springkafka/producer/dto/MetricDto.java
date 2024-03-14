package ru.develgame.springkafka.producer.dto;

import java.util.Date;

public record MetricDto(Date currentDate, int cpuUsage) {
}
