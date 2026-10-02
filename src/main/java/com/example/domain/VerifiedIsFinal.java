package com.example.domain;

/**
 * Stop-factor: a VERIFIED vulnerability is closed for audit.
 * It never moves again; a regression is tracked as a new vuln with a new VulnId.
 */
public class VerifiedIsFinal implements Rule {

    @Override
    public void check(VulnStatus from, VulnStatus to) {
        if (from == VulnStatus.VERIFIED) {
            throw new IllegalStateException(
                    "Vuln is VERIFIED and closed for audit; open a new vuln instead of moving to " + to);
        }
    }
}
