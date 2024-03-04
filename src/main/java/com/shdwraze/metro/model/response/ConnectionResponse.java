package com.shdwraze.metro.model.response;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record ConnectionResponse(
        ConnectedStation fromStation,
        ConnectedStation toStation,
        String type
) implements Serializable {}
