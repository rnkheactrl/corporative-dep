package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VulnPolicyTest {

    private VulnPolicy policy = new VulnPolicy();
    private VulnId id = new VulnId("VULN-1001");

    @ParameterizedTest
    @CsvSource({
            "DISCOVERED, CONFIRMED,  true",
            "CONFIRMED,  PATCHED,    true",
            "DISCOVERED, PATCHED,    false",
            "PATCHED,    DISCOVERED, false"
    })
    void moveFollowsReadmeTable(VulnStatus from, VulnStatus to, boolean allowed) {
        if (allowed) {
            VulnStatus result = policy.move(id, from, to);
            assertEquals(to, result);
        } else {
            assertThrows(IllegalStateException.class, () -> policy.move(id, from, to));
        }
    }

    @Test
    void nullIdThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> policy.move(null, VulnStatus.DISCOVERED, VulnStatus.CONFIRMED));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void blankOrNullIdValueThrowsWhenCreatingVulnId(String rawId) {
        assertThrows(IllegalArgumentException.class, () -> new VulnId(rawId));
    }
}
