package com.es.phoneshop.custom.exceptions;

public class CustomNoSuchElementException extends NullPointerException {
    public CustomNoSuchElementException() {
        super("No such product in list");
    }
}
