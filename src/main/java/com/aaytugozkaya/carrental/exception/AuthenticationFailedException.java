package com.aaytugozkaya.carrental.exception;

public class AuthenticationFailedException extends RuntimeException {
    public AuthenticationFailedException(String invalidEmailOrPassword) {
        super(invalidEmailOrPassword);
    }
}
