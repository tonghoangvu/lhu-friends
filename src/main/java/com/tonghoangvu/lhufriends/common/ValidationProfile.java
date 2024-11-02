package com.tonghoangvu.lhufriends.common;

import org.jetbrains.annotations.Contract;

public class ValidationProfile {
    @Contract(pure = true)
    private ValidationProfile() {}

    public interface OnCreate {}

    public interface OnUpdate {}
}
