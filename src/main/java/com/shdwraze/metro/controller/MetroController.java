package com.shdwraze.metro.controller;

import com.shdwraze.metro.service.MetroService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
public class MetroController {

    private final MetroService metroService;

    @GetMapping("/cities")
    public Set<String> getCitiesWithMetro() {
        return metroService.getCities();
    }

    @GetMapping("/lines")
    public Set<String> getMetroLinesByCity(@RequestParam String city) {
        return metroService.getMetroLinesByCity(city);
    }
}
