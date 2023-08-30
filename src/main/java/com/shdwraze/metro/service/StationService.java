package com.shdwraze.metro.service;

import com.shdwraze.metro.model.entity.Station;
import com.shdwraze.metro.model.response.Path;

import java.util.List;

public interface StationService {

    List<Station> getStations(String city, String line);

    Station addStation(Station station);

    Station getStationById(String id);

    void updateStation(String id, Station updStation);

    Path getShortestPathFromStationToStation(String from, String to);
}
