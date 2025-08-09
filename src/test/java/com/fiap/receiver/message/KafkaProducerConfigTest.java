package com.fiap.receiver.message;

import com.fiap.receiver.dto.request.PedidoRequestDTO;
import com.fiap.receiver.dto.request.PedidoReceiverRequestDTO;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Method;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class KafkaProducerConfigTest {

    @InjectMocks
    private KafkaProducerConfig kafkaProducerConfig;

    private static final String BOOTSTRAP_SERVERS = "localhost:9092";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(kafkaProducerConfig, "bootstrapServers", BOOTSTRAP_SERVERS);
    }

    @Test
    void deveInstanciarKafkaProducerConfigCorretamente() {
        assertNotNull(kafkaProducerConfig);
    }

    @Test
    void deveCriarPedidoRequestDtoProducerFactory() {
        ProducerFactory<String, PedidoRequestDTO> producerFactory = kafkaProducerConfig.pedidoRequestDtoProducerFactory();

        assertNotNull(producerFactory);
        assertInstanceOf(DefaultKafkaProducerFactory.class, producerFactory);
    }

    @Test
    void deveCriarPedidoRequestDtoKafkaTemplate() {
        KafkaTemplate<String, PedidoRequestDTO> kafkaTemplate = kafkaProducerConfig.pedidoRequestDtoKafkaTemplate();

        assertNotNull(kafkaTemplate);
        assertNotNull(kafkaTemplate.getProducerFactory());
    }

    @Test
    void deveCriarPedidoReceiverRequestDtoProducerFactory() {
        ProducerFactory<String, PedidoReceiverRequestDTO> producerFactory = kafkaProducerConfig.pedidoReceiverRequestDtoProducerFactory();

        assertNotNull(producerFactory);
        assertInstanceOf(DefaultKafkaProducerFactory.class, producerFactory);
    }

    @Test
    void deveCriarPedidoReceiverRequestDtoKafkaTemplate() {
        KafkaTemplate<String, PedidoReceiverRequestDTO> kafkaTemplate = kafkaProducerConfig.pedidoReceiverRequestDtoKafkaTemplate();

        assertNotNull(kafkaTemplate);
        assertNotNull(kafkaTemplate.getProducerFactory());
    }

    @Test
    void deveConfigurarBootstrapServersCorretamente() throws Exception {
        Method commonConfigMethod = KafkaProducerConfig.class.getDeclaredMethod("commonConfig");
        commonConfigMethod.setAccessible(true);

        @SuppressWarnings("unchecked")
        Map<String, Object> config = (Map<String, Object>) commonConfigMethod.invoke(kafkaProducerConfig);

        assertEquals(BOOTSTRAP_SERVERS, config.get(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG));
    }

    @Test
    void deveConfigurarKeySerializerCorretamente() throws Exception {
        Method commonConfigMethod = KafkaProducerConfig.class.getDeclaredMethod("commonConfig");
        commonConfigMethod.setAccessible(true);

        @SuppressWarnings("unchecked")
        Map<String, Object> config = (Map<String, Object>) commonConfigMethod.invoke(kafkaProducerConfig);

        assertEquals(StringSerializer.class, config.get(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG));
    }

    @Test
    void deveConfigurarValueSerializerCorretamente() throws Exception {
        Method commonConfigMethod = KafkaProducerConfig.class.getDeclaredMethod("commonConfig");
        commonConfigMethod.setAccessible(true);

        @SuppressWarnings("unchecked")
        Map<String, Object> config = (Map<String, Object>) commonConfigMethod.invoke(kafkaProducerConfig);

        assertEquals(JsonSerializer.class, config.get(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG));
    }

    @Test
    void deveConfigurarAcksCorretamente() throws Exception {
        Method commonConfigMethod = KafkaProducerConfig.class.getDeclaredMethod("commonConfig");
        commonConfigMethod.setAccessible(true);

        @SuppressWarnings("unchecked")
        Map<String, Object> config = (Map<String, Object>) commonConfigMethod.invoke(kafkaProducerConfig);

        assertEquals("all", config.get(ProducerConfig.ACKS_CONFIG));
    }

    @Test
    void deveConfigurarRetriesCorretamente() throws Exception {
        Method commonConfigMethod = KafkaProducerConfig.class.getDeclaredMethod("commonConfig");
        commonConfigMethod.setAccessible(true);

        @SuppressWarnings("unchecked")
        Map<String, Object> config = (Map<String, Object>) commonConfigMethod.invoke(kafkaProducerConfig);

        assertEquals(10, config.get(ProducerConfig.RETRIES_CONFIG));
    }

    @Test
    void deveConfigurarBatchSizeCorretamente() throws Exception {
        Method commonConfigMethod = KafkaProducerConfig.class.getDeclaredMethod("commonConfig");
        commonConfigMethod.setAccessible(true);

        @SuppressWarnings("unchecked")
        Map<String, Object> config = (Map<String, Object>) commonConfigMethod.invoke(kafkaProducerConfig);

        assertEquals(16384, config.get(ProducerConfig.BATCH_SIZE_CONFIG));
    }

    @Test
    void deveConfigurarLingerMsCorretamente() throws Exception {
        Method commonConfigMethod = KafkaProducerConfig.class.getDeclaredMethod("commonConfig");
        commonConfigMethod.setAccessible(true);

        @SuppressWarnings("unchecked")
        Map<String, Object> config = (Map<String, Object>) commonConfigMethod.invoke(kafkaProducerConfig);

        assertEquals(5, config.get(ProducerConfig.LINGER_MS_CONFIG));
    }

    @Test
    void deveConfigurarBufferMemoryCorretamente() throws Exception {
        Method commonConfigMethod = KafkaProducerConfig.class.getDeclaredMethod("commonConfig");
        commonConfigMethod.setAccessible(true);

        @SuppressWarnings("unchecked")
        Map<String, Object> config = (Map<String, Object>) commonConfigMethod.invoke(kafkaProducerConfig);

        assertEquals(33554432, config.get(ProducerConfig.BUFFER_MEMORY_CONFIG));
    }

    @Test
    void deveConfigurarMaxRequestSizeCorretamente() throws Exception {
        Method commonConfigMethod = KafkaProducerConfig.class.getDeclaredMethod("commonConfig");
        commonConfigMethod.setAccessible(true);

        @SuppressWarnings("unchecked")
        Map<String, Object> config = (Map<String, Object>) commonConfigMethod.invoke(kafkaProducerConfig);

        assertEquals(1048576, config.get(ProducerConfig.MAX_REQUEST_SIZE_CONFIG));
    }

    @Test
    void deveRetornarConfiguracoesCompletas() throws Exception {
        Method commonConfigMethod = KafkaProducerConfig.class.getDeclaredMethod("commonConfig");
        commonConfigMethod.setAccessible(true);

        @SuppressWarnings("unchecked")
        Map<String, Object> config = (Map<String, Object>) commonConfigMethod.invoke(kafkaProducerConfig);

        assertAll(
                () -> assertNotNull(config),
                () -> assertEquals(9, config.size()),
                () -> assertTrue(config.containsKey(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG)),
                () -> assertTrue(config.containsKey(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG)),
                () -> assertTrue(config.containsKey(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG)),
                () -> assertTrue(config.containsKey(ProducerConfig.ACKS_CONFIG)),
                () -> assertTrue(config.containsKey(ProducerConfig.RETRIES_CONFIG)),
                () -> assertTrue(config.containsKey(ProducerConfig.BATCH_SIZE_CONFIG)),
                () -> assertTrue(config.containsKey(ProducerConfig.LINGER_MS_CONFIG)),
                () -> assertTrue(config.containsKey(ProducerConfig.BUFFER_MEMORY_CONFIG)),
                () -> assertTrue(config.containsKey(ProducerConfig.MAX_REQUEST_SIZE_CONFIG))
        );
    }

    @Test
    void deveValidarTiposDosBeansProducerFactory() {
        ProducerFactory<String, PedidoRequestDTO> factory1 = kafkaProducerConfig.pedidoRequestDtoProducerFactory();
        ProducerFactory<String, PedidoReceiverRequestDTO> factory2 = kafkaProducerConfig.pedidoReceiverRequestDtoProducerFactory();

        assertNotSame(factory1, factory2);
        assertEquals(DefaultKafkaProducerFactory.class, factory1.getClass());
        assertEquals(DefaultKafkaProducerFactory.class, factory2.getClass());
    }

    @Test
    void deveValidarTiposDosBeansKafkaTemplate() {
        KafkaTemplate<String, PedidoRequestDTO> template1 = kafkaProducerConfig.pedidoRequestDtoKafkaTemplate();
        KafkaTemplate<String, PedidoReceiverRequestDTO> template2 = kafkaProducerConfig.pedidoReceiverRequestDtoKafkaTemplate();

        assertNotSame(template1, template2);
        assertNotSame(template1.getProducerFactory(), template2.getProducerFactory());
    }
}