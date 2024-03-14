package ru.develgame.springkafka.consumer.dto;

import java.util.Date;

public record MetricDto(Date currentDate, int cpuUsage) {
}
