package com.shdwraze.metro.service;

import com.shdwraze.metro.model.entity.City;
import com.shdwraze.metro.model.response.Metropolitan;

import java.util.List;

public interface MetroService {

    List<City> getCities();

//    Set<String> getMetroLinesByCity(String city);

    Metropolitan getMetropolitanByCity(String city);
}
