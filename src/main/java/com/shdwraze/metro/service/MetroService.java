package com.shdwraze.metro.service;

import com.shdwraze.metro.model.response.Metropolitan;

import java.util.Set;

public interface MetroService {

    Set<String> getCities();

    Set<String> getMetroLinesByCity(String city);

    Metropolitan getMetropolitanByCity(String city);
}
