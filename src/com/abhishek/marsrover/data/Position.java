package com.abhishek.marsrover.data;

import com.abhishek.marsrover.exceptions.InvalidInputException;

/*
 * Class to represent one position (square) on the plateau.
 */
public class Position {

    /*
     * X Coordinate.
     */
    private Coordinate x;

    /*
     * Y Coordinate.
     */
    private Coordinate y;

    /*
     * Constructor.
     */
    public Position(Coordinate x, Coordinate y) {
        this.x = x;
        this.y = y;
    }

    /*
     * Function to get integer value of X Coordinate.
     */
    public Coordinate getXCoordinate() {
        return x;
    }

    /*
     * Function to get integer value of Y Coordinate.
     */
    public Coordinate getYCoordinate() {
        return y;
    }

    /*
     * Function to move in a given direction.
     * Depending upon the direction, x or y coordinate changes.
     */
    public void move(Direction direction) throws InvalidInputException {
        if (direction == null) {
            throw new InvalidInputException("direction of rover is invalid.");
        }
    	switch(direction) {
        case N:
            y.up();
            break;
        case E:
            x.up();
            break;
        case S:
        	y.down();
            break;
        case W:
        	x.down();
            break;
        }
    }

    /*
     * Function to clone a new Position from this object.
     */
    public Position clone() {
        // deep copy
    	return new Position(x.clone(), y.clone());
    }

    /*
     * Function to check equality with another Position object.
     * Positions are equal if values of respective coordinates
     * are equal.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Position) {
            return x.equals(((Position) o).getXCoordinate()) && 
                   y.equals(((Position) o).getYCoordinate());
        }
        return false;
    }

    @Override
    public String toString() {
        return x + " " + y;
    }
}
