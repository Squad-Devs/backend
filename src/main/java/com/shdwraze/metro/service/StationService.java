package com.shdwraze.metro.service;

import com.shdwraze.metro.model.entity.Station;

import java.util.List;

public interface StationService {

    List<Station> getStations(String city, String line);

    Station addStation(Station station);

    Station getStationById(String id);

    Station updateStation(String id, Station updStation);

    List<Station> getShortestPathFromStationToStation(String from, String to);
}
