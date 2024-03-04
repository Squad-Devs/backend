package com.shdwraze.metro.model.response;

import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record StationResponse(
        Integer id,
        String name,
        LineResponse line,
        String city,
        Double latitude,
        Double longitude,
        List<ExitResponse> exits,
        List<ConnectionResponse> connections
) implements Serializable {}
