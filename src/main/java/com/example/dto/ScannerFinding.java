package com.example.dto;

import com.example.domain.VulnId;
import com.example.domain.VulnStatus;

/**
 * Shape of a finding as an external scanner reports it (JSON parsing comes later).
 * Vendor vocabulary stays here; the domain only sees VulnId and VulnStatus.
 */
public record ScannerFinding(String findingId, String state) {

    public VulnId toVulnId() {
        return new VulnId(findingId);
    }

    public VulnStatus toStatus() {
        if (state == null) {
            throw new IllegalArgumentException("Scanner state must not be null");
        }
        return switch (state.trim().toLowerCase()) {
            case "open", "new" -> VulnStatus.DISCOVERED;
            case "triaged" -> VulnStatus.CONFIRMED;
            case "fixed" -> VulnStatus.PATCHED;
            case "closed" -> VulnStatus.VERIFIED;
            default -> throw new IllegalArgumentException("Unknown scanner state: " + state);
        };
    }
}
