package com.shdwraze.metro.service;

import com.shdwraze.metro.model.response.Path;
import com.shdwraze.metro.model.response.StationResponse;

import java.util.List;

public interface StationService {

    List<StationResponse> getStations(String city, String line);

    StationResponse getStationById(Integer id);

    Path getShortestPathFromStationToStation(Integer from, Integer to);
}
