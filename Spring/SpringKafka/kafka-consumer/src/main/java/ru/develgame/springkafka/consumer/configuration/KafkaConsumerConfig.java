package ru.develgame.springkafka.consumer.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.JacksonUtils;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.develgame.springkafka.consumer.dto.MetricDto;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.kafka.support.serializer.JsonDeserializer.TYPE_MAPPINGS;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public ObjectMapper objectMapper() {
        return JacksonUtils.enhancedObjectMapper();
    }

    @Bean
    public ConsumerFactory<String, MetricDto> consumerFactory(ObjectMapper objectMapper) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "groupId");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(TYPE_MAPPINGS, "ru.develgame.springkafka.producer.dto.MetricDto:ru.develgame.springkafka.consumer.dto.MetricDto");

        DefaultKafkaConsumerFactory<String, MetricDto> kafkaConsumerFactory = new DefaultKafkaConsumerFactory<>(props);
        kafkaConsumerFactory.setValueDeserializer(new JsonDeserializer<>(objectMapper));
        return kafkaConsumerFactory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MetricDto> kafkaListenerContainerFactory(ObjectMapper objectMapper) {
        ConcurrentKafkaListenerContainerFactory<String, MetricDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(objectMapper));
        return factory;
    }
}
