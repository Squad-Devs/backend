package com.shdwraze.metro.mapper;

import com.shdwraze.metro.model.entity.Line;
import com.shdwraze.metro.model.response.LineResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LineMapper {

//    static LineResponse lineToLineResponse(Line line) {
//        return LineResponse.builder()
//                .id(line.getId())
//                .name(line.getName())
//                .color(line.getColor())
//                .build();
//    }

    LineResponse lineToLineResponse(Line line);
}
