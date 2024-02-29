package com.shdwraze.metro.controller;

import com.shdwraze.metro.model.entity.Station;
import com.shdwraze.metro.model.response.Path;
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
    public List<Station> getStations(@RequestParam String city,
                                     @RequestParam(required = false) String line) {
        return stationService.getStations(city, line);
    }

    @PostMapping()
    public Station addStation(@RequestBody Station station) {
        return stationService.addStation(station);
    }

    @PostMapping("/add")
    public void addStations(@RequestBody List<Station> stations) {
        stationService.addStations(stations);
    }

    @GetMapping("/{id}")
    public Station getStationById(@PathVariable Integer id) {
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
