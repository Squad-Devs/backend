package com.shdwraze.metro.mapper;

import com.shdwraze.metro.model.entity.Station;
import com.shdwraze.metro.model.response.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {LineMapper.class, ExitMapper.class, ConnectionMapper.class})
public interface StationMapper {

    default StationResponse stationToStationResponse(
            Station station,
            LineResponse line,
            List<ExitResponse> exits,
            List<ConnectionResponse> connections
    ) {
        return StationResponse.builder()
                .id(station.getId())
                .name(station.getName())
                .city(station.getCity().getName())
                .longitude(station.getLongitude())
                .latitude(station.getLatitude())
                .line(line)
                .exits(exits)
                .connections(connections)
                .build();
    }

//    default ConnectedStation stationToConnectedStation(Station station, LineResponse line) {
//        return ConnectedStation.builder()
//                .id(station.getId())
//                .name(station.getName())
//                .line(line)
//                .build();
//    }

    ConnectedStation stationToConnectedStation(Station station);
}
