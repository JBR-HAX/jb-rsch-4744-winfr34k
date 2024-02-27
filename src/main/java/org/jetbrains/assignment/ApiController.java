package org.jetbrains.assignment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.assignment.domain.Location;
import org.jetbrains.assignment.domain.Move;
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
    public List<Location> locations(@RequestBody final List<Move> moves) throws JsonProcessingException {
        final var robot = new Robot();
        final var currentPosition = List.of(robot.getLocation());
        final var trace = moves.stream().map(it -> robot.move(it.direction(), it.steps())).toList();
        final var locations =  Stream.of(currentPosition, trace).flatMap(Collection::stream).toList();

        // FIXME: This could be an advice.
        final var type = ApiTrace.RequestType.LOCATIONS;
        final var serializedRequest = objectMapper.writeValueAsString(moves);
        final var serializeResponse = objectMapper.writeValueAsString(locations);
        apiTraceRepository.save(new ApiTrace(type, serializedRequest, serializeResponse));

        return locations;
    }

    @PostMapping("/moves")
    public List<Move> moves(@RequestBody final List<Location> locations) throws JsonProcessingException {
        Assert.isTrue(locations.size() > 1, "Needs at least two coordinates!");
        final var iterator = locations.iterator();
        final var robot = new Robot(iterator.next());

        final var movements = new ArrayList<Move>();
        while (iterator.hasNext()) {
            final var coordinate = iterator.next();
            final var movement = robot.navigateTo(coordinate);
            if (movement != null) {
                movements.addAll(movement);
            }
        }

        // FIXME: This could be an advice.
        final var type = ApiTrace.RequestType.MOVES;
        final var serializedRequest = objectMapper.writeValueAsString(locations);
        final var serializeResponse = objectMapper.writeValueAsString(movements);
        apiTraceRepository.save(new ApiTrace(type, serializedRequest, serializeResponse));

        return movements;
    }
}
