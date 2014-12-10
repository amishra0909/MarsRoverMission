package com.abhishek.marsrover.model;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertThat;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.abhishek.marsrover.data.Coordinate;
import com.abhishek.marsrover.data.Position;

public class PlateauTest {

    private Position minPosition;
    private Position maxPosition;
    private Position prevPosition;
    private Position nextPosition;
    private Coordinate xCoordinate;
    private Coordinate yCoordinate;
    private Coordinate maxXCoordinate;
    private Coordinate maxYCoordinate;
    private Coordinate minXCoordinate;
    private Coordinate minYCoordinate;
    private Plateau plateau;
    private IMocksControl ctrl;

	@Before
    public void setup() {
        ctrl = EasyMock.createControl();
        minPosition = ctrl.createMock(Position.class);
        maxPosition = ctrl.createMock(Position.class);
        prevPosition = ctrl.createMock(Position.class);
        nextPosition = ctrl.createMock(Position.class);
        xCoordinate = ctrl.createMock(Coordinate.class);
        yCoordinate = ctrl.createMock(Coordinate.class);
        maxXCoordinate = ctrl.createMock(Coordinate.class);
        maxYCoordinate = ctrl.createMock(Coordinate.class);
        minXCoordinate = ctrl.createMock(Coordinate.class);
        minYCoordinate = ctrl.createMock(Coordinate.class);
        plateau = new Plateau(minPosition, maxPosition);
    }

    @After
    public void verify() {
        ctrl.verify();
    }

    @Test
    public void testAddUnavailablePosition_positionBecomesUnavailable() {
    	EasyMock.expect(prevPosition.clone()).andReturn(prevPosition);
        ctrl.replay();

        plateau.addUnavailablePosition(prevPosition);
        assertThat(plateau.isPositionAvailable(prevPosition), is(false));
    }

    @Test
    public void testUpdateUnavailablePosition_makesPrevPositionAvailable_makesNewPositionUnavailable() {
        EasyMock.expect(prevPosition.clone()).andReturn(prevPosition);
        EasyMock.expect(nextPosition.clone()).andReturn(nextPosition);

        ctrl.replay();

        plateau.addUnavailablePosition(prevPosition);
        assertThat(plateau.isPositionAvailable(prevPosition), is(false));

        plateau.updateUnavailablePosition(prevPosition, nextPosition);
        assertThat(plateau.isPositionAvailable(prevPosition), is(true));
        assertThat(plateau.isPositionAvailable(nextPosition), is(false));
    }

    @Test(expected=RuntimeException.class)
    public void testUpdateUnavailablePosition_withPrevPositionNotPresent_throwsException() {
        ctrl.replay();
        plateau.updateUnavailablePosition(prevPosition, nextPosition);
    }

    @Test
    public void testIsAvailable_withUnavailablePosition_returnsFalse() {
    	EasyMock.expect(prevPosition.clone()).andReturn(prevPosition);
        ctrl.replay();

        plateau.addUnavailablePosition(prevPosition);
        assertThat(plateau.isPositionAvailable(prevPosition), is(false));
    }

    @Test
    public void testIsAvailable_withAvailablePosition_returnsTrue() {
        EasyMock.expect(prevPosition.clone()).andReturn(prevPosition);
    	ctrl.replay();

        plateau.addUnavailablePosition(prevPosition);
        assertThat(plateau.isPositionAvailable(nextPosition), is(true));
    }

    @Test
    public void testIsReachable_withXPositionBelowMinValue_returnsFalse() {
        EasyMock.expect(nextPosition.getXCoordinate()).andReturn(xCoordinate);
        EasyMock.expect(maxPosition.getXCoordinate()).andReturn(maxXCoordinate);
        EasyMock.expect(xCoordinate.compareTo(EasyMock.anyObject(Coordinate.class))).andReturn(-1);
        EasyMock.expect(nextPosition.getYCoordinate()).andReturn(yCoordinate);
        EasyMock.expect(maxPosition.getYCoordinate()).andReturn(maxYCoordinate);
        EasyMock.expect(yCoordinate.compareTo(EasyMock.anyObject(Coordinate.class))).andReturn(-1);
        EasyMock.expect(nextPosition.getXCoordinate()).andReturn(xCoordinate);
        EasyMock.expect(minPosition.getXCoordinate()).andReturn(minXCoordinate);
        EasyMock.expect(xCoordinate.compareTo(EasyMock.anyObject(Coordinate.class))).andReturn(-1);
        ctrl.replay();

        assertThat(plateau.isPositionReachable(nextPosition), is(false));
    }

