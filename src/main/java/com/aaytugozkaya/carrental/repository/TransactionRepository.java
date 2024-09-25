package com.aaytugozkaya.carrental.repository;

import com.aaytugozkaya.carrental.entity.Transaction;
import com.aaytugozkaya.carrental.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    public List<Transaction> findByUserId(UUID userId);
    public List<Transaction> findByRentalCarId(UUID rentalCarId);
    public List<Transaction> findByUserIdAndRentalCarId(UUID userId, UUID rentalCarId);

    @Query("SELECT COUNT(t) > 0 FROM Transaction t " +
            "WHERE t.rentalCar.id = :rentalCarId " +
            "AND t.startDate <= :returnDate " +
            "AND t.returnDate >= :startDate " +
            "AND t.status = :status")
    boolean isCarRented(@Param("rentalCarId") UUID rentalCarId,
                        @Param("startDate") LocalDate startDate,
                        @Param("returnDate") LocalDate returnDate,
                        @Param("status") Status status);

    @Query("SELECT t.id FROM Transaction t " +
            "WHERE t.rentalCar.id = :rentalCarId " +
            "AND t.startDate <= :returnDate " +
            "AND t.returnDate >= :startDate " +
            "AND t.status = :status")
    List<UUID> findOverlappingTransaction(@Param("rentalCarId") UUID rentalCarId,
                                          @Param("startDate") LocalDate startDate,
                                          @Param("returnDate") LocalDate returnDate,
                                          @Param("status") Status status);


    List<Transaction> findByStatus(Status status);
}

