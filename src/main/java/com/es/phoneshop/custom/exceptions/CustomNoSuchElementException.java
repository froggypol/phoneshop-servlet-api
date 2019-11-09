package com.es.phoneshop.custom.exceptions;

public class CustomNoSuchElementException extends Exception {
    public CustomNoSuchElementException() {
        super("No such product in list");
    }
}
