package com.robot.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Immutable record representing a rectangular room with defined width and height dimensions.
 * The room serves as the boundary container for robot movements.
 */
public record Room(int width, int height) {
    private static final Logger log = LoggerFactory.getLogger(Room.class);

    /**
     * Constructs a new Room with validation of dimensions.
     *
     * @param width The room width (must be positive)
     * @param height The room height (must be positive)
     * @throws IllegalArgumentException if dimensions are not positive
     */
    public Room {
        if (width <= 0 || height <= 0) {
            log.error("Invalid room dimensions provided: width={}, height={}", width, height);
            throw new IllegalArgumentException("Room dimensions must be positive");
        }
        log.debug("Created new room with dimensions: {}x{}", width, height);
    }

    /**
     * Checks if a given position falls within the room boundaries.
     *
     * @param position The position to validate
     * @return true if position is within bounds, false otherwise
     */
    public boolean isWithinBounds(Position position) {
        boolean result = position.x() >= 0 && position.x() < width
                && position.y() >= 0 && position.y() < height;
        log.debug("Position {} is {} bounds of room {}x{}",
                position, result ? "within" : "outside", width, height);
        return result;
    }
}
