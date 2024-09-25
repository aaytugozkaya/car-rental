package com.aaytugozkaya.carrental.exception;

public class BalanceIsNotEnoughException extends RuntimeException {
    public BalanceIsNotEnoughException(String notEnoughBalance) {
        super(notEnoughBalance);
    }
}
