package com.shdwraze.metro.model.response;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record LineResponse(
        Integer id,
        String name,
        Integer color
) implements Serializable {}
