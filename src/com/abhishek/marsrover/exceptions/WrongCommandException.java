package com.abhishek.marsrover.exceptions;

/*
 * Class to represent exception when a command cannot be understood by rover.
 */
public class WrongCommandException extends Exception {

    // default serialVersionUID to suppress warning.
    private static final long serialVersionUID = 1L;

    public WrongCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongCommandException(String message) {
        super(message);
    }

    public WrongCommandException() {
        super();
    }
}
