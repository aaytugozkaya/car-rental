package com.aaytugozkaya.carrental.mapper;

import com.aaytugozkaya.carrental.dto.request.ReservationRequest;
import com.aaytugozkaya.carrental.dto.response.ReservationResponse;
import com.aaytugozkaya.carrental.entity.RentalCar;
import com.aaytugozkaya.carrental.entity.Reservation;
import com.aaytugozkaya.carrental.entity.User;
import com.aaytugozkaya.carrental.repository.RentalCarRepository;
import com.aaytugozkaya.carrental.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ReservationMapper {
    private final RentalCarRepository rentalCarRepository;
    private final UserRepository userRepository;
    private final RentalCarMapper rentalCarMapper;

    public Reservation toReservation(ReservationRequest request){
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        RentalCar rentalCar = rentalCarRepository.findById(request.getRentalCarId())
                .orElseThrow(() -> new RuntimeException("Rental car not found"));
        return Reservation.builder()
                .rentalCar(rentalCar)
                .user(user)
                .startDate(request.getStartDate())
                .returnDate(request.getReturnDate())
                .status(request.getStatus())
                .build();
    }

    public ReservationResponse toReservationResponse(Reservation reservation){
        return ReservationResponse.builder()
                .id(reservation.getId())
                .rentalCar(RentalCarMapper.toRentalCarResponse(reservation.getRentalCar()) )
                .user(UserMapper.toUserResponse(reservation.getUser()))
                .startDate(reservation.getStartDate())
                .returnDate(reservation.getReturnDate())
                .status(reservation.getStatus())
                .build();
    }
}
