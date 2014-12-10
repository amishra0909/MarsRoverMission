package com.abhishek.marsrover.data;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.abhishek.marsrover.exceptions.InvalidInputException;

public class DirectionTest {

    private static final String DIR_NORTH = "N";
    private static final String DIR_EAST = "E";
    private static final String DIR_SOUTH = "S";
    private static final String DIR_WEST = "W";
    private static final String SOME_RANDOM_STRING = "SomeRandomString";
    private Direction direction;

    @Before
    public void setup() {
        // always start test with north direction.
        direction = Direction.N;
    }

    @Test
    public void testGetName_returnsCharacterRepresentationOfDirection() {
        assertThat(direction.name(), is(equalTo(DIR_NORTH)));
    }

    @Test
    public void testTurnRight_returnsDirectionToTheRightOfCurrent() {
        direction = direction.turnRight();
        assertThat(direction, is(equalTo(Direction.E)));
        direction = direction.turnRight();
        assertThat(direction, is(equalTo(Direction.S)));
        direction = direction.turnRight();
        assertThat(direction, is(equalTo(Direction.W)));
        direction = direction.turnRight();
        assertThat(direction, is(equalTo(Direction.N)));
    }

    @Test
    public void testTurnLeft_returnsDirectionToTheLeftOfCurrent() {
        direction = direction.turnLeft();
        assertThat(direction, is(equalTo(Direction.W)));
        direction = direction.turnLeft();
        assertThat(direction, is(equalTo(Direction.S)));
        direction = direction.turnLeft();
        assertThat(direction, is(equalTo(Direction.E)));
        direction = direction.turnLeft();
        assertThat(direction, is(equalTo(Direction.N)));
    }

    @Test
    public void testGetDirectionFromString_returnsDirectionRepresentedByString()
        throws InvalidInputException
    {
        direction = Direction.getDirectionFromString(DIR_NORTH);
        assertThat(direction, is(equalTo(Direction.N)));
        direction = Direction.getDirectionFromString(DIR_EAST);
        assertThat(direction, is(equalTo(Direction.E)));
        direction = Direction.getDirectionFromString(DIR_SOUTH);
        assertThat(direction, is(equalTo(Direction.S)));
        direction = Direction.getDirectionFromString(DIR_WEST);
        assertThat(direction, is(equalTo(Direction.W)));
    }

    @Test(expected=InvalidInputException.class)
    public void testGetDirectionFromString_withInvalidDirectionString_throwsException()
        throws InvalidInputException
    {
        Direction.getDirectionFromString(SOME_RANDOM_STRING);
    }
}
