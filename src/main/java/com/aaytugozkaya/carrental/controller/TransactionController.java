package com.aaytugozkaya.carrental.controller;

import com.aaytugozkaya.carrental.dto.request.TransactionRequest;
import com.aaytugozkaya.carrental.dto.response.TransactionResponse;
import com.aaytugozkaya.carrental.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.aaytugozkaya.carrental.utils.AppConstant.BASE_URL;

@RestController
@RequestMapping(BASE_URL + "/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getTransactions(){
        return ResponseEntity.ok(transactionService.getTransactions());
    }
    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable UUID id){
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }
    @PostMapping
    public ResponseEntity<TransactionResponse> saveTransaction(@RequestBody TransactionRequest transactionRequest){
        return ResponseEntity.ok(transactionService.saveTransaction(transactionRequest));
    }
    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponse> updateTransaction(@PathVariable UUID id, @RequestBody TransactionRequest transactionRequest){
        return ResponseEntity.ok(transactionService.updateTransaction(id, transactionRequest));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable UUID id){
        return ResponseEntity.ok(transactionService.deleteTransaction(id));
    }
}
