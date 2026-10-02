package com.example.domain;

public class VulnId {

    private final String value;

    public VulnId(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("VulnId must not be null or blank");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
