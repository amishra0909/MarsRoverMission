package com.abhishek.marsrover.exceptions;

/*
 * Class to represent exception when rover falls off the plateau.
 * Rover falls off when initial position or position after move
 * command is not between minimum and maximum positions of plateau.
 */
public class RoverFallAndCrashException extends Exception {

    // default serialVersionUID to suppress warning
    private static final long serialVersionUID = 1L;

    public RoverFallAndCrashException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoverFallAndCrashException(String message) {
        super(message);
    }

    public RoverFallAndCrashException() {
        super();
    }
}
