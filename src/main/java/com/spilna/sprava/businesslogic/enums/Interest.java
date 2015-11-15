package com.spilna.sprava.businesslogic.enums;

/**
 * Created by Ivanov on 05.11.15.
 */
public enum Interest {
    NEWS(1),
    POLITIC(2),
    GAMES(3),
    MUSIC(4),
    SPORT(5),
    SCIENCE(6),
    BUSINESS(7),
    CINEMA(8),
    HUMOR(9),
    OTHER(10);
    private final int value;

    private Interest(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Interest parse(int i) {
        for (Interest value: values()) {
            if (i != 0 && value.getValue() == i){
                return value;
            }
        }
        return null;
    }
}
