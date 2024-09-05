package com.aaytugozkaya.carrental.exception;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(String s) {
        super(s);
    }
}
