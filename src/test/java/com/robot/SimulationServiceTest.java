package com.robot;

import com.robot.domain.Direction;
import com.robot.domain.Position;
import com.robot.domain.Robot;
import com.robot.domain.Room;
import com.robot.exception.RobotCollisionException;
import com.robot.exception.RobotOutOfBoundsException;
import com.robot.repository.RobotRepository;
import com.robot.service.SimulationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SimulationServiceTest {
    private static final Logger log = LoggerFactory.getLogger(SimulationServiceTest.class);
    private SimulationService service;
    private RobotRepository repository;
    private Room room;

    @BeforeEach
    void setUp() {
        room = new Room(5, 5);
        repository = new RobotRepository();
        service = new SimulationService(repository, room);
        log.debug("Test environment initialized with {}x{} room", room.width(), room.height());
    }

    @Test
    @DisplayName("Robot1 executes basic movement commands")
    void executeCommands_Robot1BasicMovement_Success() {
        Robot robot1 = new Robot(new Position(0, 0), Direction.NORTH);
        repository.save(robot1);
        service.executeCommands(robot1, "RF");

        assertEquals(1, robot1.getPosition().x());
        assertEquals(0, robot1.getPosition().y());
        assertEquals(Direction.EAST, robot1.getDirection());
    }

    @Test
    @DisplayName("Robot2 detects room boundary")
    void executeCommands_Robot2Boundary_ThrowsException() {
        Robot robot2 = new Robot(new Position(4, 4), Direction.NORTH);
        repository.save(robot2);
        assertThrows(RobotOutOfBoundsException.class, () ->
                service.executeCommands(robot2, "F"));
    }

    @Test
    @DisplayName("Robot collision detection works correctly")
    void executeCommands_Robot3Collision_ThrowsException() {
        Robot robot1 = new Robot(new Position(2, 2), Direction.NORTH);
        repository.save(robot1);

        Robot robot2 = new Robot(new Position(2, 3), Direction.SOUTH);
        repository.save(robot2);

        Robot robot3 = new Robot(new Position(2, 1), Direction.NORTH);
        repository.save(robot3);

        assertThrows(RobotCollisionException.class, () ->
                service.executeCommands(robot3, "F")
        );
    }

    @ParameterizedTest
    @DisplayName("Robot turns in all directions")
    @CsvSource({
            "NORTH,R,EAST",
            "EAST,R,SOUTH",
            "SOUTH,R,WEST",
            "WEST,R,NORTH",
            "NORTH,L,WEST",
            "WEST,L,SOUTH",
            "SOUTH,L,EAST",
            "EAST,L,NORTH"
    })
    void executeCommands_RobotTurning_Success(String initial, String command, String expected) {
        Robot robot = new Robot(new Position(2, 2), Direction.valueOf(initial));
        repository.save(robot);
        service.executeCommands(robot, command);
        assertEquals(Direction.valueOf(expected), robot.getDirection());
    }

    @Test
    @DisplayName("Three robots execute complex movements")
    void executeCommands_ThreeRobotsComplex_Success() {
        Robot robot1 = new Robot(new Position(0, 0), Direction.NORTH);
        Robot robot2 = new Robot(new Position(2, 2), Direction.EAST);
        Robot robot3 = new Robot(new Position(4, 0), Direction.WEST);

        repository.save(robot1);
        repository.save(robot2);
        repository.save(robot3);

        service.executeCommands(robot1, "RF");
        service.executeCommands(robot2, "LF");
        service.executeCommands(robot3, "RF");

        assertEquals(new Position(1, 0), robot1.getPosition());
        assertEquals(Direction.EAST, robot1.getDirection());

        assertEquals(new Position(2, 3), robot2.getPosition());
        assertEquals(Direction.NORTH, robot2.getDirection());

        assertEquals(new Position(4, 1), robot3.getPosition());
        assertEquals(Direction.NORTH, robot3.getDirection());
    }
}
