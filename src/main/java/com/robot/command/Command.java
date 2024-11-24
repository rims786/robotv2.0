package com.robot.command;

import com.robot.domain.Robot;
import com.robot.domain.Room;

/**
 * The Command interface represents a command that can be executed by a robot.
 * Implementing classes should provide specific behavior for different commands.
 */
public interface Command {

    /**
     * Executes the command on the given robot within the specified room.
     *
     * @param robot the robot to execute the command on
     * @param room the room in which the robot operates
     */
    void execute(Robot robot, Room room);
}
// The Command interface defines a method execute that takes a Robot and a Room as parameters.
// This method is responsible for executing the command on the Robot in the given Room.