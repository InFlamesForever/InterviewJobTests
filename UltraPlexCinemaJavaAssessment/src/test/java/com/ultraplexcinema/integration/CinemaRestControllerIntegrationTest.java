package com.ultraplexcinema.integration;

import com.ultraplexcinema.UltraPlexCinemaApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = UltraPlexCinemaApplication.class)
@AutoConfigureMockMvc
@Testcontainers
public class CinemaRestControllerIntegrationTest {

    @Container // This creates a new container for each test, may take a long time if there are lots of tests
    public static PostgreSQLContainer postgresContainer = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("ultraplex")
            .withUsername("postgres")
            .withPassword("password");

    @Autowired
    private MockMvc mvc;

    static {
        postgresContainer.start();

        System.setProperty("spring.datasource.url", postgresContainer.getJdbcUrl());
        System.setProperty("spring.datasource.password", postgresContainer.getPassword());
        System.setProperty("spring.datasource.username", postgresContainer.getUsername());
    }

    @Test
    public void testContainer() {
        assertTrue(postgresContainer.isRunning());
    }

    @Test
    public void addCinema() throws Exception {
        mvc.perform(
                post("/cinema")
                        .content("""
                                {
                                    "name" : "cinema",
                                    "location" : "town"
                                }
                                """)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("cinema"))
                .andExpect(jsonPath("$.location").value("town"));
    }
}
