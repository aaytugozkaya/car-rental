package com.aaytugozkaya.carrental.entity;

import com.aaytugozkaya.carrental.entity.enums.CarLocation;
import com.aaytugozkaya.carrental.entity.enums.CarStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class RentalCar extends Car {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private BigDecimal dailyRentingPrice;
    @Enumerated(EnumType.STRING)
    private CarStatus status;
    private String description;
    @Enumerated(EnumType.STRING)
    private CarLocation location;
    private BigDecimal deposit;
    private String minDriverLicenseYear;
    private Integer discountRate;
    @ManyToOne
    @JoinColumn(name = "renter_id")
    private User renter;
    @OneToMany(mappedBy = "rentalCar", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;
    @OneToMany(mappedBy = "rentalCar", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservations;
}
