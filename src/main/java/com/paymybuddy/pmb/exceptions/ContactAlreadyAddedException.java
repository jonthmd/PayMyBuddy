package com.paymybuddy.pmb.exceptions;

public class ContactAlreadyAddedException extends RuntimeException {
    public ContactAlreadyAddedException(String message) {
        super(message);
    }
}
