package com.shdwraze.metro.controller;

import com.shdwraze.metro.model.entity.City;
import com.shdwraze.metro.model.response.Metropolitan;
import com.shdwraze.metro.service.MetroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Metro API")
public class MetroController {

    private final MetroService metroService;

    @Operation(summary = "Get all available cities", description = "Returns the cities that are stored in the database")
    @GetMapping("/cities")
    public List<City> getCitiesWithMetro() {
        return metroService.getCities();
    }

    @Operation(summary = "Get a map of the metropolitan", description = "Returns an ordered metro scheme by city name")
    @GetMapping("/metropolitan")
    public Metropolitan getMetropolitanByCity(@RequestParam String city) {
        return metroService.getMetropolitanByCity(city);
    }
}
