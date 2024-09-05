package com.aaytugozkaya.carrental.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

public class ErrorUtils {
    public static List<String> getErrorMessages(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream()
                .map(objectError -> {
                    if (objectError instanceof FieldError) {
                        return ((FieldError) objectError).getDefaultMessage();
                    } else {
                        return objectError.getDefaultMessage();
                    }
                })
                .collect(Collectors.toList());
    }
}
