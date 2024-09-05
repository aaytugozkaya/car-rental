package com.aaytugozkaya.carrental.entity;

import com.aaytugozkaya.carrental.entity.enums.CarLocation;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rental_car_id")
    private RentalCar rentalCar;
    private LocalDate transactionDate;
    private LocalDate borrowingDate;
    private LocalDate returnDate;
    @Enumerated(EnumType.STRING)
    private CarLocation location;
    private BigDecimal totalPrice;
    private Boolean isReturned;
}
