package com.aaytugozkaya.carrental.repository;

import com.aaytugozkaya.carrental.entity.RentalCar;
import com.aaytugozkaya.carrental.entity.enums.CarLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface RentalCarRepository extends JpaRepository<RentalCar, UUID> {
    @Query("SELECT c FROM RentalCar c " +
            "WHERE c.location = :location " +
            "AND NOT EXISTS (SELECT r FROM Reservation r " +
            "WHERE r.rentalCar = c " +
            "AND ((r.startDate <= :endDate AND r.returnDate >= :startDate)))")
    List<RentalCar> findAvailableCars(@Param("location") CarLocation location,
                                      @Param("startDate") LocalDate startDate,
                                      @Param("endDate") LocalDate endDate);
}
