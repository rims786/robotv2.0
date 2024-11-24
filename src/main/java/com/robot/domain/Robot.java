package com.robot.domain;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a robot that can move and rotate within a defined space.
 * Each robot has a unique ID, position coordinates, and facing direction.
 */
@Getter
@Setter
public class Robot {
    private static final Logger log = LoggerFactory.getLogger(Robot.class);

    // Counter for generating unique robot IDs
    private static int robotCounter = 1;

    private String id;
    private Position position;
    private Direction direction;

    /**
     * Creates a new robot with specified position and direction.
     *
     * @param position Initial position of the robot
     * @param direction Initial direction the robot is facing
     */
    public Robot(Position position, Direction direction) {
        this.id = "Robot" + robotCounter++;
        this.position = position;
        this.direction = direction;
        log.info("Created {} at position {} facing {}", id, position, direction);
    }

    /**
     * Rotates the robot 90 degrees counter-clockwise.
     */
    public void turnLeft() {
        Direction oldDirection = this.direction;
        this.direction = direction.turnLeft();
        log.debug("{} turned left from {} to {}", id, oldDirection, direction);
    }

    /**
     * Rotates the robot 90 degrees clockwise.
     */
    public void turnRight() {
        Direction oldDirection = this.direction;
        this.direction = direction.turnRight();
        log.debug("{} turned right from {} to {}", id, oldDirection, direction);
    }

    /**
     * Calculates the next position based on current direction without moving.
     *
     * @return The potential next position
     */
    public Position calculateNextPosition() {
        Position nextPosition = switch (direction) {
            case NORTH -> new Position(position.x(), position.y() + 1);
            case EAST -> new Position(position.x() + 1, position.y());
            case SOUTH -> new Position(position.x(), position.y() - 1);
            case WEST -> new Position(position.x() - 1, position.y());
        };
        log.debug("{} calculated next position: {}", id, nextPosition);
        return nextPosition;
    }

    /**
     * Moves the robot one step forward in its current direction.
     */
    public void moveForward() {
        Position oldPosition = this.position;
        this.position = calculateNextPosition();
        log.debug("{} moved from {} to {}", id, oldPosition, position);
    }

    /**
     * Returns a string representation of the robot's current state.
     *
     * @return Formatted string with robot ID, position and direction
     */
    @Override
    public String toString() {
        return String.format("%s at (%d, %d) facing %c",
                id, position.x(), position.y(), direction.getSymbol());
    }
}
