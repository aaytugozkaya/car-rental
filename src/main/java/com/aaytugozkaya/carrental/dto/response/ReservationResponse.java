package com.aaytugozkaya.carrental.dto.response;

import com.aaytugozkaya.carrental.entity.enums.Status;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ReservationResponse {
    private UUID id;
    private RentalCarResponse rentalCar;
    private UserResponse user;
    private LocalDate startDate;
    private LocalDate returnDate;
    private Status status;
}
