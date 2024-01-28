package com.shdwraze.metro.model.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Exit implements Serializable {

    private int exitNumber;

    private float latitude;

    private float longitude;
}
