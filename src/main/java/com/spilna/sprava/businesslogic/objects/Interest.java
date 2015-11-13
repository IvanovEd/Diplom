package com.spilna.sprava.businesslogic.objects;

/**
 * Created by Ivanov on 05.11.15.
 */
public enum Interest {
    POLITIC(1),
    MUSIC(2),
    UNKNOWN(2),
    OTHER(3);
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
