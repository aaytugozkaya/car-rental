package com.aaytugozkaya.carrental.service;

import com.aaytugozkaya.carrental.dto.request.TransactionRequest;
import com.aaytugozkaya.carrental.dto.response.TransactionResponse;
import com.aaytugozkaya.carrental.exception.TransactionNotFoundException;
import com.aaytugozkaya.carrental.mapper.TransactionMapper;
import com.aaytugozkaya.carrental.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public List<TransactionResponse> getTransactions() {
        return transactionRepository.findAll().stream()
                .map(transactionMapper::toTransactionResponse)
                .collect(Collectors.toList());
    }
    public TransactionResponse getTransactionById(UUID id) {
        return transactionMapper.toTransactionResponse(transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found")));
    }
    public TransactionResponse saveTransaction(TransactionRequest transactionRequest) {
        return transactionMapper.toTransactionResponse(transactionRepository.save(transactionMapper.toTransaction(transactionRequest)));
    }
    public TransactionResponse updateTransaction(UUID id, TransactionRequest transactionRequest) {
        return transactionMapper.toTransactionResponse(transactionRepository.save(transactionMapper.toTransaction(transactionRequest)));
    }
    public String deleteTransaction(UUID id) {
        try {
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

}
