package com.example.domain;

import java.util.List;

/** Runs several rules in order; the first one that throws stops the move. */
public class RuleChain implements Rule {

    private final List<Rule> rules;

    public RuleChain(List<Rule> rules) {
        if (rules == null || rules.isEmpty()) {
            throw new IllegalArgumentException("RuleChain needs at least one rule");
        }
        this.rules = List.copyOf(rules);
    }

    @Override
    public void check(VulnStatus from, VulnStatus to) {
        for (Rule rule : rules) {
            rule.check(from, to);
        }
    }
}
