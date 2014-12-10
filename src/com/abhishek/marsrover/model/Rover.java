package com.abhishek.marsrover.model;

import com.abhishek.marsrover.data.Direction;
import com.abhishek.marsrover.data.Position;
import com.abhishek.marsrover.exceptions.InvalidInputException;
import com.abhishek.marsrover.exceptions.WrongCommandException;

/*
 * Class to represent Rover object.
 * A rover is defined by its position (x and y coordinates)
 * and its direction (north, east, south, west).
 */
public class Rover {

    /*
     * Position of the rover.
     */
    private Position position;

    /*
     * Direction of heading of the rover.
     */
    private Direction direction;

    /*
     * Public constructor.
     */
    public Rover(Position position, Direction direction) {
        this.position = position;
        this.direction = direction;
    }

    /*
     * Public function to get current position of rover.
     */
    public Position getPosition() {
        return position;
    }

    /*
     * Public function to get current direction of heading.
     */
    public Direction getDirection() {
        return direction;
    }

    /*
     * Public function to execute a command.
     */
    public void execute(char c) throws WrongCommandException, InvalidInputException {
        switch(c) {
        case 'L':
            direction = direction.turnLeft();
            break;
        case 'R':
            direction = direction.turnRight();
            break;
        case 'M':
            position.move(direction);
            break;
        default:
            throw new WrongCommandException("Wrong command : " + c);
        }
    }

    @Override
    public String toString() {
        return position.toString() + " " + direction.name();
    }
}
