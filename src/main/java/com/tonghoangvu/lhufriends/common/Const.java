package com.tonghoangvu.lhufriends.common;

import lombok.Getter;

@Getter
public enum Const {
    MAX_STUDENTS_PER_REQUEST(100),

    TOKEN_HEADER_PREFIX("Bearer");

    private String stringValue;
    private Integer intValue;
    private Double doubleValue;

    Const(String stringValue) {
        this.stringValue = stringValue;
    }

    Const(Integer intValue) {
        this.intValue = intValue;
    }

    Const(Double doubleValue) {
        this.doubleValue = doubleValue;
    }
}
