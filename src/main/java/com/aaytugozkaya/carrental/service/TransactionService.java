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

    public List<TransactionResponse> getTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(transactionMapper::toTransactionResponse)
                .collect(Collectors.toList());
    }

    public TransactionResponse getTransactionById(UUID id) {
        return transactionRepository.findById(id)
                .map(transactionMapper::toTransactionResponse)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));
    }

    public TransactionResponse saveTransaction(TransactionRequest transactionRequest) {
        validateDates(transactionRequest.getStartDate(), transactionRequest.getReturnDate());

        RentalCar rentalCar = findAvailableRentalCar(transactionRequest.getRentalCarId());
        checkCarAvailability(transactionRequest, rentalCar);

        Transaction transaction = transactionMapper.toTransaction(transactionRequest);
        User user = findUser(transactionRequest.getUserId());

        if (user.getBalance().compareTo(transaction.getTotalPrice()) < 0) {
            throw new BalanceIsNotEnoughException("Not enough balance");
        }

        user.setBalance(user.getBalance().subtract(transaction.getTotalPrice()));
        userRepository.save(user);

        logger.info("User balance updated after transaction: {}", user.getBalance());
        logger.info("Transaction saved with id: {}", transaction.getId());

        return transactionMapper.toTransactionResponse(transactionRepository.save(transaction));
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
                    ).stream()
                    .filter(tId -> !tId.equals(existingTransaction.getId()))
                    .collect(Collectors.toList());

            if (!overlappingTransactions.isEmpty()) {
                throw new CarIsAlreadyRentedException("Car is already rented for this date range");
            }
        }

        RentalCar rentalCar = rentalCarRepository.findById(transactionRequest.getRentalCarId())
                .orElseThrow(() -> new CarNotFoundException("Rental car not found - SaveTransaction"));

        if (rentalCar.getStatus().equals(CarStatus.AVAILABLE)) {
            updateTransactionDetails(existingTransaction, transactionRequest, rentalCar);
        }

        return transactionMapper.toTransactionResponse(transactionRepository.save(existingTransaction));
    }


    public String deleteTransaction(UUID id) {
        Transaction transaction = findTransactionById(id);
        User user = findUser(transaction.getUser().getId());

        if (LocalDate.now().isAfter(transaction.getStartDate())) {
            throw new TransactionIsAlreadyStartedException("Transaction has already started. Please update it instead.");
        }

        user.setBalance(user.getBalance().add(transaction.getTotalPrice()));
        transactionRepository.deleteById(id);

        return "Transaction deleted";
    }

    public List<TransactionResponse> getTransactionsByUserId(UUID userId) {
        return transactionRepository.findByUserId(userId)
                .stream()
                .map(transactionMapper::toTransactionResponse)
                .collect(Collectors.toList());
    }

    public List<TransactionResponse> getActiveTransactions() {
        return transactionRepository.findByStatus(Status.ACTIVE)
                .stream()
                .map(transactionMapper::toTransactionResponse)
                .collect(Collectors.toList());
    }

    public String returnCar(UUID id) {
        Transaction transaction = findTransactionById(id);

        if (transaction.getStatus() != Status.ACTIVE) {
            throw new CarIsNotRentedException("Car is not rented");
        }

        transaction.setStatus(Status.DONE);
        transaction.setIsReturned(true);
        transactionRepository.save(transaction);

        return "Car returned with id: " + id + " successfully";
    }

    private void validateDates(LocalDate startDate, LocalDate returnDate) {
        if (!startDate.isBefore(returnDate)) {
            throw new ReturnDateIsNotValidException("Return date must be after start date");
        }
    }

    private RentalCar findAvailableRentalCar(UUID rentalCarId) {
        return rentalCarRepository.findById(rentalCarId)
                .filter(car -> car.getStatus().equals(CarStatus.AVAILABLE))
                .orElseThrow(() -> new CarNotFoundException("Rental car not available"));
    }

    private User findUser(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private Transaction findTransactionById(UUID id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));
    }

    private boolean datesChanged(Transaction existingTransaction, TransactionRequest transactionRequest) {
        return !existingTransaction.getStartDate().equals(transactionRequest.getStartDate()) ||
                !existingTransaction.getReturnDate().equals(transactionRequest.getReturnDate());
    }

    private void checkCarAvailability(TransactionRequest transactionRequest, RentalCar rentalCar) {
        if (transactionRepository.isCarRented(transactionRequest.getRentalCarId(), transactionRequest.getStartDate(), transactionRequest.getReturnDate(), Status.ACTIVE)) {
            throw new CarIsAlreadyRentedException("Car is already rented for this date range");
        }
    }

    private void updateTransactionDetails(Transaction transaction, TransactionRequest transactionRequest, RentalCar rentalCar) {
        long daysBetweenOld = ChronoUnit.DAYS.between(transaction.getStartDate(), transaction.getReturnDate());
        long daysBetweenNew = ChronoUnit.DAYS.between(transactionRequest.getStartDate(), transactionRequest.getReturnDate());
        User user = findUser(transaction.getUser().getId());

        BigDecimal rentalPriceDifference = rentalCar.getDailyRentingPrice().multiply(BigDecimal.valueOf(daysBetweenOld - daysBetweenNew));

        if (daysBetweenOld > daysBetweenNew) {
            user.setBalance(user.getBalance().add(rentalPriceDifference));
        } else if (user.getBalance().compareTo(rentalPriceDifference.negate()) < 0) {
            throw new BalanceIsNotEnoughException("Not enough balance");
        } else {
            user.setBalance(user.getBalance().subtract(rentalPriceDifference.negate()));
        }

        transaction.setRentalCar(rentalCar);
        transaction.setUser(user);
        transaction.setStartDate(transactionRequest.getStartDate());
        transaction.setReturnDate(transactionRequest.getReturnDate());
        transaction.setStatus(transactionRequest.getStatus());
        transaction.setTotalPrice(BigDecimal.valueOf(daysBetweenNew).multiply(rentalCar.getDailyRentingPrice()));

        userRepository.save(user);
    }
}
