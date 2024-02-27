package org.jetbrains.assignment;

import org.jetbrains.assignment.entity.ApiTrace;
import org.jetbrains.assignment.repository.ApiTraceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void postingMovesReturnsCorrectLocations() throws Exception {
        mockMvc.perform(post("/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                [{"direction":"EAST","steps":1},{"direction":"NORTH","steps":3},{"direction":"EAST","steps":3},
                                 {"direction":"SOUTH","steps":5},{"direction":"WEST","steps":2}]"""))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                        [{"x":0,"y":0},{"x":1,"y":0},{"x":1,"y":3},{"x":4,"y":3},{"x":4,"y":-2},{"x":2,"y":-2}]"""));
    }

    @Test
    public void postingLocationsReturnsCorrectMoves() throws Exception {
        mockMvc.perform(post("/moves")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            [{"x": 0, "y": 0}, {"x": 1, "y": 0}, {"x": 1, "y": 3}, {"x": 0, "y": 3}, {"x": 0, "y": 0}]"""))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                        [{"direction":"EAST","steps":1},{"direction":"NORTH","steps":3},{"direction":"WEST","steps":1},
                        {"direction":"SOUTH","steps":3}]"""));
    }

    @Test
    public void locationsAndMoveAreSymmetrical() throws Exception {
        mockMvc.perform(post("/moves")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            [{"x":0,"y":0},{"x":1,"y":0},{"x":1,"y":3},{"x":4,"y":3},{"x":4,"y":-2},{"x":2,"y":-2}]"""))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                            [{"direction":"EAST","steps":1},{"direction":"NORTH","steps":3},{"direction":"EAST","steps":3},
                             {"direction":"SOUTH","steps":5},{"direction":"WEST","steps":2}]"""));
    }

    @Test
    public void requestsAndResponsesArePersisted(@Autowired final ApiTraceRepository repository) throws Exception {
        final var request = """
                        [{"direction":"EAST","steps":1},{"direction":"NORTH","steps":3},{"direction":"EAST","steps":3},
                        {"direction":"SOUTH","steps":5},{"direction":"WEST","steps":2}]""";
        final var response = """
                        [{"x":0,"y":0},{"x":1,"y":0},{"x":1,"y":3},{"x":4,"y":3},{"x":4,"y":-2},{"x":2,"y":-2}]""";
        mockMvc.perform(post("/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(response));


        final var persisted = repository.findLast().orElseThrow();
        assertThat(persisted.getType()).isEqualTo(ApiTrace.RequestType.LOCATIONS);
        assertThat(persisted.getRequest()).isEqualTo(request.replaceAll("\\s+", ""));
        assertThat(persisted.getResponse()).isEqualTo(response.replaceAll("\\s+", ""));
    }
}
