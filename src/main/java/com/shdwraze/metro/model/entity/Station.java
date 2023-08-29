package com.shdwraze.metro.model.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Station {

    @DocumentId
    private String id;

    private String name;

    private String line;

    private String city;

    private String nextStationId;

    private String prevStationId;

    private String transferTo;
}
