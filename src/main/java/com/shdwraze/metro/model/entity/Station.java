package com.shdwraze.metro.model.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Station {

    @DocumentId
    private String id;

    private String name;

    private String line;

    private String city;

    private ShortStationInfo nextStation;

    private ShortStationInfo prevStation;

    private ShortStationInfo transferTo;
}
