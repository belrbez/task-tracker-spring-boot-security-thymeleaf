package com.epam.company.model.types;

/**
 * Created by @belrbeZ
 */
public enum FeedType {
    ALL(0),
    OWNER(10),
    RECENT(20),
    LOCAL(30),
    CHART(40),
    SUBSCRIBED(50);

    private final int value;

    FeedType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static FeedType calc(Integer value) {
        switch (value) {
            case 20: return FeedType.RECENT;
            case 30: return FeedType.LOCAL;
            case 40: return FeedType.CHART;
            case 50: return FeedType.SUBSCRIBED;
            case 10: return FeedType.OWNER;
            case 0:
            default: return FeedType.ALL;
        }
    }
}
