package com.example.domain;

/**
 * Validates input and asks the Rule whether a move is allowed.
 * Plain Java: the Rule is passed in; Spring decides which one, in config.
 */
public class VulnPolicy {

    private final Rule rule;

    public VulnPolicy(Rule rule) {
        if (rule == null) {
            throw new IllegalArgumentException("Rule must not be null");
        }
        this.rule = rule;
    }

    public VulnStatus move(VulnId id, VulnStatus from, VulnStatus to) {
        if (id == null) {
            throw new IllegalArgumentException("VulnId must not be null");
        }
        if (from == null || to == null) {
            throw new IllegalArgumentException("Statuses must not be null");
        }
        try {
            rule.check(from, to);
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Vuln " + id.getValue() + ": " + e.getMessage(), e);
        }
        return to;
    }
}
