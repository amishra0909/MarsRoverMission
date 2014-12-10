package com.abhishek.marsrover.model;

import java.util.ArrayList;
import java.util.List;

import com.abhishek.marsrover.data.Position;

/*
 * Class to represent plateau model.
 * It contains maximum position reachable in the plateau,
 * minimum position reachable in the plateau and list of
 * positions unavailable. Unavailability is determined by
 * the fact of something being already present at a position. 
 */
public class Plateau {

    /*
     * Top right position that can reached in the plateau.
     */
    private Position maxPosition;

    /*
     * Bottom left position that can be reached in the plateau.
     */
    private Position minPosition;

    /*
     * List of positions which are unavailable in the plateau.
     * A position is unavailable when something is already
     * present in the position. In the context of the current
     * problem it is always a Rover.
     */
    private List<Position> unavailablePositions;

    /*
     * Public constructor
     */
    public Plateau(Position minPosition, Position maxPosition) {
        this.minPosition = minPosition;
        this.maxPosition = maxPosition;
        unavailablePositions = new ArrayList<>();
    }

    /*
     * Public function to add a position to the list of
     * unavailable positions.
     */
    public void addUnavailablePosition(Position position) {
        unavailablePositions.add(position.clone());
    }

    /*
     * Public function to update a position in the list
     * of unavailable positions.
     */
    public void updateUnavailablePosition(Position prevPosition, Position newPosition) {
        if (!unavailablePositions.contains(prevPosition)) {
            throw new RuntimeException("Invalid state of plateau. " +
                                       "Previous  is not present in unavailable position list. " +
                                       "Previous position: " + prevPosition);
        }
        unavailablePositions.remove(prevPosition);
        unavailablePositions.add(newPosition.clone());
    }

    /*
     * Public function to check if a given position is available in plateau.
     * A position is unavailable is something is already present in that
     * position.
     */
    public boolean isPositionAvailable(Position position) {
        for (Position unavailablePosition : unavailablePositions) {
            if (unavailablePosition.equals(position)) {
                return false;
            }
        }
        return true;
    }

    /*
     * Public function to check is a given position is reachable in plateau.
     * A position is reachable if its coordinates lie between coordinates of
     * maximum and minimum positions in the plateau.
     */
    public boolean isPositionReachable(Position position) {
        if (position.getXCoordinate().compareTo(maxPosition.getXCoordinate()) > 0 ||
            position.getYCoordinate().compareTo(maxPosition.getYCoordinate()) > 0 ||
            position.getXCoordinate().compareTo(minPosition.getXCoordinate()) < 0 ||
            position.getYCoordinate().compareTo(minPosition.getYCoordinate()) < 0)
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "[ (" + minPosition + ") ... (" + maxPosition + ") ]";
    }
}
