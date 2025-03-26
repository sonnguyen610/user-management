package com.springboot.user_management.validation.user;

import com.springboot.user_management.constant.FailureMessage;
import org.apache.coyote.BadRequestException;

public class UserRegisterValidate {
    public static void validatePassword(String password) throws Exception {
        String trimPassword = password != null ? password.trim() : null;
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{5,}$";
        if (trimPassword == null) {
            throw new BadRequestException(FailureMessage.NOT_BLANK_VALUE.replace("Value", "Password"));
        }
        if (!trimPassword.matches(regex)) {
            throw new BadRequestException(FailureMessage.INVALID_VALUE.replace("value", "password"));
        }
    }

    public static void validateEmail(String email) throws Exception {
        String trimEmail = email != null ? email.trim() : null;
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (trimEmail == null) {
            throw new BadRequestException(FailureMessage.NOT_BLANK_VALUE.replace("Value", "Email"));
        }
        if (!trimEmail.matches(regex)) {
            throw new BadRequestException(FailureMessage.INVALID_VALUE.replace("value", "email"));
        }
    }

    public static void validatePhone(String phone) throws Exception {
        String trimPhone = phone != null ? phone.trim() : null;
        String regex = "^0\\d{9}$";
        if (trimPhone == null) {
            throw new BadRequestException(FailureMessage.NOT_BLANK_VALUE.replace("Value", "Phone number"));
        }
        if (!trimPhone.matches(regex)) {
            throw new BadRequestException(FailureMessage.INVALID_VALUE.replace("value", "phone number"));
        }
    }

    public static void validateValue(String value, String name) throws Exception {
        String trimValue = value != null ? value.trim() : null;
        if (trimValue == null) {
            throw new BadRequestException(FailureMessage.NOT_BLANK_VALUE.replace("Value", name));
        }
    }
}
