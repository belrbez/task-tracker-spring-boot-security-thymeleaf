package com.epam.company.model.types;

/**
 * Created by @belrbeZ
 */
public enum TaskState {
    UNKNOWN(-1),
    HIDEN(0),
    NEW(1),
    ACCEPTED(2),
    REJECTED(3),
    BANNED(4),
    PROCESSING(5),
    TO_REVIEW(6),
    DONE(7);

    private final int value;

    TaskState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
