package org.jetbrains.assignment.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RobotTest {

    private final Robot robot = new Robot();

    @Test
    public void defaultInitialPosition() {
        final var currentPosition = robot.getPosition();

        assertThat(currentPosition).isEqualTo(new Coordinate(0, 0));
    }

    @Test
    public void customInitialPosition() {
        final var robot = new Robot(new Coordinate(-1, 0));
        final var currentPosition = robot.getPosition();

        assertThat(currentPosition).isEqualTo(new Coordinate(-1, 0));
    }

    @Test
    public void cannotMoveNegativeSteps() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> robot.move(Direction.NORTH, 0));
        Assertions.assertThrows(IllegalArgumentException.class, () -> robot.move(Direction.NORTH, -1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> robot.move(Direction.EAST, -1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> robot.move(Direction.SOUTH, -1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> robot.move(Direction.WEST, -1));
    }

    @Test
    public void movingNorth() {
        final var newPosition = robot.move(Direction.NORTH, 1);

        assertThat(newPosition).isEqualTo(new Coordinate(0, 1));
    }

    @Test
    public void movingEast() {
        final var newPosition = robot.move(Direction.EAST, 1);

        assertThat(newPosition).isEqualTo(new Coordinate(1, 0));
    }

    @Test
    public void movingSouth() {
        final var newPosition = robot.move(Direction.SOUTH, 1);

        assertThat(newPosition).isEqualTo(new Coordinate(0, -1));
    }

    @Test
    public void movingWest() {
        final var newPosition = robot.move(Direction.WEST, 1);

        assertThat(newPosition).isEqualTo(new Coordinate(-1, 0));
    }

    @Test
    public void honorsStepSize() {
        final var newPosition = robot.move(Direction.NORTH, 3);

        assertThat(newPosition).isEqualTo(new Coordinate(0, 3));
    }

    @Test
    public void navigationWithoutMovementsResultsInEmptyList() {
        final var movement = robot.navigateTo(new Coordinate(0, 0));

        assertThat(movement).isEmpty();
    }

    @Test
    public void navigatingToACoordinateWithSingleAxisPositiveChangeGivesTheCorrectStep() {
        final var movement = robot.navigateTo(new Coordinate(1, 0));

        assertThat(movement).singleElement().isEqualTo(new Movement(Direction.EAST, 1));
    }

    @Test
    public void navigatingToACoordinateWithSingleAxisNegativeChangeGivesTheCorrectStep() {
        final var movement = robot.navigateTo(new Coordinate(-3, 0));

        assertThat(movement).singleElement().isEqualTo(new Movement(Direction.WEST, 3));
    }

    @Test
    public void navigatingToACoordinateWithMultiAxisChangesGivesCorrectSteps() {
        final var movement = robot.navigateTo(new Coordinate(1, 1));

        assertThat(movement).isEqualTo(List.of(new Movement(Direction.EAST, 1), new Movement(Direction.NORTH, 1)));
    }
}