package com.aaytugozkaya.carrental.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class RentalCarResponse {
    private UUID id;
    private String brand;
    private String model;
    private String year;
    private String color;
    private String plate;
    private String type;
    private String fuelType;
    private String gearType;
    private String imageUrl;
    private Double km;
    private String seatCount;
    private String doorCount;
    private Boolean airCondition;
    private BigDecimal dailyRentingPrice;
    private String status;
    private String description;
    private String location;
    private BigDecimal deposit;
    private String minDriverLicenseYear;
    private Integer discountRate;
}
