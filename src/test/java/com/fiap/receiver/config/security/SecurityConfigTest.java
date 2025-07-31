package com.fiap.receiver.config.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.kafka.bootstrap-servers=localhost:9092",
        "spring.kafka.consumer.group-id=test-group"
})
class SecurityConfigTest {

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private SecurityFilterChain securityFilterChain;

    @Test
    void deveCarregarContextoDaAplicacao() {
        assertNotNull(securityConfig);
        assertNotNull(securityFilterChain);
    }

    @Test
    void deveConfigurarSecurityFilterChain() throws Exception {
        SecurityFilterChain filterChain = securityConfig.securityFilterChain(null);

        assertNotNull(filterChain);
    }

    @Test
    void deveTerBeanSecurityConfigCorreto() {
        assertTrue(securityConfig instanceof SecurityConfig);
    }

    @Test
    void deveValidarQueSecurityFilterChainEstaConfigurado() {
        assertNotNull(securityFilterChain);
        assertTrue(securityFilterChain.getFilters().size() > 0);
    }
}