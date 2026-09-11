package com.example;

public class VulnId {

    private String value;

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
