package com.abhishek.marsrover.data;

/*
 * Class to represent Coordinate data.
 */
public class Coordinate implements Comparable<Coordinate> {

    /*
     * Value of the coordinate represented.
     */
    private int value;

    /*
     * Constructor
     */
    public Coordinate(int value) {
        this.value = value;
    }

    /*
     * Getter functions.
     */
    public int getValue() {
        return value;
    }

    /*
     * Function to increment the value when rover moves.
     */
    public void up() {
        value = value + 1;
    }

    /*
     * Function to decrement the value when rover moves.
     */
    public void down() {
        value = value - 1;
    }

    /*
     * Function to clone this object to a new Coordinate.
     */
    public Coordinate clone() {
        return new Coordinate(value);
    }

    /*
     * Function to compare this object with another Coordinate.
     * See java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Coordinate o) {
        return this.value - o.getValue();
    }

    /*
     * Function to check equality with another Coordinate.
     * See java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Coordinate) {
            return value == ((Coordinate)o).getValue();
        }
        return false;
    }

    /*
     * Function to stringify coordinate value.
     * See java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
