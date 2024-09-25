package com.aaytugozkaya.carrental.controller;

import com.aaytugozkaya.carrental.dto.request.ReservationRequest;
import com.aaytugozkaya.carrental.dto.response.ReservationResponse;
import com.aaytugozkaya.carrental.entity.Reservation;
import com.aaytugozkaya.carrental.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger logger = LogManager.getLogger(ReservationController.class);

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        logger.info("All reservations are listed");
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/{rentalCarId}")
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable UUID rentalCarId) {
        logger.info("Reservation with id: " + rentalCarId + " is listed");
        return ResponseEntity.ok(reservationService.getReservationById(rentalCarId));
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest reservation) {
        ReservationResponse response = reservationService.saveReservation(reservation);
        logger.info("Reservation created with id: %s " , response.getId().toString());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{reservationId}")
    public ResponseEntity<ReservationResponse> updateReservation(@PathVariable UUID reservationId, @RequestBody ReservationRequest updatedReservation) {
        logger.info("Reservation with id: %s is updated" , reservationId);
        return ResponseEntity.ok(reservationService.updateReservation(reservationId, updatedReservation));
    }

    @DeleteMapping("/{rentalCarId}")
    public ResponseEntity<String> deleteReservation(@PathVariable UUID rentalCarId) {
        logger.info("Reservation with id: %s is deleted" , rentalCarId);
        return ResponseEntity.ok(reservationService.deleteReservation(rentalCarId));
    }

    @GetMapping("/reservation/{rentalCarId}/{startDate}/{returnDate}")
    public ResponseEntity<Boolean> isCarAvailable(@PathVariable UUID rentalCarId, @PathVariable LocalDate startDate, @PathVariable LocalDate returnDate) {
        logger.info("Checking availability of car with id: %s between %s and %s" , rentalCarId ,startDate.toString() , returnDate.toString());
        return ResponseEntity.ok(reservationService.isCarAvailable(rentalCarId,startDate,returnDate));
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<String> cancelReservation(@PathVariable("id") UUID id) {
        logger.info("Reservation with id: %s is cancelled" , id);
        return ResponseEntity.ok(reservationService.cancelReservation(id));
    }
}
