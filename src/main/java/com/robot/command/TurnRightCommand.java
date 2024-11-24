package com.robot.command;

import com.robot.domain.Robot;
import com.robot.domain.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Command implementation that rotates a robot 90 degrees clockwise.
 * Part of the Command pattern implementation for robot movement control.
 */
public class TurnRightCommand implements Command {
    private static final Logger log = LoggerFactory.getLogger(TurnRightCommand.class);

    /**
     * Executes a right turn command on the specified robot.
     * The room parameter is unused for turn commands but included for Command interface consistency.
     *
     * @param robot The robot to rotate
     * @param room The room context (unused)
     */
    @Override
    public void execute(Robot robot, Room room) {
        log.debug("Executing right turn command for {}", robot.getId());
        robot.turnRight();
    }
}
