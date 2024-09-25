package com.aaytugozkaya.carrental.controller;

import com.aaytugozkaya.carrental.dto.request.TransactionRequest;
import com.aaytugozkaya.carrental.dto.response.TransactionResponse;
import com.aaytugozkaya.carrental.entity.Transaction;
import com.aaytugozkaya.carrental.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger logger = LogManager.getLogger(TransactionController.class);
    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getTransactions(){
        logger.info("All transactions are listed");
        return ResponseEntity.ok(transactionService.getTransactions());
    }
    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable UUID id){
        logger.info("Transaction with id: " + id + " is listed");
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }
    @GetMapping("/active")
    public ResponseEntity<List<TransactionResponse>> getActiveTransactions(){
        return ResponseEntity.ok(transactionService.getActiveTransactions());
    }
    @PostMapping
    public ResponseEntity<TransactionResponse> saveTransaction(@RequestBody TransactionRequest transactionRequest){
        TransactionResponse response = transactionService.saveTransaction(transactionRequest);
        logger.info("Transaction saving attempt with id : {}", response.getId());
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponse> updateTransaction(@PathVariable UUID id, @RequestBody TransactionRequest transactionRequest){
        logger.info("Transaction with id: {} is updated", id);
        return ResponseEntity.ok(transactionService.updateTransaction(id, transactionRequest));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable UUID id){
        logger.info("Transaction with id: {} is deleted", id);
        return ResponseEntity.ok(transactionService.deleteTransaction(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByUserId(@PathVariable UUID userId){
        logger.info("Transactions of user with id: "+userId+" are listed" );
        return ResponseEntity.ok(transactionService.getTransactionsByUserId(userId));
    }

    @PostMapping("/return/{id}")
    public ResponseEntity<String> returnCar(@PathVariable UUID id){
        logger.info("Car with id: {} is returned", id);
        return ResponseEntity.ok(transactionService.returnCar(id));
    }

}
