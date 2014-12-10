package com.abhishek.marsrover.simulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.abhishek.marsrover.exceptions.InvalidInputException;
import com.abhishek.marsrover.exceptions.RoverCollisionException;
import com.abhishek.marsrover.exceptions.RoverFallAndCrashException;
import com.abhishek.marsrover.exceptions.WrongCommandException;

public class MarsRoverSimulatorTest {

    private static final String VALID_FIRSTLINE = "5 5";
    private static final String VALID_ROVERLINE_ONE = "1 2 N";
    private static final String VALID_COMMANDLINE_ONE = "LRM";
    private static final String VALID_ROVERLINE_TWO = "2 4 S";
    private static final String VALID_COMMANDLINE_TWO = "MRL";
    private static final String INVALID_FIRSTLINE_LENGTH_ONE = "1";
    private static final String INVALID_FIRSTLINE_LENGTH_ZERO = "";
    private static final String INVALID_FIRSTLINE_NON_NUM_FIRST_CHAR = "a 1";
    private static final String INVALID_FIRSTLINE_NON_NUM_SECOND_CHAR = "1 b";
    private static final String INVALID_ROVERLINE_LENGTH_NOT_THREE = "1";
    private static final String INVALID_ROVERLINE_NON_NUM_FIRST_CHAR = "a 2 N";
    private static final String INVALID_ROVERLINE_NON_NUM_SECOND_CHAR = "1 b N";
    private static final String INVALID_ROVERLINE_INVALID_DIRECTION_CHAR = "1 2 X";
    private static final String VALID_ROVERLINE_BUT_POSITION_OUT_OF_PLATEAU = "6 6 N";
    private static final String VALID_ROVERLINE_TWO_BUT_SAME_COORDINATE_AS_ONE = "1 2 S";
    private static final String INVALID_COMMANDLINE_SPACE = "M R";
    private static final String INVALID_COMMANDLINE = "MRLX";
    private static final String VALID_COMMANDLINE__ONE_MAKING_FINAL_POSITION_OUT_OF_PLATEAU = "LRMMMM";
    private static final String VALID_COMMANDLINE_TWO_WITH_COLLIDING_POSITION = "MRM";

    private BufferedReader input;
    private PrintStream output;
    private IOException readlineException;
    private MarsRoverSimulator simulator;
    private IMocksControl ctrl;

    @Before
    public void setup() {
        ctrl = EasyMock.createControl();
        input = ctrl.createMock(BufferedReader.class);
        output = ctrl.createMock(PrintStream.class);
        readlineException = new IOException();
        simulator = new MarsRoverSimulator(input, output);
    }

    @After
    public void verify() {
        ctrl.verify();
    }

    @Test(expected=RuntimeException.class)
    public void testInitialize_whenUnableToReadFirstLine_throwsException()
        throws IOException, InvalidInputException, RoverCollisionException, RoverFallAndCrashException
    {
        EasyMock.expect(input.readLine()).andThrow(readlineException);
        ctrl.replay();

        simulator.initialize();
    }

    @Test(expected=InvalidInputException.class)
    public void testInitialize_withFirstLineNull_throwsException()
        throws IOException, InvalidInputException, RoverCollisionException, RoverFallAndCrashException
    {
        EasyMock.expect(input.readLine()).andReturn(null);
        ctrl.replay();

        simulator.initialize();
    }

    @Test(expected=InvalidInputException.class)
    public void testInitialize_withFirstLineSizeZero_throwsException()
        throws IOException, InvalidInputException, RoverCollisionException, RoverFallAndCrashException
    {
        EasyMock.expect(input.readLine()).andReturn(INVALID_FIRSTLINE_LENGTH_ZERO);
        ctrl.replay();

        simulator.initialize();
    }

    @Test(expected=InvalidInputException.class)
    public void testInitialize_withFirstLineWithoutTwoCoordinates_throwsException()
        throws IOException, InvalidInputException, RoverCollisionException, RoverFallAndCrashException
    {
        EasyMock.expect(input.readLine()).andReturn(INVALID_FIRSTLINE_LENGTH_ONE);
        ctrl.replay();

        simulator.initialize();
    }

    @Test(expected=InvalidInputException.class)
    public void testInitialize_withFirstLineWithNonNumberFirstCharacter_throwsException()
        throws IOException, InvalidInputException, RoverCollisionException, RoverFallAndCrashException
    {
        EasyMock.expect(input.readLine()).andReturn(INVALID_FIRSTLINE_NON_NUM_FIRST_CHAR);
        ctrl.replay();

        simulator.initialize();
    }

    @Test(expected=InvalidInputException.class)
    public void testInitialize_withFirstLineWithNonNumberSecondCharacter_throwsException()
        throws IOException, InvalidInputException, RoverCollisionException, RoverFallAndCrashException
    {
        EasyMock.expect(input.readLine()).andReturn(INVALID_FIRSTLINE_NON_NUM_SECOND_CHAR);
        ctrl.replay();

        simulator.initialize();
    }

