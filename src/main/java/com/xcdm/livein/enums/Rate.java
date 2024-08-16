package com.xcdm.livein.enums;

public enum Rate {
    ONE(1),
    ONE_POINT_FIVE(1.5),
    TWO(2),
    TWO_POINT_FIVE(2.5),
    THREE(3),
    THREE_POINT_FIVE(3.5),
    FOUR(4),
    FOUR_POINT_FIVE(4.5),
    FIVE(5);

    private final double value;

    Rate(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

}
