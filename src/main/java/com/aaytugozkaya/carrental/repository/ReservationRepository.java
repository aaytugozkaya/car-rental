package com.aaytugozkaya.carrental.repository;

import com.aaytugozkaya.carrental.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID>{
    @Query("SELECT COUNT(r) > 0 FROM Reservation r WHERE r.rentalCar.id = :rentalCarId AND ((r.startDate <= :endDate AND r.returnDate >= :startDate))")
    boolean isCarReserved(@Param("rentalCarId") UUID rentalCarId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

//    @Query("SELECT r.id FROM Reservation r WHERE r.rentalCar.id = :rentalCarId AND ((r.startDate < :endDate AND r.returnDate > :startDate))")
//    Optional<UUID> findOverlappingReservation(@Param("rentalCarId") UUID rentalCarId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT r.id FROM Reservation r WHERE r.rentalCar.id = :rentalCarId AND ((r.startDate < :endDate AND r.returnDate > :startDate))")
    Optional<List<UUID>> findOverlappingReservation(@Param("rentalCarId") UUID rentalCarId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


}
