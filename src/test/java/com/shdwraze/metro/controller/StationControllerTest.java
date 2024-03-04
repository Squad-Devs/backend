package com.shdwraze.metro.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shdwraze.metro.model.response.LineResponse;
import com.shdwraze.metro.model.response.Path;
import com.shdwraze.metro.model.response.StationResponse;
import com.shdwraze.metro.service.StationService;
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
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class StationControllerTest {

    @Mock
    private StationService stationService;

    @InjectMocks
    private StationController stationController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(stationController).build();
    }

    @Test
    void givenCityAndLine_whenGetStations_thenStatus200() throws Exception {
        String city = "TestCity";
        String lineName = "TestLine";
        LineResponse line = new LineResponse(1, lineName, 123456);
        List<StationResponse> stations = Arrays.asList(
                new StationResponse(1, "Station1", line, city, 50.0, 30.0, new ArrayList<>(), new ArrayList<>()),
                new StationResponse(2, "Station2", line, city, 51.0, 31.0, new ArrayList<>(), new ArrayList<>())
        );
        when(stationService.getStations(city, lineName)).thenReturn(stations);

        mockMvc.perform(get("/stations").param("city", city).param("line", lineName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void givenId_whenGetStationById_thenStatus200() throws Exception {
        Integer id = 1;
        String city = "TestCity";
        String lineName = "TestLine";
        LineResponse line = new LineResponse(1, lineName, 123456);
        StationResponse station = new StationResponse(id, "Station1", line, city, 50.0, 30.0, new ArrayList<>(), new ArrayList<>());
        when(stationService.getStationById(id)).thenReturn(station);

        mockMvc.perform(get("/stations/" + id))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(station)));
    }

    @Test
    void givenFromAndTo_whenGetShortestPathFromStationToStation_thenStatus200() throws Exception {
        Integer from = 1;
        Integer to = 2;
        String city = "TestCity";
        String lineName = "TestLine";
        LineResponse line = new LineResponse(1, lineName, 123456);
        List<StationResponse> pathStations = Arrays.asList(
                new StationResponse(from, "Station1", line, city, 50.0, 30.0, new ArrayList<>(), new ArrayList<>()),
                new StationResponse(to, "Station2", line, city, 51.0, 31.0, new ArrayList<>(), new ArrayList<>())
        );
        Path path = new Path(1, 10, pathStations);
        when(stationService.getShortestPathFromStationToStation(from, to)).thenReturn(path);

        mockMvc.perform(get("/stations/path").param("from", from.toString()).param("to", to.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(path)));
    }

    @Test
    void givenNullCity_whenGetStations_thenStatus400() throws Exception {
        mockMvc.perform(get("/stations").param("city", (String) null))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenNonExistingId_whenGetStationById_thenStatus404() throws Exception {
        Integer id = 999;
        when(stationService.getStationById(id)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/stations/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenNonExistingFromAndTo_whenGetShortestPathFromStationToStation_thenStatus404() throws Exception {
        Integer from = 999;
        Integer to = 999;
        when(stationService.getShortestPathFromStationToStation(from, to)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/stations/path").param("from", from.toString()).param("to", to.toString()))
                .andExpect(status().isNotFound());
    }
}
