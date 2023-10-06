package com.shdwraze.metro.service.impl;

import com.shdwraze.metro.model.entity.Station;
import com.shdwraze.metro.model.response.Path;
import com.shdwraze.metro.repository.impl.StationRepository;
import com.shdwraze.metro.service.StationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class StationServiceImpl implements StationService {

    private static final int AVERAGE_TRAVEL_TIME_MIN = 5;

    private final StationRepository stationRepository;

    @Override
    public List<Station> getStations(String city, String line) {
        return line != null
                ? stationRepository.findAllByCityAndLine(city, line)
                : stationRepository.findAllByCity(city);
    }

    @Override
    public Station addStation(Station station) {
        stationRepository.save(station);
        return station;
    }

    @Override
    public Station getStationById(String id) {
        return stationRepository.findById(id);
    }

    @Override
    public void updateStation(String id, Station updStation) {
        stationRepository.update(id, updStation);
    }

    @Override
    public Path getShortestPathFromStationToStation(String from, String to) {
        Queue<Station> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        Map<String, String> parents = new HashMap<>();

        queue.add(stationRepository.findById(from));
        visited.add(from);

        while (!queue.isEmpty()) {
            Station currentStation = queue.poll();

            if (currentStation.getId().equals(to)) {
                break;
            }

            for (Station neighbor : getStationNeighbors(currentStation)) {
                String neighborId = neighbor.getId();
                if (neighborId != null && !visited.contains(neighborId)) {
                    queue.add(neighbor);
                    visited.add(neighborId);
                    parents.put(neighborId, currentStation.getId());
                }
            }
        }

        if (!parents.containsKey(to)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Неможливо розрахувати маршрут");
        }

        List<Station> path = reconstructPath(from, to, parents);
        return Path.builder()
                .transfersNumber(getNumberOfTransfers(path))
                .travelTimeInMinutes(getTravelTimeInMinutes(path))
                .path(path)
                .build();
    }

    @Override
    public void addStations(List<Station> stations) {
        for (Station station : stations) {
            log.error(addStation(station).toString());
        }
    }

    private List<Station> getStationNeighbors(Station station) {
        List<Station> neighbors = new ArrayList<>();
        if (station.getNextStation() != null) {
            neighbors.add(stationRepository.findById(station.getNextStation().getId()));
        }
        if (station.getPrevStation() != null) {
            neighbors.add(stationRepository.findById(station.getPrevStation().getId()));
        }
        if (station.getTransferTo() != null) {
            neighbors.add(stationRepository.findById(station.getTransferTo().getId()));
        }

        return neighbors;
    }

    private List<Station> reconstructPath(String fromStationId, String toStationId, Map<String, String> parents) {
        List<Station> path = new ArrayList<>();

        while (!toStationId.equals(fromStationId)) {
            Station station = stationRepository.findById(toStationId);
            path.add(0, station);
            toStationId = parents.get(toStationId);
        }

        path.add(0, stationRepository.findById(fromStationId));
        return path;
    }

    private int getNumberOfTransfers(List<Station> path) {
        short numberOfTransfers = 0;
        String prevLine = path.get(0).getLine();

        for (Station station : path) {
            String currentLine = station.getLine();
            if (!currentLine.equals(prevLine)) {
                numberOfTransfers++;
            }
            prevLine = currentLine;
        }

        return numberOfTransfers;
    }

    private int getTravelTimeInMinutes(List<Station> path) {
        return path.stream().mapToInt(station -> AVERAGE_TRAVEL_TIME_MIN).sum();
    }
}
