package com.example.config;

import com.example.domain.Rule;
import com.example.domain.VulnId;
import com.example.domain.VulnPolicy;
import com.example.domain.VulnStatus;
import org.springframework.stereotype.Service;

@Service
public class VulnService {

    private final VulnPolicy policy;

    public VulnService(Rule rules) {
        this.policy = new VulnPolicy(rules);
    }

    public VulnStatus move(VulnId id, VulnStatus from, VulnStatus to) {
        return policy.move(id, from, to);
    }
}
