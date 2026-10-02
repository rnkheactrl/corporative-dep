package com.example.domain;

import java.util.Map;
import java.util.Set;

/**
 * The status table from the README: which moves are allowed.
 * Anything not listed here is forbidden.
 */
public class TransitionRule implements Rule {

    private static final Map<VulnStatus, Set<VulnStatus>> ALLOWED = Map.of(
            VulnStatus.DISCOVERED, Set.of(VulnStatus.CONFIRMED),
            VulnStatus.CONFIRMED, Set.of(VulnStatus.PATCHED),
            VulnStatus.PATCHED, Set.of(VulnStatus.VERIFIED)
    );

    @Override
    public void check(VulnStatus from, VulnStatus to) {
        if (!ALLOWED.getOrDefault(from, Set.of()).contains(to)) {
            throw new IllegalStateException("Transition not allowed: " + from + " -> " + to);
        }
    }
}
