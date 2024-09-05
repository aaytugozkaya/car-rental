package com.aaytugozkaya.carrental.dto.request;

import com.aaytugozkaya.carrental.entity.enums.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class RentalCarRequest {
    @NotNull(message = "Daily renting price is required.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Daily renting price must be greater than 0.")
    private BigDecimal dailyRentingPrice;

    @NotNull(message = "Car status is required.")
    @Enumerated(EnumType.STRING)
    private CarStatus status;

    private String description;

    @NotNull(message = "Car location is required.")
    @Enumerated(EnumType.STRING)
    private CarLocation location;

    @DecimalMin(value = "0.0", inclusive = false, message = "Deposit must be greater than 0.")
    private BigDecimal deposit;

    @Pattern(regexp = "\\d{4}", message = "Driver license year must be a 4-digit year.")
    private String minDriverLicenseYear;

    @Min(value = 0, message = "Discount rate must be 0 or greater.")
    @Max(value = 100, message = "Discount rate must be 100 or less.")
    private Integer discountRate;

    @NotNull(message = "Brand is required.")
    private Brand brand;

    @NotNull(message = "Model is required.")
    private Model model;

    @NotNull(message = "Year is required.")
    @Pattern(regexp = "\\d{4}", message = "Year must be a 4-digit number.")
    private String year;

    @NotNull(message = "Color is required.")
    private String color;

    @NotNull(message = "Plate is required.")
    private String plate;

    @NotNull(message = "Car type is required.")
    @Enumerated(EnumType.STRING)
    private CarType type;

    @NotNull(message = "Fuel type is required.")
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    @NotNull(message = "Gear type is required.")
    @Enumerated(EnumType.STRING)
    private CarGearType gearType;

    private String imageUrl;

    @NotNull(message = "Kilometers driven is required.")
    @Min(value = 0, message = "Kilometers driven must be 0 or greater.")
    private Double km;

    @NotNull(message = "Seat count is required.")
    @Pattern(regexp = "\\d+", message = "Seat count must be a number.")
    private String seatCount;

    @NotNull(message = "Door count is required.")
    @Pattern(regexp = "\\d+", message = "Door count must be a number.")
    private String doorCount;

    @NotNull(message = "Air conditioning availability is required.")
    private Boolean airCondition;

}
