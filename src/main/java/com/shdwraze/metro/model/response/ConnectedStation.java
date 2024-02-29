package com.shdwraze.metro.model.response;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record ConnectedStation(
        Integer id,
        String name,
        LineResponse line
) implements Serializable {}