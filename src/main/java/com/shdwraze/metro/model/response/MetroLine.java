package com.shdwraze.metro.model.response;

import java.io.Serializable;
import java.util.List;

public record MetroLine(
        String name,

        int color,

        List<StationResponse> stations
) implements Serializable {
}
