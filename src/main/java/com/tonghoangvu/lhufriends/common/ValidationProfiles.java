package com.tonghoangvu.lhufriends.common;

import org.jetbrains.annotations.Contract;

public class ValidationProfiles {
    @Contract(pure = true)
    private ValidationProfiles() {}

    public interface OnAuth {}
    public interface OnCreate {}
    public interface OnUpdate {}
}
