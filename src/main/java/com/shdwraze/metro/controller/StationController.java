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

    @GetMapping("/{id}")
    public Station getStationById(@PathVariable String id) {
        return stationService.getStationById(id);
    }

    @PutMapping("/{id}")
    public void updateStation(@PathVariable String id,
                                       @RequestBody Station updStation) {
        stationService.updateStation(id, updStation);
    }

    @GetMapping("/path")
    public Path getShortestPathFromStationToStation(
            @RequestParam String from,
            @RequestParam String to
    ) {
        return stationService.getShortestPathFromStationToStation(from, to);
    }
}
