package com.example.domain;

/**
 * One business rule about moving a vulnerability between statuses.
 * Throws IllegalStateException when the move is not allowed; returns normally otherwise.
 */
public interface Rule {

    void check(VulnStatus from, VulnStatus to);
}
