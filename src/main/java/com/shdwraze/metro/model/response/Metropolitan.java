package com.shdwraze.metro.model.response;

import java.util.List;

public record Metropolitan(
        String city,

        List<MetroLine> lines
) {
}
