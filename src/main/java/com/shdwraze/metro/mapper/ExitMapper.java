package com.shdwraze.metro.mapper;

import com.shdwraze.metro.model.entity.Exit;
import com.shdwraze.metro.model.response.ExitResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExitMapper {

//    static ExitResponse exitToExitResponse(Exit exit) {
//        return ExitResponse.builder()
//                .exitNumber(exit.getExitNumber())
//                .latitude(exit.getLatitude())
//                .longitude(exit.getLongitude())
//                .build();
//    }
    ExitResponse exitToExitResponse(Exit exit);
}
