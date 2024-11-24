package com.robot;

import com.robot.domain.*;
import com.robot.repository.RobotRepository;
import com.robot.service.SimulationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
/**
 * Main application class for the Robot Simulator.
 * Provides interactive command-line interface for robot movement simulation.
 */

public class RobotSimulatorApp {
    private static final Logger log = LoggerFactory.getLogger(RobotSimulatorApp.class);

    /**
     * Entry point of the application.
     * Initializes the simulation environment and manages user interaction.
     */
    public static void main(String[] args) {
        System.out.println("Starting Robot Simulator v1.0");
        printWelcomeMessage();

        try (Scanner scanner = new Scanner(System.in)) {
            Room room = setupRoom(scanner);
            RobotRepository repository = new RobotRepository();
            SimulationService service = new SimulationService(repository, room);
            runSimulation(scanner, repository, service);
        }
        System.out.println("Simulation ended successfully");
    }

    /**
     * Sets up the simulation room based on user input.
     */
    private static Room setupRoom(Scanner scanner) {
        System.out.println("\nSTEP 1: Room Setup");
        System.out.println("Enter room dimensions (width height), e.g., '5 5':");
        int width = scanner.nextInt();
        int height = scanner.nextInt();
        Room room = new Room(width, height);
        log.debug("Room created: {}x{}", width, height);
        System.out.println("Room created with dimensions: " + width + "x" + height);
        return room;
    }

    /**
     * Main simulation loop handling user commands.
     */
    private static void runSimulation(Scanner scanner, RobotRepository repository, SimulationService service) {
        System.out.println("\n📋 How many robots would you like to add? (1-3): ");
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                int numRobots = Integer.parseInt(input);

                if (numRobots >= 1 && numRobots <= 3) {
                    for (int i = 1; i <= numRobots; i++) {
                        System.out.println("\n🤖 Setting up Robot" + i + " of " + numRobots);
                        handleRobotSimulation(scanner, repository, service, i);
                    }
                    break;
                } else {
                    System.out.println("\n📌 Please enter a number between 1 and 3");
                }
            } catch (NumberFormatException e) {
                System.out.println("\n📌 Please enter a valid number (1-3)");
            }
        }
    }

    /**
     * Handles the creation and command execution for a single robot.
     */
    private static void handleRobotSimulation(Scanner scanner, RobotRepository repository, SimulationService service, int robotNumber) {
        try {
            Robot robot = createRobot(scanner, robotNumber);
            repository.save(robot);
            String commands = getRobotCommands(scanner, robotNumber);
            executeRobotCommands(service, robot, commands);
        } catch (IllegalArgumentException e) {
            System.out.println("\n❌ Invalid input for Robot" + robotNumber + ". Please try again.");
            log.debug("Robot" + robotNumber + " creation failed: {}", e.getMessage());
        }
    }

    /**
     * Creates a new robot based on user input.
     */
    private static Robot createRobot(Scanner scanner, int robotNumber) {
        while (true) {
            try {
                System.out.println("\n📍 Robot" + robotNumber + " - Enter position and direction:");
                System.out.println("Format: 'x y direction' (Example: '2 3 N')");
                System.out.println("• x and y: numbers between 0 and room size");
                System.out.println("• direction: N (North), E (East), S (South), W (West)");
                System.out.print("\nRobot" + robotNumber + " position: ");

                String[] inputs = scanner.nextLine().trim().split("\\s+");
                if (inputs.length != 3) {
                    System.out.println("\n🔍 Robot" + robotNumber + " needs three values. Example: '2 3 N'");
                    continue;
                }
                int x = Integer.parseInt(inputs[0]);
                int y = Integer.parseInt(inputs[1]);
                Direction direction = Direction.fromSymbol(inputs[2].charAt(0));
                System.out.println("Creating Robot" + robotNumber + " at position: " + x + ", " + y + ", " + direction);
                return new Robot(new Position(x, y), direction);
            } catch (NumberFormatException e) {
                System.out.println("\n🔍 Robot" + robotNumber + " position needs numbers. Example: '2 3 N'");
                log.debug("Invalid position format for Robot{}: {}", robotNumber, e.getMessage());
            }
        }
    }

    /**
     * Gets movement commands from user input.
     */
    private static String getRobotCommands(Scanner scanner, int robotNumber) {
        while (true) {
            System.out.println("\n🤖 Robot" + robotNumber + " - Enter movement commands:");
            System.out.println("Available commands:");
            System.out.println("• L - Turn Left");
            System.out.println("• R - Turn Right");
            System.out.println("• F - Move Forward");
            System.out.println("\nExamples:");
            System.out.println("• 'RFRF' - Move in a square");
            System.out.println("• 'FFLL' - Move forward twice, turn around");
            System.out.print("\nRobot" + robotNumber + " commands: ");

            String commands = scanner.nextLine().toUpperCase();
            log.debug("Received commands for Robot{}: {}", robotNumber, commands);

            if (commands.matches("[LRF]+")) {
                return commands;
            }

            System.out.println("\n🔍 Invalid command for Robot" + robotNumber + "! Please use only L, R, or F");
            System.out.println("Examples: 'LRF', 'FFRL', 'RFRFRF'");
        }
    }

    /**
     * Executes the movement commands for a robot.
     */
    private static void executeRobotCommands(SimulationService service, Robot robot, String commands) {
        try {
            service.executeCommands(robot, commands);
            System.out.println("\n✅ Movement completed successfully!");
            System.out.println("📍 Position of the robot: " + robot);
            log.debug("Robot {} completed movement sequence", robot.getId());
        } catch (Exception e) {
            System.out.println("\n⚠️ Movement stopped - Robot is at safe position");
            log.debug("Movement execution failed: {}", e.getMessage());
        }
    }

    /**
     * Displays the welcome message and simulation capabilities.
     */
    private static void printWelcomeMessage() {
        System.out.println("=================================");
        System.out.println("Welcome to Robot Simulator v1.0");
        System.out.println("=================================");
        System.out.println("This simulator allows you to:");
        System.out.println("- Create a room with custom dimensions");
        System.out.println("- Place multiple robots in the room");
        System.out.println("- Control robots using simple commands");
        System.out.println("- Avoid collisions and boundary violations");
        System.out.println("=================================");
    }
}
