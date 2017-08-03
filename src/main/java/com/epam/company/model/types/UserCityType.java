package com.epam.company.model.types;

public enum UserCityType {
    UNKNOWN(-1),
    EMPTY(0),
    SAINT_PETERSBURG(2),
    MOSCOW(3),
    MINSK(4);

    private final int value;

    UserCityType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
