package com.abhishek.marsrover.data;

import com.abhishek.marsrover.exceptions.InvalidInputException;

/*
 * Enumeration to represent the four directions which a rover can head.
 * Direction are enumerated in clockwise manner, so that logic of turning
 * can be simplified.
 */
public enum Direction {

    N(0),
    E(1),
    S(2),
    W(3);

    /*
     * Represent the numeric value of a direction.
     * NORTH: 0, EAST: 1, SOUTH: 2, WEST: 3
     */
    private int value;

    Direction(int value) {
        this.value = value;
    }

    /*
     * Function to turn left from the current direction.
     */
    public Direction turnLeft() {
        return values()[(this.value + 3) % 4];
    }

    /*
     * Function to turn right from the current direction.
     */
    public Direction turnRight() {
        return values()[(this.value + 1) % 4];
    }

    /*
     * A static function to return direction from the string
     * passed in the input. This function throws an exception 
     * when input string is invalid, i.e. input string is 
     * something other that N, E, S or W.
     */
    public static Direction getDirectionFromString(String dir)
        throws InvalidInputException
    {
        for (Direction direction : values()) {
            if (dir.equals(direction.name())) {
                return direction;
            }
        }
        throw new InvalidInputException("Unknown direction string : " + dir);
    }
}
