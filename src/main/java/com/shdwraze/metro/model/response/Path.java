package com.shdwraze.metro.model.response;

import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record Path(
        int transfersNumber,

        int travelTimeInMinutes,

        List<StationResponse> path
) implements Serializable {
}
