package com.abhishek.marsrover.data;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class CoordinateTest {

    private Coordinate coordinate;
    private static final int VALUE = 1;
    private static final int BIGGER_VALUE = 2;
    private static final int SMALLER_VALUE = 0;

    @Before
    public void setup() {
        coordinate = new Coordinate(VALUE);
    }

    @Test
    public void testGetValue_returnsIntegerValue() {
        assertThat(coordinate.getValue(), is(equalTo(VALUE)));
    }

    @Test
    public void testUp_incrementsValueByOne() {
        coordinate.up();
        assertThat(coordinate.getValue(), is(equalTo(VALUE + 1)));
    }

    @Test
    public void testDown_decrementsValueByOne() {
        coordinate.down();
        assertThat(coordinate.getValue(), is(equalTo(VALUE - 1)));
    }

    @Test
    public void testClone_createsNewObjectWithSameValue() {
        Coordinate testCoordinate = coordinate.clone();
        assertThat(testCoordinate.getValue(), is(equalTo(coordinate.getValue())));
        assertThat(testCoordinate.hashCode(), is(not(equalTo(coordinate.hashCode()))));
    }
    @Test
    public void testCompareTo_withGreaterCoordinate_returnsNegativeInteger() {
        Coordinate testCoordinate = new Coordinate(BIGGER_VALUE);
        int comparision = coordinate.compareTo(testCoordinate);
        assertThat(comparision < 0, is(true));
    }

    @Test
    public void testCompareTo_withSmallerCoordinate_returnsPositiveInteger() {
        Coordinate testCoordinate = new Coordinate(SMALLER_VALUE);
        int comparision = coordinate.compareTo(testCoordinate);
        assertThat(comparision > 0, is(true));
    }

    @Test
    public void testCompareTo_withSameCoordinate_returnsZero() {
        Coordinate testCoordinate = new Coordinate(VALUE);
        assertThat(coordinate.compareTo(testCoordinate), is(equalTo(0)));
    }

    @Test
    public void testEquals_withSameCoordinateValue_returnsTrue() {
        Coordinate testCoordinate = new Coordinate(VALUE);
        assertThat(coordinate.equals(testCoordinate), is(true));
    }

    @Test
    public void testEquals_withDifferentCoordinateValue_returnsFalse() {
        Coordinate testCoordinate = new Coordinate(BIGGER_VALUE);
        assertThat(coordinate.equals(testCoordinate), is(false));
    }

    @Test
    public void testEquals_withDifferentObjectType_returnsFalse() {
        Object testObject = new Object();
        assertThat(coordinate.equals(testObject), is(false));
    }
}
