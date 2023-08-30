package com.shdwraze.metro.model.response;

import com.shdwraze.metro.model.entity.Station;
import lombok.Builder;

import java.util.List;

@Builder
public record Path(
        int transfersNumber,

        int travelTimeInMinutes,

        List<Station> path
) {
}
