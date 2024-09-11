package com.aaytugozkaya.carrental.dto.request;

import com.aaytugozkaya.carrental.entity.enums.CarLocation;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class AvailableCarRequest {
    @NotNull
    @Enumerated(EnumType.STRING)
    private CarLocation location;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
}