    @Test(expected=RuntimeException.class)
    public void testInitialize_whenUnableToReadRoverLine__initializesPlateau_andThrowsException()
        throws IOException, InvalidInputException, RoverCollisionException, RoverFallAndCrashException
    {
        EasyMock.expect(input.readLine()).andReturn(VALID_FIRSTLINE);
        EasyMock.expect(input.readLine()).andThrow(readlineException);
        ctrl.replay();

        simulator.initialize();
    }

    @Test(expected=RuntimeException.class)
    public void testInitialize_whenUnableToReadCommandLine_initializedPlateau_andThrowsException()
        throws IOException, InvalidInputException, RoverCollisionException, RoverFallAndCrashException
    {
        EasyMock.expect(input.readLine()).andReturn(VALID_FIRSTLINE);
        EasyMock.expect(input.readLine()).andReturn(VALID_ROVERLINE_ONE);
        EasyMock.expect(input.readLine()).andThrow(readlineException);
        ctrl.replay();

        simulator.initialize();
    }

    @Test(expected=InvalidInputException.class)
    public void testInitialize_withRoverLineWithoutThreeNonSpaceCharacters_throwsException()
        throws IOException, InvalidInputException, RoverCollisionException, RoverFallAndCrashException
    {
        EasyMock.expect(input.readLine()).andReturn(VALID_FIRSTLINE);
        EasyMock.expect(input.readLine()).andReturn(INVALID_ROVERLINE_LENGTH_NOT_THREE);
        EasyMock.expect(input.readLine()).andReturn(VALID_COMMANDLINE_ONE);
        ctrl.replay();

        simulator.initialize();
    }

    @Test(expected=InvalidInputException.class)
    public void testInitialize_withRoverLineWithNonNumFirstChararter_throwsException()
        throws IOException, InvalidInputException, RoverCollisionException, RoverFallAndCrashException
    {
        EasyMock.expect(input.readLine()).andReturn(VALID_FIRSTLINE);
        EasyMock.expect(input.readLine()).andReturn(INVALID_ROVERLINE_NON_NUM_FIRST_CHAR);
        EasyMock.expect(input.readLine()).andReturn(VALID_COMMANDLINE_ONE);
        ctrl.replay();

        simulator.initialize();
    }

    @Test(expected=InvalidInputException.class)
    public void testInitialize_withRoverLineWithNonNumSecondCharacter_throwsException()
        throws IOException, InvalidInputException, RoverCollisionException, RoverFallAndCrashException
    {
        EasyMock.expect(input.readLine()).andReturn(VALID_FIRSTLINE);
        EasyMock.expect(input.readLine()).andReturn(INVALID_ROVERLINE_NON_NUM_SECOND_CHAR);
        EasyMock.expect(input.readLine()).andReturn(VALID_COMMANDLINE_ONE);
        ctrl.replay();

        simulator.initialize();
    }

    @Test(expected=InvalidInputException.class)
    public void testInitialize_withRoverLineWithInvalidDirectionCharacter_throwsException()
        throws IOException, InvalidInputException, RoverCollisionException, RoverFallAndCrashException
    {
        EasyMock.expect(input.readLine()).andReturn(VALID_FIRSTLINE);
        EasyMock.expect(input.readLine()).andReturn(INVALID_ROVERLINE_INVALID_DIRECTION_CHAR);
        EasyMock.expect(input.readLine()).andReturn(VALID_COMMANDLINE_ONE);
        ctrl.replay();

        simulator.initialize();
    }

    @Test(expected=RoverFallAndCrashException.class)
    public void testInitialize_withRoverLineContainingPositionOutOfPlateau_throwsException()
        throws IOException, InvalidInputException, RoverCollisionException, RoverFallAndCrashException
    {
        EasyMock.expect(input.readLine()).andReturn(VALID_FIRSTLINE);
        EasyMock.expect(input.readLine()).andReturn(VALID_ROVERLINE_BUT_POSITION_OUT_OF_PLATEAU);
        EasyMock.expect(input.readLine()).andReturn(VALID_COMMANDLINE_ONE);
        ctrl.replay();

        simulator.initialize();
    }

    @Test(expected=RoverCollisionException.class)
    public void testInitialize_withRoverLineContainingAlreadyAcquiredPositionOfPlateau_throwsException()
        throws IOException, InvalidInputException, RoverCollisionException, RoverFallAndCrashException
    {
        EasyMock.expect(input.readLine()).andReturn(VALID_FIRSTLINE);
        EasyMock.expect(input.readLine()).andReturn(VALID_ROVERLINE_ONE);
        EasyMock.expect(input.readLine()).andReturn(VALID_COMMANDLINE_ONE);
        EasyMock.expect(input.readLine()).andReturn(VALID_ROVERLINE_TWO_BUT_SAME_COORDINATE_AS_ONE);
        EasyMock.expect(input.readLine()).andReturn(VALID_COMMANDLINE_TWO);
        ctrl.replay();

        simulator.initialize();
    }

