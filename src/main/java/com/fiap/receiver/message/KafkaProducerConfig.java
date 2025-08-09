package com.fiap.receiver.message;

import com.fiap.receiver.dto.request.PedidoRequestDTO;
import com.fiap.receiver.dto.request.PedidoReceiverRequestDTO;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ProducerFactory<String, PedidoRequestDTO> pedidoRequestDtoProducerFactory() {
        Map<String, Object> configProps = commonConfig();
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, PedidoRequestDTO> pedidoRequestDtoKafkaTemplate() {
        return new KafkaTemplate<>(pedidoRequestDtoProducerFactory());
    }

    @Bean
    public ProducerFactory<String, PedidoReceiverRequestDTO> pedidoReceiverRequestDtoProducerFactory() {
        Map<String, Object> configProps = commonConfig();
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, PedidoReceiverRequestDTO> pedidoReceiverRequestDtoKafkaTemplate() {
        return new KafkaTemplate<>(pedidoReceiverRequestDtoProducerFactory());
    }

    private Map<String, Object> commonConfig() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(ProducerConfig.ACKS_CONFIG, "all");
        configProps.put(ProducerConfig.RETRIES_CONFIG, 10);
        configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        configProps.put(ProducerConfig.LINGER_MS_CONFIG, 5);
        configProps.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        configProps.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, 1048576);
        return configProps;
    }
}