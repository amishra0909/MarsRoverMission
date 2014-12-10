package com.abhishek.marsrover.data;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import static org.junit.Assert.assertThat;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.abhishek.marsrover.exceptions.InvalidInputException;

public class PositionTest {

    private static final int XVALUE = 1;
    private static final int YVALUE = 1;
    private Coordinate xCoordinate;
    private Coordinate clonedXCoordinate;
    private Coordinate clonedYCoordinate;
    private Coordinate yCoordinate;
    private Position position;
    private IMocksControl ctrl;

    @Before
    public void setup() {
        ctrl = EasyMock.createControl();
        xCoordinate = ctrl.createMock(Coordinate.class);
        clonedXCoordinate = ctrl.createMock(Coordinate.class);
        yCoordinate = ctrl.createMock(Coordinate.class);
        clonedYCoordinate = ctrl.createMock(Coordinate.class);
        position = new Position(xCoordinate, yCoordinate);
    }

    @After
    public void verify() {
        ctrl.verify();
    }

    @Test
    public void testGetXCoordinate_returnsCoordinate() {
        ctrl.replay();
        assertThat(position.getXCoordinate(), is(xCoordinate));
    }

    @Test
    public void testGetYCoordinateValue_returnsYValue() {
        ctrl.replay();
        assertThat(position.getYCoordinate(), is(yCoordinate));
    }

    @Test
    public void testMove_towardsNorth_incrementsYValue() throws InvalidInputException {
        yCoordinate.up();
        EasyMock.expectLastCall();
        ctrl.replay();

        position.move(Direction.N);
    }

    @Test
    public void testMove_towardsSouth_decrementsYValue() throws InvalidInputException {
        yCoordinate.down();
        EasyMock.expectLastCall();
        ctrl.replay();

        position.move(Direction.S);
    }

    @Test
    public void testMove_towardsEast_incrementsXValue() throws InvalidInputException {
        xCoordinate.up();
        EasyMock.expectLastCall();
        ctrl.replay();

        position.move(Direction.E);
    }

    @Test
    public void testMove_towardsWest_decrementsXValue() throws InvalidInputException {
        xCoordinate.down();
        EasyMock.expectLastCall();
        ctrl.replay();

        position.move(Direction.W);
    }

    @Test(expected=InvalidInputException.class)
    public void testMove_withNullDirection_throwsException() throws InvalidInputException {
        ctrl.replay();
        position.move(null);
    }

    @Test
    public void testClone_returnsNewPositionObjectWithSameCoordinateValues() {
        EasyMock.expect(xCoordinate.clone()).andReturn(clonedXCoordinate);
        EasyMock.expect(yCoordinate.clone()).andReturn(clonedYCoordinate);
        EasyMock.expect(xCoordinate.getValue()).andReturn(XVALUE);
        EasyMock.expect(clonedXCoordinate.getValue()).andReturn(XVALUE);
        EasyMock.expect(yCoordinate.getValue()).andReturn(YVALUE);
        EasyMock.expect(clonedYCoordinate.getValue()).andReturn(YVALUE);
        ctrl.replay();

        Position clonedPosition = position.clone();
        assertThat(clonedPosition.hashCode(), is(not(equalTo(position.hashCode()))));
        assertThat(clonedPosition.getXCoordinate().getValue(), is(equalTo(position.getXCoordinate().getValue())));
        assertThat(clonedPosition.getYCoordinate().getValue(), is(equalTo(position.getYCoordinate().getValue())));
    }

    @Test
    public void testEquals_withSameValuesOfCoordinates_returnsTrue() {
        Position testPosition = new Position(xCoordinate, yCoordinate);
        ctrl.replay();

        assertThat(position.equals(testPosition), is(true));
    }

    @Test
    public void testEquals_withDifferentXCoordinates_returnsFalse() {
        Position testPosition = new Position(clonedXCoordinate, yCoordinate);
    	ctrl.replay();

        assertThat(position.equals(testPosition), is(false));
    }

    @Test
    public void testEquals_withDifferentYCoordinates_returnsFalse() {
        Position testPosition = new Position(xCoordinate, clonedYCoordinate);
        ctrl.replay();

        assertThat(position.equals(testPosition), is(false));
    }

    @Test
    public void testEquals_withNonCoordinateObject_returnsFalse() {
        Object testObject = new Object();
        ctrl.replay();

        assertThat(position.equals(testObject), is(false));
    }
}
