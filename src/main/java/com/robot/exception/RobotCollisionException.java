package com.robot.exception;

/**
 * Exception thrown when a robot attempts to move into a position occupied by another robot.
 * This runtime exception helps maintain safe distances between robots and prevents collisions
 * during movement operations.
 */
public class RobotCollisionException extends RuntimeException {

    /**
     * Constructs a new RobotCollisionException with the specified error message.
     *
     * @param message Detailed description of the collision scenario including position information
     */
    public RobotCollisionException(String message) {
        super(message); // Pass message to parent RuntimeException
    }
}
