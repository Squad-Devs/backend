package com.shdwraze.metro.service.impl;

import com.shdwraze.metro.model.entity.Station;
import com.shdwraze.metro.repository.impl.StationRepository;
import com.shdwraze.metro.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StationServiceImpl implements StationService {

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
    public Station updateStation(String id, Station updStation) {
        return null;
    }

    @Override
    public List<Station> getShortestPathFromStationToStation(String from, String to) {
        return null;
    }
}
