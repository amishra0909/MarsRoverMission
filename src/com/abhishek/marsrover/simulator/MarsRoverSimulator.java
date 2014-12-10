package com.abhishek.marsrover.simulator;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.abhishek.marsrover.data.Coordinate;
import com.abhishek.marsrover.data.Direction;
import com.abhishek.marsrover.data.Position;
import com.abhishek.marsrover.exceptions.InvalidInputException;
import com.abhishek.marsrover.exceptions.RoverCollisionException;
import com.abhishek.marsrover.exceptions.RoverFallAndCrashException;
import com.abhishek.marsrover.exceptions.WrongCommandException;
import com.abhishek.marsrover.model.Plateau;
import com.abhishek.marsrover.model.Rover;

/*
 * Class to simulate the mars rover mission.
 * This class initializes plateau and rovers to
 * be placed on the plateau, and orders command
 * to them from input.
 */
public class MarsRoverSimulator {

    /*
     * Regex to split string.
     */
    private static final String REGEX = "\\s+";

    /*
     * Buffered input stream to read the input.
     */
    private BufferedReader input;

    /*
     * Out stream to show the output.
     */
    private PrintStream output;

    /*
     * Plateau on mars.
     */
    private Plateau plateau;

    /*
     * Map of rovers on the plateau and their command string.
     * Since rovers process commands sequentially, order of
     * retrival of entries should be same as order of insertion.
     * So LinkedHashMap implementation is used.
     */
    Map<Rover, String> roverCommandMap = new LinkedHashMap<>();

    /*
     * Public constructor
     */
    public MarsRoverSimulator(BufferedReader input, PrintStream output) {
    	this.input = input;
        this.output = output;
    }

    /*
     * Public function to initialize models from input.
     */
    public void initialize() throws InvalidInputException,
        RoverCollisionException, RoverFallAndCrashException
    {
        try {
            // read first line and initialize plateau.
        	String firstLine = input.readLine();
        	createAndInitializePlateau(firstLine);

        	//create and initialize rovers from next set of lines
        	int roverCount = 1; // used to indicate which rover has wrong inputs.
        	String roverLine = input.readLine();
        	while(roverLine != null && roverLine.length() > 0) {
        	    // read and check the validity of command for this rover
        	    String commandLine = input.readLine();
        	    createRoverAndAddToMap(roverLine, commandLine, roverCount);
        	    roverLine = input.readLine();
        	    roverCount++;
        	}
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * Public function to make rovers to execute command.
     */
    public void processRoverCommands() throws WrongCommandException,
        InvalidInputException, RoverFallAndCrashException, RoverCollisionException
    {
        for (Entry<Rover, String> entry : roverCommandMap.entrySet()) {
            Rover rover = entry.getKey();
            String commandLine = entry.getValue();
            for (int i = 0; i < commandLine.length(); i++) {
                char command = commandLine.charAt(i);
                Position oldPosition = rover.getPosition().clone();
                rover.execute(command);
                Position newPosition = rover.getPosition();

                // check the validity of new position and update
                // unavailable positions in plateau if rover moved.
                if (command == 'M') {
                    checkValidityOfRoverPosition(newPosition);
                    plateau.updateUnavailablePosition(oldPosition, newPosition);
                }
            }
        }
    }

    /*
     * Function to print out to output stream.
     */
    public void printOutput() {
        for (Entry<Rover, String> entry : roverCommandMap.entrySet()) {
            Rover rover = entry.getKey();
            output.println(rover);
        }
    }

    /*
     * Private function to create plateau from string input.
     */
    private void createAndInitializePlateau(String plateauLine) throws InvalidInputException {
        if (plateauLine == null || plateauLine.length() == 0) {
            throw new InvalidInputException("Line to define plateau is null or empty.");
        }

        String[] maxCoordinateValues = plateauLine.split(REGEX);
        if (maxCoordinateValues.length != 2) {
            throw new InvalidInputException("Invalid values of upper right coordinates: " +
                                             plateauLine);
        }

        try {
        	// create max position from first two values in the input string.
            int maxXValue = Integer.parseInt(maxCoordinateValues[0]);
            int maxYValue = Integer.parseInt(maxCoordinateValues[1]);
            Position maxPosition = new Position(new Coordinate(maxXValue),
                                                new Coordinate(maxYValue));

            // create min position from (0, 0).
            Position minPosition = new Position(new Coordinate(0),
                                                new Coordinate(0));

            // construct plateau
            plateau = new Plateau(minPosition, maxPosition);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid values of upper right coordinates. " +
                                            "Error message: " + e.getMessage());
        }
    }

    /*
     * Private function to create rover and add entries to rover-command map.
     */
    private void createRoverAndAddToMap(String roverLine, String commandLine, int roverIndex)
        throws InvalidInputException, RoverCollisionException, RoverFallAndCrashException
    {
        if (plateau == null) {
            throw new RuntimeException("Plateau should be initialized before adding rovers.");
        }

        // crate rover from input line.
        Rover rover = createRoverFromString(roverLine, roverIndex);

        // check if rover position is valid or not.
        checkValidityOfRoverPosition(rover.getPosition());

        // check validity of command line.
        if (commandLine == null || commandLine.contains(" ")) {
            throw new InvalidInputException("Invalid commands for rover#" +
                                             roverIndex + ". Command: " + commandLine);
        }
        roverCommandMap.put(rover, commandLine);
        plateau.addUnavailablePosition(rover.getPosition());
    }

    /*
     * Private function to create rover from input string.
     */
    private Rover createRoverFromString(String roverLine, int roverIndex)
        throws InvalidInputException
    {
        String[] roverCoordinatesAndDirection = roverLine.split(REGEX);
        if (roverCoordinatesAndDirection.length != 3) {
            throw new InvalidInputException("Invalid information to initialize rover #" +
                                            roverIndex + ". Info provided: " + roverLine);
        }

        try {
            // create position from first two values in input string.
            int x = Integer.parseInt(roverCoordinatesAndDirection[0]);
            int y = Integer.parseInt(roverCoordinatesAndDirection[1]);
            Position position = new Position(new Coordinate(x), new Coordinate(y));

            // get direction from third value in input string.
            Direction direction = Direction.getDirectionFromString(roverCoordinatesAndDirection[2]);

            // create new rover and return
            Rover rover = new Rover(position, direction);
            return rover;
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Rover position is not valid for rover #" +
                                            roverIndex + ". Error message: " + e.getMessage());
        }
    }

    /*
     * Private function to check validity of rover position.
     */
    private void checkValidityOfRoverPosition(Position position)
        throws RoverCollisionException, RoverFallAndCrashException
    {
        if (!plateau.isPositionReachable(position)) {
            throw new RoverFallAndCrashException("Rover fell off the plateau. " +
                                                  "Rover's new position:" + position +
                                                  ", Plateau : " + plateau);
        }
        if (!plateau.isPositionAvailable(position)) {
            throw new RoverCollisionException("Rover collided with another rover. " +
                                              "Position: " + position);
        }
    }

    /*
     * Main function.
     * The input is read from a file named "input.dat".
     */
    public static void main(String[] args) {
    	try {
            // assume input is provide in text file "input.dat"
    		FileInputStream inputStream = new FileInputStream("input.dat");
    		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    		MarsRoverSimulator simulator = new MarsRoverSimulator(reader, System.out);
    		simulator.initialize();
    		simulator.processRoverCommands();
    		simulator.printOutput();
    	} catch (Exception e) {
    	    System.out.println(e.getMessage());
    	    e.printStackTrace();
    	}
    }

}
