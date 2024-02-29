package com.shdwraze.metro.mapper;

import com.shdwraze.metro.model.entity.Connection;
import com.shdwraze.metro.model.response.ConnectionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {StationMapper.class, LineMapper.class})
public interface ConnectionMapper {

    ConnectionResponse connectionToConnectionResponse(Connection connection);
}
