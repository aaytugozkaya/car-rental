package com.aaytugozkaya.carrental.controller;

import com.aaytugozkaya.carrental.dto.request.ReservationRequest;
import com.aaytugozkaya.carrental.dto.response.ReservationResponse;
import com.aaytugozkaya.carrental.entity.Reservation;
import com.aaytugozkaya.carrental.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.aaytugozkaya.carrental.utils.AppConstant.BASE_URL;

@RestController
@RequestMapping(BASE_URL + "/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/{rentalCarId}")
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable UUID rentalCarId) {
        return ResponseEntity.ok(reservationService.getReservationById(rentalCarId));
    }

    @PostMapping
    public ReservationResponse createReservation(@RequestBody ReservationRequest reservation) {
        return reservationService.saveReservation(reservation);
    }

    @PutMapping("/{reservationId}")
    public ResponseEntity<ReservationResponse> updateReservation(@PathVariable UUID reservationId, @RequestBody ReservationRequest updatedReservation) {
        return ResponseEntity.ok(reservationService.updateReservation(reservationId, updatedReservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable UUID rentalCarId) {
        return ResponseEntity.ok(reservationService.deleteReservation(rentalCarId));
    }

    @GetMapping("/reservation/{rentalCarId}/{startDate}/{returnDate}")
    public ResponseEntity<Boolean> isCarAvailable(@PathVariable UUID rentalCarId, @PathVariable LocalDate startDate, @PathVariable LocalDate returnDate) {
        return ResponseEntity.ok(reservationService.isCarAvailable(rentalCarId,startDate,returnDate));
    }
}
