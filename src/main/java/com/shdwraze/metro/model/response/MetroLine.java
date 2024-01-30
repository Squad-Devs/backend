package com.shdwraze.metro.model.response;

import com.shdwraze.metro.model.entity.Station;

import java.io.Serializable;
import java.util.List;

public record MetroLine(
        String name,

        int color,

        List<Station> stations
) implements Serializable {
}
