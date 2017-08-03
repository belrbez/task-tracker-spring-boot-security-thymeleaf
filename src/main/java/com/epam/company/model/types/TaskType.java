package com.epam.company.model.types;

/**
 * Created by @belrbeZ
 */
public enum TaskType {
    EMPTY(0),
    WEB(1),
    DESKTOP(2),
    SITE(3),
    ANDROID(4),
    IOS(5),
    MOBILE(6),
    CORPORATIVE(7);

    private final long value;

    TaskType(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
