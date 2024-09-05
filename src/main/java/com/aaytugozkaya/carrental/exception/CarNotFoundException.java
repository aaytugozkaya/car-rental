package com.aaytugozkaya.carrental.exception;

public class CarNotFoundException extends RuntimeException {
    public CarNotFoundException(String s) {
        super(s);
    }
}