    @Test(expected=InvalidInputException.class)
    public void testInitialize_withCommandLineAsNull_throwsException()
        throws IOException, InvalidInputException, RoverCollisionException, RoverFallAndCrashException
    {
        EasyMock.expect(input.readLine()).andReturn(VALID_FIRSTLINE);
        EasyMock.expect(input.readLine()).andReturn(VALID_ROVERLINE_ONE);
        EasyMock.expect(input.readLine()).andReturn(null);
        ctrl.replay();

        simulator.initialize();
    }

    @Test(expected=InvalidInputException.class)
    public void testInitialize_withCommandLineContainingSpace_throwsException()
        throws IOException, InvalidInputException, RoverCollisionException, RoverFallAndCrashException
    {
        EasyMock.expect(input.readLine()).andReturn(VALID_FIRSTLINE);
        EasyMock.expect(input.readLine()).andReturn(VALID_ROVERLINE_ONE);
        EasyMock.expect(input.readLine()).andReturn(INVALID_COMMANDLINE_SPACE);
        ctrl.replay();

        simulator.initialize();
    }

    @Test
    public void testInitialize_withValidFirstLineRoverLinesAndCommandLines_initializedSuccessfully()
        throws IOException, InvalidInputException, RoverCollisionException, RoverFallAndCrashException
    {
        EasyMock.expect(input.readLine()).andReturn(VALID_FIRSTLINE);
        EasyMock.expect(input.readLine()).andReturn(VALID_ROVERLINE_ONE);
        EasyMock.expect(input.readLine()).andReturn(VALID_COMMANDLINE_ONE);
        EasyMock.expect(input.readLine()).andReturn(VALID_ROVERLINE_TWO);
        EasyMock.expect(input.readLine()).andReturn(VALID_COMMANDLINE_TWO);
        EasyMock.expect(input.readLine()).andReturn(null);
        ctrl.replay();

        simulator.initialize();
    }

    @Test(expected=WrongCommandException.class)
    public void testProcessRoverCommands_withInvalidCommandInCommandLine_throwsException()
        throws IOException, InvalidInputException, RoverCollisionException, RoverFallAndCrashException, WrongCommandException
    {
        EasyMock.expect(input.readLine()).andReturn(VALID_FIRSTLINE);
        EasyMock.expect(input.readLine()).andReturn(VALID_ROVERLINE_ONE);
        EasyMock.expect(input.readLine()).andReturn(INVALID_COMMANDLINE);
        EasyMock.expect(input.readLine()).andReturn(null);
        ctrl.replay();

        simulator.initialize();
        simulator.processRoverCommands();
    }

    @Test(expected=RoverFallAndCrashException.class)
    public void testProcessRoverCommands_withValidCommandLineButRoverFinalPositionOutOfPlateau_throwsException()
        throws IOException, InvalidInputException, RoverCollisionException, RoverFallAndCrashException, WrongCommandException
    {
        EasyMock.expect(input.readLine()).andReturn(VALID_FIRSTLINE);
        EasyMock.expect(input.readLine()).andReturn(VALID_ROVERLINE_ONE);
        EasyMock.expect(input.readLine()).andReturn(VALID_COMMANDLINE__ONE_MAKING_FINAL_POSITION_OUT_OF_PLATEAU);
        EasyMock.expect(input.readLine()).andReturn(null);
        ctrl.replay();

        simulator.initialize();
        simulator.processRoverCommands();
    }

    @Test(expected=RoverCollisionException.class)
    public void testProcessRoverCommands_withValidCommandLineButTwoRoversColliding_throwsException()
        throws IOException, InvalidInputException, RoverCollisionException, RoverFallAndCrashException, WrongCommandException
    {
        EasyMock.expect(input.readLine()).andReturn(VALID_FIRSTLINE);
        EasyMock.expect(input.readLine()).andReturn(VALID_ROVERLINE_ONE);
        EasyMock.expect(input.readLine()).andReturn(VALID_COMMANDLINE_ONE);
        EasyMock.expect(input.readLine()).andReturn(VALID_ROVERLINE_TWO);
        EasyMock.expect(input.readLine()).andReturn(VALID_COMMANDLINE_TWO_WITH_COLLIDING_POSITION);
        EasyMock.expect(input.readLine()).andReturn(null);
        ctrl.replay();

        simulator.initialize();
        simulator.processRoverCommands();
    }

    @Test
    public void testProcessRoverCommands_withValidAndSafeCommandLines_processCommands()
        throws IOException, InvalidInputException, RoverCollisionException, RoverFallAndCrashException, WrongCommandException
    {
        EasyMock.expect(input.readLine()).andReturn(VALID_FIRSTLINE);
        EasyMock.expect(input.readLine()).andReturn(VALID_ROVERLINE_ONE);
        EasyMock.expect(input.readLine()).andReturn(VALID_COMMANDLINE_ONE);
        EasyMock.expect(input.readLine()).andReturn(VALID_ROVERLINE_TWO);
        EasyMock.expect(input.readLine()).andReturn(VALID_COMMANDLINE_TWO);
        EasyMock.expect(input.readLine()).andReturn(null);
        ctrl.replay();

        simulator.initialize();
        simulator.processRoverCommands();
    }
}
