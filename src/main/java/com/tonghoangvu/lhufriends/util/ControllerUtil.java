package com.tonghoangvu.lhufriends.util;

import com.tonghoangvu.lhufriends.common.ErrorCode;
import com.tonghoangvu.lhufriends.exception.AppException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

public class ControllerUtil {
    @Contract(pure = true)
    private ControllerUtil() {}

    private static @Nullable String getFirstBindingError(@NotNull BindingResult bindingResult) {
        return bindingResult.hasErrors()
                ? bindingResult.getAllErrors().get(0).getDefaultMessage()
                : null;
    }

    public static void handleBindingError(@NotNull BindingResult bindingResult) {
        String error = getFirstBindingError(bindingResult);
        if (error != null)
            throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST, error);
    }
}
