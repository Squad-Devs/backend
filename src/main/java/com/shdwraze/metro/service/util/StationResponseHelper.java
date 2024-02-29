package com.shdwraze.metro.service.util;

import com.shdwraze.metro.mapper.ConnectionMapper;
import com.shdwraze.metro.mapper.ExitMapper;
import com.shdwraze.metro.mapper.LineMapper;
import com.shdwraze.metro.mapper.StationMapper;
import com.shdwraze.metro.model.entity.Station;
import com.shdwraze.metro.model.response.ConnectionResponse;
import com.shdwraze.metro.model.response.ExitResponse;
import com.shdwraze.metro.model.response.LineResponse;
import com.shdwraze.metro.model.response.StationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StationResponseHelper {

    private final LineMapper lineMapper;

    private final ExitMapper exitMapper;

    private final ConnectionMapper connectionMapper;

    private final StationMapper stationMapper;

    public StationResponse convertToStationResponse(Station station) {
        LineResponse lineResponse = lineMapper.lineToLineResponse(station.getLine());
        List<ExitResponse> exitResponses = station.getExits()
                .stream().map(exitMapper::exitToExitResponse).toList();
        List<ConnectionResponse> connectionResponses = station.getConnections()
                .stream().map(connectionMapper::connectionToConnectionResponse).toList();

        return stationMapper.stationToStationResponse(station, lineResponse, exitResponses, connectionResponses);
    }
}

