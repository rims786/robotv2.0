package com.robot.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Immutable record representing a 2D position with x and y coordinates.
 * Used to track robot locations within the room grid system.
 */
public record Position(int x, int y) {
    private static final Logger log = LoggerFactory.getLogger(Position.class);

    /**
     * Constructs a new Position with validation of coordinates.
     *
     * @param x The x-coordinate (must be non-negative)
     * @param y The y-coordinate (must be non-negative)
     * @throws IllegalArgumentException if coordinates are negative
     */
    public Position {
        if (x < 0 || y < 0) {
            log.error("Invalid position coordinates provided: x={}, y={}", x, y);
            throw new IllegalArgumentException("Position coordinates cannot be negative");
        }
        log.trace("Created new position at coordinates: ({}, {})", x, y);
    }
}
