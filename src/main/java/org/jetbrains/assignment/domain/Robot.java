package org.jetbrains.assignment.domain;

import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public class Robot {

    private Location location;

    public Robot() {
        this(new Location(0, 0));
    }

    public Robot(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public Location move(final Direction direction, final int steps) {
        Assert.isTrue(steps > 0, "Steps need to be positive!");

        final var newPosition = switch (direction) {
            case NORTH -> new Location(location.x(), location.y() + steps);
            case EAST -> new Location(location.x() + steps, location.y());
            case SOUTH -> new Location(location.x(), location.y() - steps);
            case WEST -> new Location(location.x() - steps, location.y());
        };
        this.location = newPosition;

        return newPosition;
    }

    public List<Move> navigateTo(final Location location) {
        final var differenceX = location.x() - this.location.x();
        final var differenceY = location.y() - this.location.y();

        var movements = new ArrayList<Move>();

        if (differenceX > 0) {
            this.location = location;
            movements.add(new Move(Direction.EAST, differenceX));
        }
        if (differenceX < 0) {
            this.location = location;
            movements.add(new Move(Direction.WEST, Math.abs(differenceX)));
        }
        if (differenceY > 0) {
            this.location = location;
            movements.add(new Move(Direction.NORTH, differenceY));
        }
        if (differenceY < 0) {
            this.location = location;
            movements.add(new Move(Direction.SOUTH, Math.abs(differenceY)));
        }

        return movements;
    }
}
