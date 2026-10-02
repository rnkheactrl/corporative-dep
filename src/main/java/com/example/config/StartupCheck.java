package com.example.config;

import com.example.domain.VulnId;
import com.example.domain.VulnStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/** On `mvn spring-boot:run`, proves Spring injected the Rule into VulnService. */
@Component
public class StartupCheck implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(StartupCheck.class);

    private final VulnService service;

    public StartupCheck(VulnService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) {
        VulnStatus result = service.move(new VulnId("VULN-DEMO"), VulnStatus.DISCOVERED, VulnStatus.CONFIRMED);
        log.info("VulnService ready: VULN-DEMO DISCOVERED -> {}", result);
    }
}
