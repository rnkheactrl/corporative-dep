package com.example.config;

import com.example.domain.Rule;
import com.example.domain.RuleChain;
import com.example.domain.TransitionRule;
import com.example.domain.VerifiedIsFinal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RuleConfig {

    /** The chain: stop-factor first, then the status table. */
    @Bean
    public Rule rules() {
        return new RuleChain(List.of(
                new VerifiedIsFinal(),
                new TransitionRule()
        ));
    }
}
