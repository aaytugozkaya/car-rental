package com.aaytugozkaya.carrental.dto.response;


import com.aaytugozkaya.carrental.entity.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.UUID;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private String mobilePhone;
    private String driverLicenseNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate driverLicenseDate;
    @Enumerated(EnumType.STRING)
    private Role role;
    private BigDecimal balance;
}
