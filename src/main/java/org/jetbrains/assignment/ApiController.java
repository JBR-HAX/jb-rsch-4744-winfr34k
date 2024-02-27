package org.jetbrains.assignment;

import org.jetbrains.assignment.domain.Coordinate;
import org.jetbrains.assignment.domain.Robot;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@RestController
public class ApiController {

    private final Robot robot;

    public ApiController(Robot robot) {
        this.robot = robot;
    }

    @PostMapping("/locations")
    public List<Coordinate> locations(@RequestBody final List<Movement> movements) {
        final var currentPosition = List.of(robot.getPosition());
        final var trace = movements.stream().map(it -> robot.move(it.direction(), it.steps())).toList();
        return Stream.of(currentPosition, trace).flatMap(Collection::stream).toList();
    }
}
