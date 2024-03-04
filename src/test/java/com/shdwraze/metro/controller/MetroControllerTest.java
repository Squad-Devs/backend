package com.shdwraze.metro.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shdwraze.metro.model.entity.City;
import com.shdwraze.metro.model.response.Metropolitan;
import com.shdwraze.metro.service.MetroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class MetroControllerTest {

    @Mock
    private MetroService metroService;

    @InjectMocks
    private MetroController metroController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(metroController).build();
    }

    @Test
    void givenCities_whenGetCities_thenStatus200AndExpectedSize() throws Exception {
        List<City> cities = Arrays.asList(
                new City(1, "Name1", new ArrayList<>()),
                new City(2, "Name2", new ArrayList<>()));
        when(metroService.getCities()).thenReturn(cities);

        mockMvc.perform(get("/cities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void givenCity_whenGetMetropolitanByCity_thenStatus200() throws Exception {
        String city = "city";
        Metropolitan metropolitan = new Metropolitan(city, new ArrayList<>());
        when(metroService.getMetropolitanByCity(city)).thenReturn(metropolitan);

        mockMvc.perform(get("/metropolitan").param("city", city))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(metropolitan)));
    }

    @Test
    void givenNoCities_whenGetCities_thenStatus200AndEmptyList() throws Exception {
        when(metroService.getCities()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/cities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void givenNullCity_whenGetMetropolitanByCity_thenStatus400() throws Exception {
        mockMvc.perform(get("/metropolitan").param("city", (String) null))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenNonExistingCity_whenGetMetropolitanByCity_thenStatus404() throws Exception {
        String city = "NonExistingCity";
        when(metroService.getMetropolitanByCity(city)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/metropolitan").param("city", city))
                .andExpect(status().isNotFound());
    }

}
