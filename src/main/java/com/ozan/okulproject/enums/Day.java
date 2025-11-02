package com.ozan.okulproject.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Day {
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday"),
    SUNDAY("Sunday");

    private final String value;

    Day(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
