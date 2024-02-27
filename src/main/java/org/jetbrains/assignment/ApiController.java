package org.jetbrains.assignment;

import org.jetbrains.assignment.domain.Coordinate;
import org.jetbrains.assignment.domain.Movement;
import org.jetbrains.assignment.domain.Robot;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@RestController
public class ApiController {

    @PostMapping("/locations")
    public List<Coordinate> locations(@RequestBody final List<Movement> movements) {
        final var robot = new Robot();
        final var currentPosition = List.of(robot.getPosition());
        final var trace = movements.stream().map(it -> robot.move(it.direction(), it.steps())).toList();
        return Stream.of(currentPosition, trace).flatMap(Collection::stream).toList();
    }

    @PostMapping("/moves")
    public List<Movement> moves(@RequestBody final List<Coordinate> coordinates) {
        Assert.isTrue(coordinates.size() > 1, "Needs at least two coordinates!");
        final var iterator = coordinates.iterator();
        final var robot = new Robot(iterator.next());

        final List<Movement> movements = new ArrayList<>();
        while (iterator.hasNext()) {
            final var coordinate = iterator.next();
            final var movement = robot.navigateTo(coordinate);
            if (movement != null) {
                movements.addAll(movement);
            }
        }

        return movements;
    }
}
