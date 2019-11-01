package com.es.phoneshop.CustomExceptions;

public class CustomNoSuchElementException extends Exception {
    public CustomNoSuchElementException() {
        super("No such product in list");
    }
}
