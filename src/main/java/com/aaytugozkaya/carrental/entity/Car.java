package com.aaytugozkaya.carrental.entity;

import com.aaytugozkaya.carrental.entity.enums.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@MappedSuperclass
@SuperBuilder
@RequiredArgsConstructor
public class Car {

    private Brand brand;
    private Model model;
    private String year;
    private String color;
    @Column(unique = true, nullable = false)
    private String plate;
    @Enumerated(EnumType.STRING)
    private CarType type;
    private FuelType fuelType;
    @Enumerated(EnumType.STRING)
    private CarGearType gearType;
    private String imageUrl;
    private Double km;
    private String seatCount;
    private String doorCount;
    private Boolean airCondition;
}
