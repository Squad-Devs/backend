package com.shdwraze.metro.model.response;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record ExitResponse(
        Integer exitNumber,
        Double latitude,
        Double longitude
) implements Serializable {}
