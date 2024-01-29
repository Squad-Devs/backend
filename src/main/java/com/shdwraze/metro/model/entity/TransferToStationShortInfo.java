package com.shdwraze.metro.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransferToStationShortInfo implements Serializable {

    private String id;

    private String name;

    private String line;

    private int color;
}