    @Test
    public void testIsReachable_withYPositionBelowMinValue_returnsFalse() {
        EasyMock.expect(nextPosition.getXCoordinate()).andReturn(xCoordinate);
        EasyMock.expect(maxPosition.getXCoordinate()).andReturn(maxXCoordinate);
        EasyMock.expect(xCoordinate.compareTo(EasyMock.anyObject(Coordinate.class))).andReturn(-1);
        EasyMock.expect(nextPosition.getYCoordinate()).andReturn(yCoordinate);
        EasyMock.expect(maxPosition.getYCoordinate()).andReturn(maxYCoordinate);
        EasyMock.expect(yCoordinate.compareTo(EasyMock.anyObject(Coordinate.class))).andReturn(-1);
        EasyMock.expect(nextPosition.getXCoordinate()).andReturn(xCoordinate);
        EasyMock.expect(minPosition.getXCoordinate()).andReturn(minXCoordinate);
        EasyMock.expect(xCoordinate.compareTo(EasyMock.anyObject(Coordinate.class))).andReturn(1);
        EasyMock.expect(nextPosition.getYCoordinate()).andReturn(yCoordinate);
        EasyMock.expect(minPosition.getYCoordinate()).andReturn(minYCoordinate);
        EasyMock.expect(yCoordinate.compareTo(EasyMock.anyObject(Coordinate.class))).andReturn(-1);
        ctrl.replay();

        assertThat(plateau.isPositionReachable(nextPosition), is(false));
    }

    @Test
    public void testIsReachable_withXPositionAboveMaxValue_returnsFalse() {
        EasyMock.expect(nextPosition.getXCoordinate()).andReturn(xCoordinate);
        EasyMock.expect(maxPosition.getXCoordinate()).andReturn(maxXCoordinate);
        EasyMock.expect(xCoordinate.compareTo(EasyMock.anyObject(Coordinate.class))).andReturn(1);
        ctrl.replay();

        assertThat(plateau.isPositionReachable(nextPosition), is(false));
    }

    @Test
    public void testIsReachable_withYPositionAboveMaxValue_returnsFalse() {
        EasyMock.expect(nextPosition.getXCoordinate()).andReturn(xCoordinate);
        EasyMock.expect(maxPosition.getXCoordinate()).andReturn(maxXCoordinate);
        EasyMock.expect(xCoordinate.compareTo(EasyMock.anyObject(Coordinate.class))).andReturn(-1);
        EasyMock.expect(nextPosition.getYCoordinate()).andReturn(yCoordinate);
        EasyMock.expect(maxPosition.getYCoordinate()).andReturn(maxYCoordinate);
        EasyMock.expect(yCoordinate.compareTo(EasyMock.anyObject(Coordinate.class))).andReturn(1);
        ctrl.replay();

        assertThat(plateau.isPositionReachable(nextPosition), is(false));
    }

    @Test
    public void testIsReachable_withPositionBetweenMaxAndMinPositions_returnsTrue() {
        EasyMock.expect(nextPosition.getXCoordinate()).andReturn(xCoordinate);
        EasyMock.expect(maxPosition.getXCoordinate()).andReturn(maxXCoordinate);
        EasyMock.expect(xCoordinate.compareTo(EasyMock.anyObject(Coordinate.class))).andReturn(-1);
        EasyMock.expect(nextPosition.getYCoordinate()).andReturn(yCoordinate);
        EasyMock.expect(maxPosition.getYCoordinate()).andReturn(maxYCoordinate);
        EasyMock.expect(yCoordinate.compareTo(EasyMock.anyObject(Coordinate.class))).andReturn(-1);
        EasyMock.expect(nextPosition.getXCoordinate()).andReturn(xCoordinate);
        EasyMock.expect(minPosition.getXCoordinate()).andReturn(minXCoordinate);
        EasyMock.expect(xCoordinate.compareTo(EasyMock.anyObject(Coordinate.class))).andReturn(1);
        EasyMock.expect(nextPosition.getYCoordinate()).andReturn(yCoordinate);
        EasyMock.expect(minPosition.getYCoordinate()).andReturn(minYCoordinate);
        EasyMock.expect(yCoordinate.compareTo(EasyMock.anyObject(Coordinate.class))).andReturn(1);
        ctrl.replay();

        assertThat(plateau.isPositionReachable(nextPosition), is(true));
    }
}
