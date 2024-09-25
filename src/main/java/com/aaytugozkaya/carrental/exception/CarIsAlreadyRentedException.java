package com.aaytugozkaya.carrental.exception;

public class CarIsAlreadyRentedException extends RuntimeException {
    public CarIsAlreadyRentedException(String s) {
        super(s);
    }
}
