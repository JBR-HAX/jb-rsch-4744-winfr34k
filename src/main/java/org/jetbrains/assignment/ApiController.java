package org.jetbrains.assignment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.assignment.domain.Coordinate;
import org.jetbrains.assignment.domain.Movement;
import org.jetbrains.assignment.domain.Robot;
import org.jetbrains.assignment.entity.ApiTrace;
import org.jetbrains.assignment.repository.ApiTraceRepository;
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

    private final ObjectMapper objectMapper;
    private final ApiTraceRepository apiTraceRepository;

    public ApiController(final ObjectMapper objectMapper, final ApiTraceRepository apiTraceRepository) {
        this.objectMapper = objectMapper;
        this.apiTraceRepository = apiTraceRepository;
    }

    @PostMapping("/locations")
    public List<Coordinate> locations(@RequestBody final List<Movement> movements) throws JsonProcessingException {
        final var robot = new Robot();
        final var currentPosition = List.of(robot.getPosition());
        final var trace = movements.stream().map(it -> robot.move(it.direction(), it.steps())).toList();
        final var locations =  Stream.of(currentPosition, trace).flatMap(Collection::stream).toList();

        // FIXME: This could be an advice.
        final var type = ApiTrace.RequestType.LOCATIONS;
        final var serializedRequest = objectMapper.writeValueAsString(movements);
        final var serializeResponse = objectMapper.writeValueAsString(locations);
        apiTraceRepository.save(new ApiTrace(type, serializedRequest, serializeResponse));

        return locations;
    }

    @PostMapping("/moves")
    public List<Movement> moves(@RequestBody final List<Coordinate> coordinates) throws JsonProcessingException {
        Assert.isTrue(coordinates.size() > 1, "Needs at least two coordinates!");
        final var iterator = coordinates.iterator();
        final var robot = new Robot(iterator.next());

        final var movements = new ArrayList<Movement>();
        while (iterator.hasNext()) {
            final var coordinate = iterator.next();
            final var movement = robot.navigateTo(coordinate);
            if (movement != null) {
                movements.addAll(movement);
            }
        }

        // FIXME: This could be an advice.
        final var type = ApiTrace.RequestType.MOVES;
        final var serializedRequest = objectMapper.writeValueAsString(coordinates);
        final var serializeResponse = objectMapper.writeValueAsString(movements);
        apiTraceRepository.save(new ApiTrace(type, serializedRequest, serializeResponse));

        return movements;
    }
}
