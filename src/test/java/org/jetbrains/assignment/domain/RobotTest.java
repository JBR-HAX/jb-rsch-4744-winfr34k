package org.jetbrains.assignment.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RobotTest {

    @Test
    public void initialPosition() {
        final var robot = new Robot();

        final var currentPosition = robot.getPosition();

        assertThat(currentPosition).isEqualTo(new Coordinate(0, 0));
    }

    @Test
    public void cannotMoveNegativeSteps() {
        final var robot = new Robot();

        Assertions.assertThrows(IllegalArgumentException.class, () -> robot.move(Direction.NORTH, -1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> robot.move(Direction.EAST, -1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> robot.move(Direction.SOUTH, -1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> robot.move(Direction.WEST, -1));
    }

    @Test
    public void movingNorth() {
        final var robot = new Robot();

        final var newPosition = robot.move(Direction.NORTH, 1);

        assertThat(newPosition).isEqualTo(new Coordinate(1, 0));
    }

    @Test
    public void movingEast() {
        final var robot = new Robot();

        final var newPosition = robot.move(Direction.EAST, 1);

        assertThat(newPosition).isEqualTo(new Coordinate(0, 1));
    }

    @Test
    public void movingSouth() {
        final var robot = new Robot();

        final var newPosition = robot.move(Direction.SOUTH, 1);

        assertThat(newPosition).isEqualTo(new Coordinate(-1, 0));
    }

    @Test
    public void movingWest() {
        final var robot = new Robot();

        final var newPosition = robot.move(Direction.WEST, 1);

        assertThat(newPosition).isEqualTo(new Coordinate(0, -1));
    }

    @Test
    public void honorsStepSize() {
        final var robot = new Robot();

        final var newPosition = robot.move(Direction.NORTH, 3);

        assertThat(newPosition).isEqualTo(new Coordinate(3, 0));
    }
}