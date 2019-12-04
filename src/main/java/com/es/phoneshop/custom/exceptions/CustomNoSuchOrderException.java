package com.es.phoneshop.custom.exceptions;

public class CustomNoSuchOrderException extends NullPointerException {
    public CustomNoSuchOrderException() {
        super("No such order in list");
    }
}
