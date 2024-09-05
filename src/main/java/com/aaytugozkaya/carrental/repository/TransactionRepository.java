package com.aaytugozkaya.carrental.repository;

import com.aaytugozkaya.carrental.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    public List<Transaction> findByUserId(UUID userId);
    public List<Transaction> findByRentalCarId(UUID rentalCarId);
    public List<Transaction> findByUserIdAndRentalCarId(UUID userId, UUID rentalCarId);
}
