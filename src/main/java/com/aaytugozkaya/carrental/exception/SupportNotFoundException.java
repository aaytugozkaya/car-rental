package com.aaytugozkaya.carrental.exception;

public class SupportNotFoundException extends RuntimeException {
    public SupportNotFoundException(String supportNotFound) {
        super(supportNotFound);
    }
}
