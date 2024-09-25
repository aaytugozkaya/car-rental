package com.aaytugozkaya.carrental.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
        private String name;

        @NotBlank(message = "Surname is required")
        @Size(min = 2, max = 50, message = "Surname must be between 2 and 50 characters")
        private String surname;

        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        private String email;

        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        private String password;

        @NotBlank(message = "Mobile phone is required")
        @Pattern(regexp = "^[0-9]{10}$", message = "Mobile phone must be 10 digits. (5XX XXX XX XX)")
        private String mobilePhone;

        @NotBlank(message = "Driver license number is required")
        private String driverLicenseNumber;

        @NotNull(message = "Birth date is required")
        @Past(message = "Birth date must be a past date")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd")
        private LocalDate birthDate;

        @NotNull(message = "Driver license date is required")
        @Past(message = "Driver license date must be a past date")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate driverLicenseDate;

}
