package com.robot.command;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory class for creating and managing robot commands.
 * This class provides a way to retrieve commands based on character instructions.
 */
public class CommandFactory {
    private static final Logger logger = LoggerFactory.getLogger(CommandFactory.class);
    private static final Map<Character, Command> COMMANDS = new HashMap<>();

    static {
        // Register default commands
        COMMANDS.put('L', new TurnLeftCommand());
        COMMANDS.put('R', new TurnRightCommand());
        COMMANDS.put('F', new MoveForwardCommand());
        logger.info("Default commands registered: L (Turn Left), R (Turn Right), F (Move Forward)");
    }

    /**
     * Retrieves a command based on the given instruction character.
     *
     * @param instruction the character representing the command
     * @return the corresponding Command object
     * @throws IllegalArgumentException if the command is unknown
     */
    public static Command getCommand(char instruction) {
        Command command = COMMANDS.get(instruction);
        if (command == null) {
            String errorMessage = "Unknown command: " + instruction;
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        logger.debug("Command retrieved for instruction '{}': {}", instruction, command.getClass().getSimpleName());
        return command;
    }

    /**
     * Registers a new command with a specified key.
     *
     * @param key the character key for the command
     * @param command the Command object to register
     */
    public static void registerCommand(char key, Command command) {
        COMMANDS.put(key, command);
        logger.info("Command registered: {} -> {}", key, command.getClass().getSimpleName());
    }
}