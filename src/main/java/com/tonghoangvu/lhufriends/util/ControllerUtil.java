package com.tonghoangvu.lhufriends.util;

import com.tonghoangvu.lhufriends.common.ErrorCode;
import com.tonghoangvu.lhufriends.exception.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

public class ControllerUtil {
    private ControllerUtil() {}

    private static String getFirstBindingError(final BindingResult bindingResult) {
        return bindingResult.hasErrors()
                ? bindingResult.getAllErrors().get(0).getDefaultMessage()
                : null;
    }

    public static void handleBindingError(final BindingResult bindingResult) {
        String error = getFirstBindingError(bindingResult);
        if (error != null)
            throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST, error);
    }
}
