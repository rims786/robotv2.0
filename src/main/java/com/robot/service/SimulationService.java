package com.robot.service;

import com.robot.domain.Direction;
import com.robot.domain.Position;
import com.robot.domain.Robot;
import com.robot.domain.Room;
import com.robot.exception.RobotCollisionException;
import com.robot.exception.RobotOutOfBoundsException;
import com.robot.repository.RobotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service class responsible for handling robot movement simulation within a defined room.
 * Manages robot movements, turns, and collision detection.
 */
public class SimulationService {
    private static final Logger log = LoggerFactory.getLogger(SimulationService.class);
    private final RobotRepository repository;
    private final Room room;

    /**
     * Constructs a new SimulationService with the specified repository and room.
     *
     * @param repository The repository managing robot instances
     * @param room The room defining the boundaries for robot movement
     */
    public SimulationService(RobotRepository repository, Room room) {
        this.repository = repository;
        this.room = room;
        log.info("SimulationService initialized with room dimensions: {}x{}", room.width(), room.height());
    }

    /**
     * Executes a series of commands for a specific robot.
     * Valid commands are: F (Forward), L (Left), R (Right)
     *
     * @param robot The robot to execute commands on
     * @param commands String of commands to execute
     * @throws IllegalArgumentException if an invalid command is provided
     */
    public void executeCommands(Robot robot, String commands) {
        log.debug("Executing commands '{}' for robot at position {}", commands, robot.getPosition());
        for (char command : commands.toCharArray()) {
            switch (command) {
                case 'F' -> moveForward(robot);
                case 'L' -> turnLeft(robot);
                case 'R' -> turnRight(robot);
                default -> {
                    log.error("Invalid command encountered: {}", command);
                    throw new IllegalArgumentException("Invalid command: " + command);
                }
            }
        }
        log.debug("Commands execution completed. Robot final position: {}", robot.getPosition());
    }

    /**
     * Moves the robot one step forward in its current direction.
     */
    private void moveForward(Robot robot) {
        Position newPosition = calculateNewPosition(robot);
        validatePosition(newPosition);
        checkCollision(newPosition);
        robot.setPosition(newPosition);
        log.debug("Robot moved to position: {}", newPosition);
    }

    /**
     * Calculates the new position based on current position and direction.
     */
    private Position calculateNewPosition(Robot robot) {
        // Calculate new position based on current direction
        return switch (robot.getDirection()) {
            case NORTH -> new Position(robot.getPosition().x(), robot.getPosition().y() + 1);
            case EAST -> new Position(robot.getPosition().x() + 1, robot.getPosition().y());
            case SOUTH -> new Position(robot.getPosition().x(), robot.getPosition().y() - 1);
            case WEST -> new Position(robot.getPosition().x() - 1, robot.getPosition().y());
        };
    }

    /**
     * Validates if the position is within room boundaries.
     *
     * @throws RobotOutOfBoundsException if position is outside room boundaries
     */
    private void validatePosition(Position position) {
        if (position.x() < 0 || position.x() >= room.width() ||
                position.y() < 0 || position.y() >= room.height()) {
            log.warn("Robot attempted to move out of bounds to position: {}", position);
            throw new RobotOutOfBoundsException("Position out of bounds: " + position);
        }
    }

    /**
     * Checks if moving to the new position would result in a collision.
     *
     * @throws RobotCollisionException if a collision would occur at the new position
     */
    private void checkCollision(Position newPosition) {
        if (repository.findAll().values().stream()
                .anyMatch(robot -> robot.getPosition().equals(newPosition))) {
            log.warn("Collision detected at position: {}", newPosition);
            throw new RobotCollisionException("Collision detected at position " + newPosition);
        }
    }

    /**
     * Rotates the robot 90 degrees to the left.
     */
    private void turnLeft(Robot robot) {
        Direction oldDirection = robot.getDirection();
        robot.setDirection(switch (oldDirection) {
            case NORTH -> Direction.WEST;
            case WEST -> Direction.SOUTH;
            case SOUTH -> Direction.EAST;
            case EAST -> Direction.NORTH;
        });
        log.debug("Robot turned left from {} to {}", oldDirection, robot.getDirection());
    }

    /**
     * Rotates the robot 90 degrees to the right.
     */
    private void turnRight(Robot robot) {
        Direction oldDirection = robot.getDirection();
        robot.setDirection(switch (oldDirection) {
            case NORTH -> Direction.EAST;
            case EAST -> Direction.SOUTH;
            case SOUTH -> Direction.WEST;
            case WEST -> Direction.NORTH;
        });
        log.debug("Robot turned right from {} to {}", oldDirection, robot.getDirection());
    }
}
