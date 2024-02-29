package com.shdwraze.metro.controller;

import com.shdwraze.metro.model.entity.Station;
import com.shdwraze.metro.model.response.Path;
import com.shdwraze.metro.model.response.StationResponse;
import com.shdwraze.metro.service.StationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/stations")
public class StationController {

    private final StationService stationService;

    @GetMapping()
    public List<StationResponse> getStations(@RequestParam String city,
                                     @RequestParam(required = false) String line) {
        return stationService.getStations(city, line);
    }

    @GetMapping("/{id}")
    public StationResponse getStationById(@PathVariable Integer id) {
        return stationService.getStationById(id);
    }

    @GetMapping("/path")
    public Path getShortestPathFromStationToStation(
            @RequestParam Integer from,
            @RequestParam Integer to
    ) {
        return stationService.getShortestPathFromStationToStation(from, to);
    }
}
