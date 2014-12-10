package com.abhishek.marsrover.exceptions;

/*
 * Class to represent exception when rover collide with
 * something already present at a given position.
 */
public class RoverCollisionException extends Exception {

    // default serialVersionUID to suppress warning.
    private static final long serialVersionUID = 1L;

    public RoverCollisionException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoverCollisionException(String message) {
        super(message);
    }

    public RoverCollisionException() {
        super();
    }
}
