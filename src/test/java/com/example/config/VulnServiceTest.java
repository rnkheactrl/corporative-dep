package com.example.config;

import com.example.domain.Rule;
import com.example.domain.VulnId;
import com.example.domain.VulnStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Spring context starts and VulnService gets the Rule chain injected. */
@SpringBootTest
class VulnServiceTest {

    @Autowired
    private VulnService service;

    @Autowired
    private Rule rules;

    @Test
    void contextWiresRuleIntoService() {
        assertNotNull(rules);
        assertNotNull(service);
    }

    @Test
    void allowedMoveGoesThrough() {
        assertEquals(VulnStatus.CONFIRMED,
                service.move(new VulnId("VULN-7"), VulnStatus.DISCOVERED, VulnStatus.CONFIRMED));
    }

    @Test
    void forbiddenMoveIsRejected() {
        assertThrows(IllegalStateException.class,
                () -> service.move(new VulnId("VULN-7"), VulnStatus.DISCOVERED, VulnStatus.PATCHED));
    }
}
