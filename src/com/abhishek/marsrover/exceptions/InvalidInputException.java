package com.abhishek.marsrover.exceptions;

/*
 * Exception class to represent the invalid inputs.
 * An input is invalid, when inputs in string to initialize
 * models or data is either null or unexpected value.
 */
public class InvalidInputException extends Exception {

    // default serialVersionUID to suppress warning.
    private static final long serialVersionUID = 1L;

    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException() {
        super();
    }
}
