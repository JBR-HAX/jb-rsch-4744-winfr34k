package org.jetbrains.assignment.domain;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class Robot {
    private static final Object LOCK = new Object();

    private Coordinate position = new Coordinate(0, 0);

    public Coordinate getPosition() {
        return new Coordinate(position.x(), position.y());
    }

    public Coordinate move(final Direction direction, int steps) {
        Assert.isTrue(steps > 0, "Steps need to be positive!");

        // FIXME: This is a primitive solution, maybe come up with a more performant idea later?
        //        What about lock free data structures?
        synchronized(LOCK) {
            final var newPosition = switch (direction) {
                case NORTH -> new Coordinate(position.x() + steps, position.y());
                case EAST -> new Coordinate(position.x(), position.y() + steps);
                case SOUTH -> new Coordinate(position.x() - steps, position.y());
                case WEST -> new Coordinate(position.x(), position.y() - steps);
            };

            this.position = newPosition;

            return newPosition;
        }
    }
}
