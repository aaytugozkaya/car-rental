package com.aaytugozkaya.carrental.service;

import com.aaytugozkaya.carrental.dto.request.ReservationRequest;
import com.aaytugozkaya.carrental.dto.response.ReservationResponse;
import com.aaytugozkaya.carrental.entity.Reservation;
import com.aaytugozkaya.carrental.entity.enums.Status;
import com.aaytugozkaya.carrental.exception.*;
import com.aaytugozkaya.carrental.mapper.ReservationMapper;
import com.aaytugozkaya.carrental.repository.RentalCarRepository;
import com.aaytugozkaya.carrental.repository.ReservationRepository;
import com.aaytugozkaya.carrental.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final RentalCarRepository rentalCarRepository;
    private final UserRepository userRepository;

    public List<ReservationResponse> getAllReservations() {
        return reservationRepository.findAll().stream().filter(t->t.getStatus() == Status.ACTIVE).map(reservationMapper::toReservationResponse).toList();
    }

    public ReservationResponse getReservationById(UUID id) {
        return reservationMapper.toReservationResponse(reservationRepository.findById(id).orElseThrow(() -> new ReservationNotFoundException("Reservation not found")));
    }

    public ReservationResponse saveReservation(ReservationRequest reservationRequest) {

        if(!reservationRequest.getStartDate().isAfter(reservationRequest.getReturnDate())){
            if (!reservationRepository.isCarReserved(reservationRequest.getRentalCarId(), reservationRequest.getStartDate(), reservationRequest.getReturnDate())){
                return reservationMapper.toReservationResponse(reservationRepository.save(reservationMapper.toReservation(reservationRequest)));
            }
            else {
                throw new CarIsAlreadyReservedException("Car is already reserved for this date range");
            }
        }
        else{
            throw new ReturnDateIsNotValidException("Return date must be after start date");
        }
    }

    public ReservationResponse updateReservation(UUID reservationId, ReservationRequest reservationRequest) {

        Reservation existingReservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));
        boolean datesChanged = !existingReservation.getStartDate().equals(reservationRequest.getStartDate())
                || !existingReservation.getReturnDate().equals(reservationRequest.getReturnDate());
        if (datesChanged) {
            if (reservationRequest.getStartDate().isAfter(reservationRequest.getReturnDate())) {
                throw new ReturnDateIsNotValidException("Return date must be after start date");
            }
            List<UUID> overlappingReservations = reservationRepository.findOverlappingReservation(
                    reservationRequest.getRentalCarId(),
                    reservationRequest.getStartDate(),
                    reservationRequest.getReturnDate()
            ).orElse(Collections.emptyList());
            overlappingReservations.remove(reservationId);
            if (!overlappingReservations.isEmpty()) {
                throw new CarIsAlreadyReservedException("Car is already reserved for this date range");
            }
        }
        updateReservationDetails(existingReservation, reservationRequest);
        return reservationMapper.toReservationResponse(reservationRepository.save(existingReservation));
    }

    private void updateReservationDetails(Reservation reservation, ReservationRequest reservationRequest) {
        reservation.setRentalCar(rentalCarRepository.findById(reservationRequest.getRentalCarId())
                .orElseThrow(() -> new CarNotFoundException("Rental car not found.- UpdateReservation")));
        reservation.setUser(userRepository.findById(reservationRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found.- UpdateReservation")));
        reservation.setStartDate(reservationRequest.getStartDate());
        reservation.setReturnDate(reservationRequest.getReturnDate());
        reservation.setStatus(reservationRequest.getStatus());
    }


    public String deleteReservation(UUID id) {
        reservationRepository.deleteById(id);
        return "Reservation with id " + id + " deleted successfully";
    }

    public Boolean isCarAvailable(UUID rentalCarId , LocalDate startDate, LocalDate returnDate) {
        return reservationRepository.isCarReserved(rentalCarId,startDate,returnDate);
    }
}
