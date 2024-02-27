package com.shdwraze.metro.service.impl;

import com.shdwraze.metro.model.entity.Connection;
import com.shdwraze.metro.model.entity.Station;
import com.shdwraze.metro.model.entity.enums.ConnectionType;
import com.shdwraze.metro.model.response.Path;
import com.shdwraze.metro.repository.StationRepository;
import com.shdwraze.metro.service.StationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
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
                ? stationRepository.findByCityNameAndLineName(city, line)
                : stationRepository.findByCityName(city);
    }

    @Override
    public Station addStation(Station station) {
        stationRepository.save(station);
        return station;
    }

    @Override
    public Station getStationById(Integer id) {
        return stationRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Station with this id isn't found!"));
    }

    @Override
    public void updateStation(String id, Station updStation) {
//        stationRepository.update(id, updStation);
        // todo
    }

    @Override
    @Cacheable(value = "shortestPath", key = "#from.concat('-').concat(#to)",
            unless = "#result == null", cacheManager = "cacheManagerWithTTL")
    public Path getShortestPathFromStationToStation(Integer from, Integer to) {
        Queue<Station> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        Map<Integer, Integer> parents = new HashMap<>();

        queue.add(stationRepository.findById(from).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Station with this id isn't found!")));
        visited.add(from);

        while (!queue.isEmpty()) {
            Station currentStation = queue.poll();

            if (currentStation.getId().equals(to)) {
                break;
            }

            for (Station neighbor : getStationNeighbors(currentStation)) {
                Integer neighborId = neighbor.getId();
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

        // Получаем все соединения для данной станции
        List<Connection> connections = station.getConnections();

        // Проходимся по каждому соединению и добавляем соседние станции
        for (Connection connection : connections) {
            if (connection.getType() == ConnectionType.NEXT || connection.getType() == ConnectionType.TRANSFER) {
                neighbors.add(connection.getToStation());
            }
            if (connection.getType() == ConnectionType.PREV) {
                neighbors.add(connection.getFromStation());
            }
        }

        return neighbors;
    }


    private List<Station> reconstructPath(Integer fromStationId, Integer toStationId, Map<Integer, Integer> parents) {
        List<Station> path = new ArrayList<>();

        while (!toStationId.equals(fromStationId)) {
            Station station = stationRepository.findById(toStationId).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Station with this id isn't found!"));
            path.add(0, station);
            toStationId = parents.get(toStationId);
        }

        path.add(0, stationRepository.findById(fromStationId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Station with this id isn't found!")));
        return path;
    }

    private int getNumberOfTransfers(List<Station> path) {
        short numberOfTransfers = 0;
        String prevLine = path.get(0).getLine().getName();

        for (Station station : path) {
            String currentLine = station.getLine().getName();
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
