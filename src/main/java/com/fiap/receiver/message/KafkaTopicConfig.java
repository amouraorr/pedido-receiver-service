package com.fiap.receiver.message;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.topic.novo-pedido:novo-pedido}")
    private String novoPedidoTopic;

    @Bean
    public NewTopic novoPedidoTopic() {
        return TopicBuilder.name(novoPedidoTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }
}