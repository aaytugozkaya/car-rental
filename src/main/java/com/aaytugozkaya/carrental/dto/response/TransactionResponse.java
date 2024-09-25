package com.aaytugozkaya.carrental.dto.response;

import com.aaytugozkaya.carrental.entity.enums.CarLocation;
import com.aaytugozkaya.carrental.entity.RentalCar;
import com.aaytugozkaya.carrental.entity.User;
import com.aaytugozkaya.carrental.entity.enums.Status;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class TransactionResponse {

    private UUID id;
    private UUID userId;
    private UUID rentalCarId;
    private String plateNumber;
    private LocalDate transactionDate;
    private LocalDate startDate;
    private LocalDate returnDate;
    private CarLocation location;
    private BigDecimal totalPrice;
    private Boolean isReturned;
    private Status status;
}
