package com.alvarohdezarroyo.lookmomicanfly.Enums;

import lombok.Getter;

@Getter
public enum SizeRun {

    NO_SIZE("ONE-SIZE"),
    SMALL("S"),
    MEDIUM("M"),
    LARGE("L"),
    EXTRA_LARGE("XL"),
    THIRTY_EIGHT("38"),
    THIRTY_NINE("39"),
    FORTY("40"),
    FORTY_ONE("45"),
    FORTY_TWO("42"),
    FORTY_THREE("43"),
    FORTY_FOUR("44"),
    FORTY_FIVE("45");

    private final String value;

    SizeRun(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }

}
