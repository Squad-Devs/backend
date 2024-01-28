package com.shdwraze.metro.model.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Station implements Serializable {

    @DocumentId
    private String id;

    private String name;

    private String line;

    private String city;

    private ShortStationInfo nextStation;

    private ShortStationInfo prevStation;

    private ShortStationInfo transferTo;

    private float latitude;

    private float longitude;

    private List<Exit> exits;
}
