package org.jetbrains.assignment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void postingMovementsReturnsCorrectLocations() throws Exception {
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
    public void postingLocationsReturnsCorrectMovements() throws Exception {
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
    public void locationsAndMovementsAreSymmetrical() throws Exception {
        mockMvc.perform(post("/moves")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            [{"x":0,"y":0},{"x":1,"y":0},{"x":1,"y":3},{"x":4,"y":3},{"x":4,"y":-2},{"x":2,"y":-2}]"""))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                            [{"direction":"EAST","steps":1},{"direction":"NORTH","steps":3},{"direction":"EAST","steps":3},
                             {"direction":"SOUTH","steps":5},{"direction":"WEST","steps":2}]"""));
    }
}
