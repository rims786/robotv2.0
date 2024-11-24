package com.robot.repository;

import com.robot.domain.Robot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread-safe repository for managing robot instances.
 * Provides CRUD operations for Robot entities using an in-memory concurrent hash map.
 */
public class RobotRepository {
    private static final Logger log = LoggerFactory.getLogger(RobotRepository.class);

    // Thread-safe map to store robots with their IDs
    private final Map<String, Robot> robotStore = new ConcurrentHashMap<>();

    /**
     * Saves or updates a robot in the repository.
     *
     * @param robot The robot instance to save
     */
    public void save(Robot robot) {
        log.debug("Saving robot with ID: {} at position: {}", robot.getId(), robot.getPosition());
        robotStore.put(robot.getId(), robot);
    }

    /**
     * Retrieves a robot by its ID.
     *
     * @param id The ID of the robot to find
     * @return The robot if found, null otherwise
     */
    public Robot findById(String id) {
        Robot robot = robotStore.get(id);
        log.debug("Retrieved robot with ID: {} - {}", id, robot != null ? "found" : "not found");
        return robot;
    }

    /**
     * Returns all robots currently in the repository.
     *
     * @return Map of all robots with their IDs
     */
    public Map<String, Robot> findAll() {
        log.debug("Retrieving all robots. Current count: {}", robotStore.size());
        return robotStore;
    }

    /**
     * Removes a robot from the repository.
     *
     * @param id The ID of the robot to delete
     */
    public void delete(String id) {
        log.debug("Deleting robot with ID: {}", id);
        robotStore.remove(id);
    }
}
