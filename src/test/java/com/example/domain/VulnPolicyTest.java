package com.example.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Plain JUnit, no Spring: the domain works without a container. */
class VulnPolicyTest {

    private final Rule rules = new RuleChain(List.of(new VerifiedIsFinal(), new TransitionRule()));
    private final VulnPolicy policy = new VulnPolicy(rules);
    private final VulnId id = new VulnId("VULN-1");

    @ParameterizedTest
    @CsvSource({
            "DISCOVERED, CONFIRMED,  true",
            "CONFIRMED,  PATCHED,    true",
            "PATCHED,    VERIFIED,   true",
            "DISCOVERED, PATCHED,    false",
            "PATCHED,    DISCOVERED, false",
            "VERIFIED,   DISCOVERED, false",
            "VERIFIED,   PATCHED,    false"
    })
    void moveFollowsReadmeTable(VulnStatus from, VulnStatus to, boolean allowed) {
        if (allowed) {
            assertEquals(to, policy.move(id, from, to));
        } else {
            assertThrows(IllegalStateException.class, () -> policy.move(id, from, to));
        }
    }

    @Test
    void errorMessageNamesTheVuln() {
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> policy.move(id, VulnStatus.DISCOVERED, VulnStatus.PATCHED));
        assertTrue(e.getMessage().contains("VULN-1"));
    }

    @Test
    void verifiedIsStoppedByStopFactorFirst() {
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> policy.move(id, VulnStatus.VERIFIED, VulnStatus.CONFIRMED));
        assertTrue(e.getMessage().contains("closed for audit"));
    }

    @Test
    void nullIdThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> policy.move(null, VulnStatus.DISCOVERED, VulnStatus.CONFIRMED));
    }

    @Test
    void nullStatusThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> policy.move(id, null, VulnStatus.CONFIRMED));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void blankIdThrows(String rawId) {
        assertThrows(IllegalArgumentException.class, () -> new VulnId(rawId));
    }
}
