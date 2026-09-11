package com.example;

public class VulnPolicy {

    public VulnStatus move(VulnId id, VulnStatus from, VulnStatus to) {
        if (id == null) {
            throw new IllegalArgumentException("VulnId must not be null");
        }
        if (from == null || to == null) {
            throw new IllegalArgumentException("Statuses must not be null");
        }

        if (isAllowed(from, to)) {
            return to;
        }

        throw new IllegalStateException("Transition not allowed for vuln " + id.getValue() + ": " + from + " -> " + to);
    }

    private boolean isAllowed(VulnStatus from, VulnStatus to) {
        if (from == VulnStatus.DISCOVERED) {
            return to == VulnStatus.CONFIRMED;
        }
        if (from == VulnStatus.CONFIRMED) {
            return to == VulnStatus.PATCHED;
        }
        if (from == VulnStatus.PATCHED) {
            return to == VulnStatus.VERIFIED;
        }
        return false;
    }
}
