package com.aaytugozkaya.carrental.mapper;

import com.aaytugozkaya.carrental.dto.request.TransactionRequest;
import com.aaytugozkaya.carrental.dto.response.TransactionResponse;
import com.aaytugozkaya.carrental.entity.RentalCar;
import com.aaytugozkaya.carrental.entity.Transaction;
import com.aaytugozkaya.carrental.entity.User;
import com.aaytugozkaya.carrental.repository.RentalCarRepository;
import com.aaytugozkaya.carrental.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TransactionMapper {
    private final RentalCarRepository rentalCarRepository;
    private final UserRepository userRepository;

    public Transaction toTransaction(TransactionRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        RentalCar rentalCar = rentalCarRepository.findById(request.getRentalCarId())
                .orElseThrow(() -> new RuntimeException("Rental car not found"));

        return Transaction.builder()
                .user(user)
                .rentalCar(rentalCar)
                .transactionDate(request.getTransactionDate())
                .borrowingDate(LocalDate.now().plusDays(1))
                .returnDate(request.getReturnDate())
                .location(request.getLocation())
                .totalPrice(request.getTotalPrice())
                .isReturned(false)
                .build();
    }

    public TransactionResponse toTransactionResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .user(transaction.getUser())
                .rentalCar(transaction.getRentalCar())
                .transactionDate(transaction.getTransactionDate())
                .borrowingDate(transaction.getBorrowingDate())
                .returnDate(transaction.getReturnDate())
                .location(transaction.getLocation())
                .totalPrice(transaction.getTotalPrice())
                .isReturned(transaction.getIsReturned())
                .build();
    }
}
