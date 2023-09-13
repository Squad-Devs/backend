package com.shdwraze.metro.service;

import java.util.Set;

public interface MetroService {

    Set<String> getCities();

    Set<String> getMetroLinesByCity(String city);
}
