package com.shdwraze.metro.service.impl;

import com.shdwraze.metro.model.entity.City;
import com.shdwraze.metro.model.entity.Connection;
import com.shdwraze.metro.model.entity.Line;
import com.shdwraze.metro.model.entity.Station;
import com.shdwraze.metro.model.entity.enums.ConnectionType;
import com.shdwraze.metro.model.response.MetroLine;
import com.shdwraze.metro.model.response.Metropolitan;
import com.shdwraze.metro.repository.CityRepository;
import com.shdwraze.metro.repository.StationRepository;
import com.shdwraze.metro.service.MetroService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MetroServiceImpl implements MetroService {

    private final CityRepository cityRepository;

    private final StationRepository stationRepository;

    @Override
    public List<City> getCities() {
        return cityRepository.findAll();
    }

    @Override
    @Cacheable(value = "metropolitan", key = "#city")
    public Metropolitan getMetropolitanByCity(String city) {
        List<Station> stations = stationRepository.findByCityName(city);

        // Группировка станций по линиям
        Map<Line, List<Station>> stationsByLine = stations.stream()
                .collect(Collectors.groupingBy(Station::getLine));

        List<MetroLine> metroLines = stationsByLine.entrySet().stream()
                .map(entry -> {
                    Line line = entry.getKey();
                    List<Station> lineStations = entry.getValue();
                    int color = line.getColor() != null ? line.getColor() : Color.BLACK.getRGB();
                    List<Station> sortedStations = sortStations(lineStations);
                    return new MetroLine(line.getName(), color, sortedStations);
                })
                .toList();

        return new Metropolitan(city, metroLines);
    }

    private List<Station> sortStations(List<Station> stations) {
        List<Station> sortedStations = new ArrayList<>();

        // Находим начальную станцию
        Station currentStation = stations.stream()
                .filter(station -> station.getConnections().stream()
                        .noneMatch(connection -> connection.getType() == ConnectionType.PREV))
                .findFirst()
                .orElse(null);

        while (currentStation != null) {
            sortedStations.add(currentStation);
            // Ищем следующую станцию по соединениям
            currentStation = currentStation.getConnections().stream()
                    .filter(connection -> connection.getType() == ConnectionType.NEXT)
                    .map(Connection::getToStation)
                    .findFirst()
                    .orElse(null);
        }

        return sortedStations;
    }
}
