package com.aaytugozkaya.carrental.exception;

public class CarIsAlreadyReservedException extends RuntimeException{
    public CarIsAlreadyReservedException(String s) {
        super(s);
    }
}
