package com.abhishek.marsrover.model;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.abhishek.marsrover.data.Coordinate;
import com.abhishek.marsrover.data.Direction;
import com.abhishek.marsrover.data.Position;
import com.abhishek.marsrover.exceptions.InvalidInputException;
import com.abhishek.marsrover.exceptions.WrongCommandException;

public class RoverTest {

    private static final char LEFT_COMMAND = 'L';
    private static final char RIGHT_COMMAND = 'R';
    private static final char MOVE_COMMAND = 'M';
    private static final char INVALID_COMMAND = '?';
    private Coordinate xCoordinate;
    private Coordinate yCoordinate;
    private Position position;
    private Direction direction;
    private Rover rover;
    private IMocksControl ctrl;

    @Before
    public void setup() {
    	ctrl = EasyMock.createControl();
    	xCoordinate = ctrl.createMock(Coordinate.class);
    	yCoordinate = ctrl.createMock(Coordinate.class);
    	// enums are stateless and mutable, so no need to mock.
        direction = Direction.N;
        // since direction is a real object, lets create rover
        // with real position too.
        position = new Position(xCoordinate, yCoordinate);
        rover = new Rover(position, direction);
    }

    @After
    public void verify() {
        ctrl.verify();
    }

    @Test
    public void testGetPosition_returnsCurrentPosition() {
        ctrl.replay();
        assertEquals(rover.getPosition(), position);
    }

    @Test
    public void testGetDirection_returnsCurrentDirection() {
        ctrl.replay();
        assertEquals(rover.getDirection(), direction);
    }

    @Test(expected=WrongCommandException.class)
    public void testExecute_withInvalidCommand_throwsException()
        throws InvalidInputException, WrongCommandException
    {
        ctrl.replay();
    	rover.execute(INVALID_COMMAND);
    }

    @Test
    public void testExecute_withLeftTurnCommand_turnsLeft()
        throws InvalidInputException, WrongCommandException
    {
        ctrl.replay();
        rover.execute(LEFT_COMMAND);
        Direction dir = rover.getDirection();
        assertEquals(dir, Direction.W);
    }

    @Test
    public void testExecute_withRightTurnCommand_turnsRight()
        throws InvalidInputException, WrongCommandException
    {
        ctrl.replay();
        rover.execute(RIGHT_COMMAND);
        Direction dir = rover.getDirection();
        assertEquals(dir, Direction.E);
    }

    @Test
    public void testExecute_withMoveCommandAndNorthHeading_incrementsYCoordinate()
        throws InvalidInputException, WrongCommandException
    {
        yCoordinate.up();
        EasyMock.expectLastCall();
        ctrl.replay();

        // current direction is north
        rover.execute(MOVE_COMMAND);
    }

    @Test
    public void testExecute_withMoveCommandAndSouthHeading_decrementsYCoordinate()
        throws InvalidInputException, WrongCommandException
    {
        yCoordinate.down();
        EasyMock.expectLastCall();
        ctrl.replay();

        // turn left twice to make rover head south
        rover.execute(LEFT_COMMAND);
        rover.execute(LEFT_COMMAND);
        rover.execute(MOVE_COMMAND);
    }

    @Test
    public void testExecute_withMoveCommandAndEastHeading_incrementsXCoordinate()
        throws InvalidInputException, WrongCommandException
    {
        xCoordinate.up();
        EasyMock.expectLastCall();
        ctrl.replay();

        // turn right to make rover head east
        rover.execute(RIGHT_COMMAND);
        rover.execute(MOVE_COMMAND);
    }

    @Test
    public void testExecute_withMoveCommandAndWestHeading_decrementsXCoordinate()
        throws InvalidInputException, WrongCommandException
    {
        xCoordinate.down();
        EasyMock.expectLastCall();
        ctrl.replay();

        // turn left to make rover head west
        rover.execute(LEFT_COMMAND);
        rover.execute(MOVE_COMMAND);
    }
}
