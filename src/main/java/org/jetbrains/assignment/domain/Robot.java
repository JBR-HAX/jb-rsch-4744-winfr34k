package org.jetbrains.assignment.domain;

import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public class Robot {

    private Coordinate position;

    public Robot() {
        this(new Coordinate(0, 0));
    }

    public Robot(Coordinate coordinate) {
        this.position = coordinate;
    }

    public Coordinate getPosition() {
        return position;
    }

    public Coordinate move(final Direction direction, final int steps) {
        Assert.isTrue(steps > 0, "Steps need to be positive!");

        final var newPosition = switch (direction) {
            case NORTH -> new Coordinate(position.x(), position.y() + steps);
            case EAST -> new Coordinate(position.x() + steps, position.y());
            case SOUTH -> new Coordinate(position.x(), position.y() - steps);
            case WEST -> new Coordinate(position.x() - steps, position.y());
        };
        this.position = newPosition;

        return newPosition;
    }

    public List<Movement> navigateTo(final Coordinate coordinate) {
        final var differenceX = coordinate.x() - position.x();
        final var differenceY = coordinate.y() - position.y();

        var movements = new ArrayList<Movement>();

        if (differenceX > 0) {
            position = coordinate;
            movements.add(new Movement(Direction.EAST, differenceX));
        }
        if (differenceX < 0) {
            position = coordinate;
            movements.add(new Movement(Direction.WEST, Math.abs(differenceX)));
        }
        if (differenceY > 0) {
            position = coordinate;
            movements.add(new Movement(Direction.NORTH, differenceY));
        }
        if (differenceY < 0) {
            position = coordinate;
            movements.add(new Movement(Direction.SOUTH, Math.abs(differenceY)));
        }

        return movements;
    }
}
