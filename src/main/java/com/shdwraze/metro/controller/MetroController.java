package com.shdwraze.metro.controller;

import com.shdwraze.metro.model.entity.City;
import com.shdwraze.metro.model.response.Metropolitan;
import com.shdwraze.metro.service.MetroService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MetroController {

    private final MetroService metroService;

    @GetMapping("/cities")
    public List<City> getCitiesWithMetro() {
        return metroService.getCities();
    }

//    @GetMapping("/lines")
//    public Set<String> getMetroLinesByCity(@RequestParam String city) {
//        return metroService.getMetroLinesByCity(city);
//    }

    @GetMapping("/metropolitan")
    public Metropolitan getMetropolitanByCity(@RequestParam String city) {
        return metroService.getMetropolitanByCity(city);
    }
}
