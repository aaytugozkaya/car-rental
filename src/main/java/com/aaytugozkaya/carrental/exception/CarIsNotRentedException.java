package com.aaytugozkaya.carrental.exception;

public class CarIsNotRentedException extends RuntimeException {
    public CarIsNotRentedException(String carIsNotRented) {
        super(carIsNotRented);
    }
}
