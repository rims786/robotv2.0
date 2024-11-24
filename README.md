# Robot Simulation Application

## Overview
This application simulates the movement of robots within a defined room. Robots can execute commands to move forward, turn left, or turn right. The application includes collision detection and boundary checks to ensure safe movement.

## Features
- Simulate robot movements within a defined room.
- Handle commands: Forward (F), Left (L), Right (R).
- Detect collisions between robots.
- Validate movements to prevent going out of bounds.

## Requirements
- Java 11 or higher
- Maven (for building the project and managing dependencies)

## Getting Started

### Clone the Repository
	git clone https://github.com/rims786/robot-simulation.git
	cd robot-simulation

### Build the Project
   Use Maven to build the project and download dependencies:
 	mvn clean install


### Running the Application
- Navigate to the project directory.
- Run the main application class (e.g., `RobotSimulationApp` if you have a main class):
	mvn exec:java -Dexec.mainClass="com.robot.RobotSimulationApp"


Follow the prompts to create robots and execute commands.

### Running Tests
   To run the unit tests included in the project, use the following Maven command:
   mvn test

This will execute all the test cases defined in the `SimulationServiceTest` and other test classes.

### Test Coverage
   You can check the test coverage report by running:
   mvn test jacoco:report
   The coverage report will be generated in the `target/site/jacoco` directory.

## Prerequisites
- Java 17 or later
- Maven 3.6 or later

## Run the Simulation
   Execute the main simulation class:
   java -cp target/robot-simulator-1.0-SNAPSHOT.jar com.robotsimulator.controller.Simulation


# Robot Simulator

## Overview
The Robot Simulator is a Java-based application that simulates the movement of robots within a defined room. Robots can receive commands to turn or move forward, and the application includes collision detection to prevent robots from overlapping.

## Features
- Move multiple robots in a room.
- Supports commands:
    - `L`: Turn left
    - `R`: Turn right
    - `F`: Move forward
- Collision detection to prevent robots from occupying the same space.
- Extensible command system for adding new commands.

## Project Structure

plaintext
robot-simulator/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── robotsimulator/
│   │   │           ├── command/
│   │   │           ├── controller/
│   │   │           ├── model/
│   │   │           ├── repository/
│   │   │           └── service/
│   │   └── resources/
│   └── test/
└── README.md



## Usage
1. **Input Format**
    - First line: Room dimensions (width height)
    - Second line: Initial robot position and direction (x y direction)
    - Third line: Command sequence (e.g., `LFRFFRFRF`)

   Example Input:
   5 5
   1 2 N
   RFRFFRFRF

2. **Output**
    - After executing the commands, the robot will report its final position and direction.

   Example Output:
   Report: 1 3 N
   mvn test

## Contributing
Contributions are welcome! Please fork the repository and submit a pull request for any enhancements or bug fixes.

## License
This project is licensed under the MIT License. 

## Acknowledgments
- Inspired by robotics and simulation concepts.
- 
- Uses SLF4J for logging and JUnit for testing.
