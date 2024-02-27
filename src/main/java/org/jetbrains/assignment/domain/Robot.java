package org.jetbrains.assignment.domain;

import org.springframework.util.Assert;

public class Robot {

    private Coordinate position = new Coordinate(0, 0);

    public Coordinate getPosition() {
        return position;
    }

    public Coordinate move(final Direction direction, int steps) {
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
}
