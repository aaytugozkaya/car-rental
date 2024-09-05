package com.aaytugozkaya.carrental.validator;

import com.aaytugozkaya.carrental.dto.request.RentalCarRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.Year;

@Component
public class RentalCarValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return RentalCarRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RentalCarRequest request = (RentalCarRequest) target;

        // Check if 'year' is not greater than the current year
        int currentYear = Year.now().getValue();
        try {
            int carYear = Integer.parseInt(request.getYear());
            if (carYear > currentYear) {
                errors.rejectValue("year", "field.invalid", "Year cannot be greater than the current year.");
            }
        } catch (NumberFormatException e) {
            errors.rejectValue("year", "field.invalid", "Year must be a valid number.");
        }

        // Other field validations
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dailyRentingPrice", "field.required", "Daily renting price is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "status", "field.required", "Car status is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "location", "field.required", "Car location is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "brand", "field.required", "Brand is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "model", "field.required", "Model is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "color", "field.required", "Color is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "plate", "field.required", "Plate is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "type", "field.required", "Car type is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fuelType", "field.required", "Fuel type is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gearType", "field.required", "Gear type is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "km", "field.required", "Kilometers driven is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "seatCount", "field.required", "Seat count is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "doorCount", "field.required", "Door count is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "airCondition", "field.required", "Air conditioning is required.");
    }
}
