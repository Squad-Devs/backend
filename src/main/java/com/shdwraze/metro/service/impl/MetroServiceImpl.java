package com.shdwraze.metro.service.impl;

import com.shdwraze.metro.model.entity.Station;
import com.shdwraze.metro.model.response.MetroLine;
import com.shdwraze.metro.model.response.Metropolitan;
import com.shdwraze.metro.repository.impl.StationRepository;
import com.shdwraze.metro.service.MetroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MetroServiceImpl implements MetroService {

    private final StationRepository stationRepository;

    private final Map<String, Integer> metroLineColors = Map.of(
            "Холодногірсько-заводська лінія", 0xFFEA4335,
            "Салтівська лінія", 0xFF0167b4,
            "Олексіївська лінія", 0xFF00933C
    );

    @Override
    public Set<String> getCities() {
        return stationRepository.getCities();
    }

    @Override
    public Set<String> getMetroLinesByCity(String city) {
        return stationRepository.getMetroLinesByCity(city);
    }

    @Override
    public Metropolitan getMetropolitanByCity(String city) {
        List<Station> stations = stationRepository.findAllByCity(city);
        Map<String, List<Station>> stationsByLine = stations.stream()
                .collect(Collectors.groupingBy(Station::getLine))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> {
                    List<Station> sortedStations = sortStations(e.getValue());
                    return sortedStations;
                }));

        List<MetroLine> metroLines = stationsByLine.entrySet().stream()
                .map(entry -> new MetroLine(entry.getKey(),
                        metroLineColors.getOrDefault(entry.getKey(), Color.BLACK.getRGB()),
                        entry.getValue()))
                .toList();

        return new Metropolitan(city, metroLines);
    }

    private List<Station> sortStations(List<Station> stations) {
        List<Station> sortedStations = new ArrayList<>();

        Station currentStation = stations.stream()
                .filter(station -> station.getPrevStation() == null)
                .findFirst()
                .orElse(null);

        while (currentStation != null) {
            sortedStations.add(currentStation);
            String nextStationId = currentStation.getNextStation() != null ? currentStation.getNextStation().getId() : null;
            currentStation = stations.stream()
                    .filter(station -> Objects.equals(station.getId(), nextStationId))
                    .findFirst()
                    .orElse(null);
        }

        return sortedStations;
    }
}
