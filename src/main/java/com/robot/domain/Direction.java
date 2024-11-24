package com.robot.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Enumeration representing cardinal directions that a robot can face.
 * Provides functionality for direction symbols, parsing, and rotation operations.
 */
public enum Direction {
    NORTH('N'),
    EAST('E'),
    SOUTH('S'),
    WEST('W');

    private static final Logger logger = LoggerFactory.getLogger(Direction.class);
    private final char symbol;

    /**
     * Constructs a Direction with its associated symbol.
     *
     * @param symbol Character representing the direction
     */
    Direction(char symbol) {
        this.symbol = symbol;

    }

    /**
     * Gets the character symbol for this direction.
     *
     * @return Character representing the direction
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * Creates a Direction from its character symbol.
     *
     * @param symbol Character representing desired direction
     * @return Direction corresponding to the symbol
     * @throws IllegalArgumentException if symbol is invalid
     */
    public static Direction fromSymbol(char symbol) {
        char upperSymbol = Character.toUpperCase(symbol);
        logger.debug("Converting symbol {} to direction", upperSymbol);

        return switch (upperSymbol) {
            case 'N' -> NORTH;
            case 'E' -> EAST;
            case 'S' -> SOUTH;
            case 'W' -> WEST;
            default -> {
                logger.error("Invalid direction symbol provided: {}", symbol);
                throw new IllegalArgumentException("Valid directions are: N, E, S, W");
            }
        };
    }

    /**
     * Rotates the direction 90 degrees counter-clockwise.
     *
     * @return New direction after left turn
     */
    public Direction turnLeft() {
        Direction newDirection = switch (this) {
            case NORTH -> WEST;
            case WEST -> SOUTH;
            case SOUTH -> EAST;
            case EAST -> NORTH;
        };
        logger.debug("Direction turned left: {} -> {}", this, newDirection);
        return newDirection;
    }

    /**
     * Rotates the direction 90 degrees clockwise.
     *
     * @return New direction after right turn
     */
    public Direction turnRight() {
        Direction newDirection = switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
        };
        logger.debug("Direction turned right: {} -> {}", this, newDirection);
        return newDirection;
    }
}
