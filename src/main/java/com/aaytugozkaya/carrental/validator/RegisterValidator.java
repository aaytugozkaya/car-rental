package com.aaytugozkaya.carrental.validator;

import com.aaytugozkaya.carrental.dto.request.RegisterRequest;
import org.springframework.validation.Validator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import java.util.Set;

@Component
public class RegisterValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

        RegisterRequest request = (RegisterRequest) target;

        if (request.getName() == null || request.getName().isEmpty()) {
            errors.rejectValue("firstname", "field.required", "ad zorunludur.");
        }

        if (request.getSurname() == null || request.getSurname().isEmpty()) {
            errors.rejectValue("lastname", "field.required", "Soyad zorunludur.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.e_mail.notempty","Email zorunludur.");
        if (!errors.hasFieldErrors("email")) {
            if (!request.getEmail().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
                errors.rejectValue("email", "error.e_mail.email", "Email formatında olmalıdır");
            }
        }

        if (request.getMobilePhone() == null || request.getMobilePhone().isEmpty()) {
            errors.rejectValue("mobilePhone", "field.required", "Cep telefonu numarası zorunludur.");
        } else if (!request.getMobilePhone().matches("^\\d{10}$")) {
            errors.rejectValue("mobilePhone", "field.invalid", "Geçersiz cep telefonu numarası.");
        }

    }

}
