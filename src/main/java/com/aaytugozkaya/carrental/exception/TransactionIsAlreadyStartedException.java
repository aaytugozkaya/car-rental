package com.aaytugozkaya.carrental.exception;

public class TransactionIsAlreadyStartedException extends RuntimeException {
    public TransactionIsAlreadyStartedException(String transactionIsAlreadyStarted) {
        super(transactionIsAlreadyStarted);
    }
}
