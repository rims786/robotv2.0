package com.robot.command;

import com.robot.domain.Robot;
import com.robot.domain.Room;
import com.robot.exception.RobotOutOfBoundsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Command implementation that moves a robot one step forward in its current direction.
 * Validates movement bounds before execution to prevent out-of-bounds scenarios.
 */
public class MoveForwardCommand implements Command {
    private static final Logger log = LoggerFactory.getLogger(MoveForwardCommand.class);

    /**
     * Executes a forward movement command on the specified robot.
     * Calculates the next position and validates it against room boundaries before moving.
     *
     * @param robot The robot to move forward
     * @param room The room containing movement boundaries
     * @throws RobotOutOfBoundsException if movement would place robot outside room bounds
     */
    @Override
    public void execute(Robot robot, Room room) {
        log.debug("Executing forward movement command for {} at position {}",
                robot.getId(), robot.getPosition());

        var nextPosition = robot.calculateNextPosition();
        log.debug("Calculated next position: {}", nextPosition);

        if (!room.isWithinBounds(nextPosition)) {
            log.warn("Movement blocked - {} would move outside room bounds at {}",
                    robot.getId(), nextPosition);
            throw new RobotOutOfBoundsException("Robot would move outside room bounds");
        }

        robot.moveForward();
        log.debug("Movement completed - {} now at position {}",
                robot.getId(), robot.getPosition());
    }
}
