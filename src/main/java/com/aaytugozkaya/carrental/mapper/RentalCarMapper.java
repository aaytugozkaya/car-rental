package com.aaytugozkaya.carrental.mapper;

import com.aaytugozkaya.carrental.dto.request.RentalCarRequest;
import com.aaytugozkaya.carrental.dto.response.RentalCarResponse;
import com.aaytugozkaya.carrental.entity.RentalCar;
import org.springframework.stereotype.Component;

@Component
public class RentalCarMapper {
    public static RentalCar toRentalCar(RentalCarRequest request){
        return RentalCar.builder()
                .dailyRentingPrice(request.getDailyRentingPrice())
                .status(request.getStatus())
                .description(request.getDescription())
                .location(request.getLocation())
                .deposit(request.getDeposit())
                .minDriverLicenseYear(request.getMinDriverLicenseYear())
                .discountRate(request.getDiscountRate())
                .brand(request.getBrand())
                .model(request.getModel())
                .year(request.getYear())
                .color(request.getColor())
                .plate(request.getPlate())
                .type(request.getType())
                .fuelType(request.getFuelType())
                .gearType(request.getGearType())
                .imageUrl(request.getImageUrl())
                .km(request.getKm())
                .seatCount(request.getSeatCount())
                .doorCount(request.getDoorCount())
                .airCondition(request.getAirCondition())
                .build();
    }

    public static RentalCarResponse toRentalCarResponse(RentalCar rentalCar){
        return RentalCarResponse.builder()
                .id(rentalCar.getId())
                .brand(rentalCar.getBrand().name())
                .model(rentalCar.getModel().name())
                .year(rentalCar.getYear())
                .color(rentalCar.getColor())
                .plate(rentalCar.getPlate())
                .type(rentalCar.getType().name())
                .fuelType(rentalCar.getFuelType().name())
                .gearType(rentalCar.getGearType().name())
                .imageUrl(rentalCar.getImageUrl())
                .km(rentalCar.getKm())
                .seatCount(rentalCar.getSeatCount())
                .doorCount(rentalCar.getDoorCount())
                .airCondition(rentalCar.getAirCondition())
                .dailyRentingPrice(rentalCar.getDailyRentingPrice())
                .status(rentalCar.getStatus().name())
                .description(rentalCar.getDescription())
                .location(rentalCar.getLocation().name())
                .deposit(rentalCar.getDeposit())
                .minDriverLicenseYear(rentalCar.getMinDriverLicenseYear())
                .discountRate(rentalCar.getDiscountRate())
                .build();
    }
}
