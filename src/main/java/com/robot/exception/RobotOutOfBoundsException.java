package com.robot.exception;

/**
 * Exception thrown when a robot attempts to move outside the defined room boundaries.
 * This runtime exception indicates invalid movement that would place the robot in an illegal position.
 */
public class RobotOutOfBoundsException extends RuntimeException {

    /**
     * Constructs a new RobotOutOfBoundsException with the specified error message.
     *
     * @param message Detailed description of the boundary violation
     */
    public RobotOutOfBoundsException(String message) {
        super(message); // Pass message to parent RuntimeException
    }
}
