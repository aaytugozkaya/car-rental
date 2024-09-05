package com.aaytugozkaya.carrental.dto.request;

import com.aaytugozkaya.carrental.entity.enums.CarLocation;
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
public class TransactionRequest {
    private UUID userId;
    private UUID rentalCarId;
    private LocalDate transactionDate;
    private LocalDate borrowingDate;
    private LocalDate returnDate;
    private CarLocation location;
    private BigDecimal totalPrice;
}
