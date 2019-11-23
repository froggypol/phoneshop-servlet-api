package com.es.phoneshop.custom.exceptions;

public class OutOfStockException extends NumberFormatException {
    public OutOfStockException() {
        super("No such product in list");
    }
}
