package com.formation.films.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.formation.films.domain.dto.FilmCreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
@Transactional
class FilmControllerIT {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @Test
    void post_filmValide_retourne201() throws Exception {
        FilmCreateRequest req = new FilmCreateRequest("The Dark Knight", 2008, 152, "Christopher Nolan");
        mockMvc.perform(post("/api/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titre").value("The Dark Knight"))
                .andExpect(header().exists("Location"));
    }

    @Test
    void post_filmInvalide_retourne400() throws Exception {
        FilmCreateRequest req = new FilmCreateRequest("", 1800, -5, "");
        mockMvc.perform(post("/api/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void get_filmInconnu_retourne404() throws Exception {
        mockMvc.perform(get("/api/films/999999"))
                .andExpect(status().isNotFound());
    }
}