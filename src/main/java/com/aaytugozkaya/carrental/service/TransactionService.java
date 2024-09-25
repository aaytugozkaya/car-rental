package com.aaytugozkaya.carrental.service;

import com.aaytugozkaya.carrental.dto.request.TransactionRequest;
import com.aaytugozkaya.carrental.dto.response.TransactionResponse;
import com.aaytugozkaya.carrental.entity.RentalCar;
import com.aaytugozkaya.carrental.entity.Transaction;
import com.aaytugozkaya.carrental.entity.User;
import com.aaytugozkaya.carrental.entity.enums.CarStatus;
import com.aaytugozkaya.carrental.entity.enums.Status;
import com.aaytugozkaya.carrental.exception.*;
import com.aaytugozkaya.carrental.mapper.TransactionMapper;
import com.aaytugozkaya.carrental.repository.TransactionRepository;
import com.aaytugozkaya.carrental.repository.RentalCarRepository;
import com.aaytugozkaya.carrental.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final RentalCarRepository rentalCarRepository;
    private final UserRepository userRepository;
    private static final Logger logger = LogManager.getLogger(TransactionService.class);

    // Get all transactions
    public List<TransactionResponse> getTransactions() {
        return transactionRepository.findAll().stream()
                .map(transactionMapper::toTransactionResponse)
                .collect(Collectors.toList());
    }

    // Get a single transaction by ID
    public TransactionResponse getTransactionById(UUID id) {
        return transactionMapper.toTransactionResponse(transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found")));
    }


    public TransactionResponse saveTransaction(TransactionRequest transactionRequest) {

        if (!transactionRequest.getStartDate().isAfter(transactionRequest.getReturnDate())) {
            RentalCar rentalCar = rentalCarRepository.findById(transactionRequest.getRentalCarId())
                    .orElseThrow(() -> new CarNotFoundException("Rental car not found - SaveTransaction"));
            if (!transactionRepository.isCarRented(transactionRequest.getRentalCarId(), transactionRequest.getStartDate(), transactionRequest.getReturnDate(), Status.ACTIVE) && rentalCar.getStatus().equals(CarStatus.AVAILABLE)) {
                Transaction transaction = transactionMapper.toTransaction(transactionRequest);
                User user = userRepository.findById(transactionRequest.getUserId())
                        .orElseThrow(() -> new UserNotFoundException("User not found - SaveTransaction"));
                if(user.getBalance().compareTo(transaction.getTotalPrice()) != 1) {
                    throw new BalanceIsNotEnoughException("Not enough balance");
                }
                user.setBalance(user.getBalance().subtract(transaction.getTotalPrice()));
                logger.info("User balance updated after transaction: " + user.getBalance());
                logger.info("Transaction saved with id: " + transaction.getId());
                userRepository.save(user);
                return transactionMapper.toTransactionResponse(transactionRepository.save(transaction));
            } else {
                throw new CarIsAlreadyRentedException("Car is already rented for this date range");
            }
        } else {
            throw new ReturnDateIsNotValidException("Return date must be after start date");
        }
    }

    public TransactionResponse updateTransaction(UUID id, TransactionRequest transactionRequest) {
        Transaction existingTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));

        boolean datesChanged = !existingTransaction.getStartDate().equals(transactionRequest.getStartDate())
                || !existingTransaction.getReturnDate().equals(transactionRequest.getReturnDate());

        if (datesChanged) {

            if (transactionRequest.getStartDate().isAfter(transactionRequest.getReturnDate())) {
                throw new ReturnDateIsNotValidException("Return date must be after start date");
            }

            List<UUID> overlappingTransactions = transactionRepository.findOverlappingTransaction(
                    transactionRequest.getRentalCarId(),
                    transactionRequest.getStartDate(),
                    transactionRequest.getReturnDate(),
                    Status.ACTIVE
            );

            overlappingTransactions.remove(id);

            if (!overlappingTransactions.isEmpty()) {
                throw new CarIsAlreadyRentedException("Car is already rented for this date range");
            }
        }
        RentalCar rentalCar = rentalCarRepository.findById(transactionRequest.getRentalCarId())
                .orElseThrow(() -> new CarNotFoundException("Rental car not found - SaveTransaction"));
        if(rentalCar.getStatus().equals(CarStatus.AVAILABLE)){
            updateTransactionDetails(existingTransaction, transactionRequest,rentalCar);
        }



        return transactionMapper.toTransactionResponse(transactionRepository.save(existingTransaction));
    }

    private void updateTransactionDetails(Transaction transaction, TransactionRequest transactionRequest,RentalCar rentalCar) {
        Long daysBetweenOldTransaction = (ChronoUnit.DAYS.between(transaction.getStartDate(), transaction.getReturnDate()));
        Long daysBetweenNewTransaction = (ChronoUnit.DAYS.between(transactionRequest.getStartDate(), transactionRequest.getReturnDate()));
        User user = userRepository.findById(transaction.getUser().getId())
                .orElseThrow(() -> new UserNotFoundException("User not found - UpdateTransaction"));
        if((user.getBalance().add(transaction.getTotalPrice())).compareTo(rentalCar.getDailyRentingPrice().multiply(BigDecimal.valueOf(daysBetweenNewTransaction))) != 1) {
            throw new BalanceIsNotEnoughException("Not enough balance");
        }
        if(daysBetweenOldTransaction > daysBetweenNewTransaction){
            user.setBalance(user.getBalance().add(rentalCar.getDailyRentingPrice().multiply(BigDecimal.valueOf(daysBetweenOldTransaction - daysBetweenNewTransaction))));
        }else{
            user.setBalance(user.getBalance().subtract(rentalCar.getDailyRentingPrice().multiply(BigDecimal.valueOf(daysBetweenOldTransaction - daysBetweenNewTransaction))));
        }
        transaction.setRentalCar(rentalCarRepository.findById(transactionRequest.getRentalCarId())
                .orElseThrow(() -> new CarNotFoundException("Rental car not found - UpdateTransaction")));
        transaction.setUser(userRepository.findById(transactionRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found - UpdateTransaction")));
        transaction.setStartDate(transactionRequest.getStartDate());
        transaction.setReturnDate(transactionRequest.getReturnDate());
        transaction.setStatus(transactionRequest.getStatus());
        transaction.setTotalPrice(BigDecimal.valueOf(daysBetweenNewTransaction).multiply(transaction.getRentalCar().getDailyRentingPrice()));
        userRepository.save(user);
    }

    // Delete a transaction
    public String deleteTransaction(UUID id) {
        try {

            Transaction transaction = transactionRepository.findById(id)
                    .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));
            User user = userRepository.findById(transaction.getUser().getId())
                    .orElseThrow(() -> new UserNotFoundException("User not found - DeleteTransaction"));
            if(LocalDate.now().isAfter(transaction.getStartDate())){
               throw new TransactionIsAlreadyStartedException("Transaction is already started.Please update your transaction instead of deleting it.");
            }
            user.setBalance(user.getBalance().add(transaction.getTotalPrice()));
            transactionRepository.deleteById(id);
            return "Transaction deleted";
        } catch (Exception e) {
            throw new TransactionNotFoundException("Transaction not found");
        }
    }

    public List<TransactionResponse> getTransactionsByUserId(UUID userId) {
        return transactionRepository.findByUserId(userId).stream()
                .map(transactionMapper::toTransactionResponse)
                .collect(Collectors.toList());
    }

    public List<TransactionResponse> getTransactionsByCarId(UUID carId) {
        return transactionRepository.findByRentalCarId(carId).stream()
                .map(transactionMapper::toTransactionResponse)
                .collect(Collectors.toList());
    }

    public List<TransactionResponse> getTransactionsByUserIdAndCarId(UUID userId, UUID carId) {
        return transactionRepository.findByUserIdAndRentalCarId(userId, carId).stream()
                .map(transactionMapper::toTransactionResponse)
                .collect(Collectors.toList());
    }

    public List<TransactionResponse> getActiveTransactions() {
        return transactionRepository.findByStatus(Status.ACTIVE).stream()
                .map(transactionMapper::toTransactionResponse)
                .collect(Collectors.toList());
    }

    public String returnCar(UUID id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));
        if(transaction.getStatus().equals(Status.ACTIVE)){
            transaction.setStatus(Status.DONE);
            transaction.setIsReturned(true);
            transactionRepository.save(transaction);
            return "Car returned with id: " + id + " successfully";
        }else{
            throw new CarIsNotRentedException("Car is not rented");
        }
    }
}
