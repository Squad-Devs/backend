package com.shdwraze.metro.controller;

import com.shdwraze.metro.model.response.Path;
import com.shdwraze.metro.model.response.StationResponse;
import com.shdwraze.metro.service.StationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/stations")
@Tag(name = "Stations API")
public class StationController {

    private final StationService stationService;

    @Operation(summary = "Get all stations", description = "Returns a complete unordered list of all available stations by city name (line parameter optional)")
    @GetMapping()
    public List<StationResponse> getStations(@RequestParam String city, @RequestParam(required = false) String line) {
        return stationService.getStations(city, line);
    }

    @Operation(summary = "Get a station by ID", description = "Returns complete information about the station by station ID")
    @GetMapping("/{id}")
    public StationResponse getStationById(@PathVariable Integer id) {
        return stationService.getStationById(id);
    }

    @Operation(summary = "Get the shortest path", description = "Returns the shortest route from station to station based on their IDs")
    @GetMapping("/path")
    public Path getShortestPathFromStationToStation(@RequestParam Integer from, @RequestParam Integer to) {
        return stationService.getShortestPathFromStationToStation(from, to);
    }
}
