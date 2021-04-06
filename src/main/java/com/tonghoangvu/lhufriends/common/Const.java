package com.tonghoangvu.lhufriends.common;

import lombok.Getter;
import org.jetbrains.annotations.Contract;

@Getter
public enum Const {
    MAX_STUDENTS_PER_REQUEST(100),

    TOKEN_HEADER_PREFIX("Bearer");

    private String stringValue;
    private Integer intValue;
    private Double doubleValue;

    @Contract(pure = true)
    Const(String stringValue) {
        this.stringValue = stringValue;
    }

    @Contract(pure = true)
    Const(Integer intValue) {
        this.intValue = intValue;
    }

    @Contract(pure = true)
    Const(Double doubleValue) {
        this.doubleValue = doubleValue;
    }
}
