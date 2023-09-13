package com.shdwraze.metro.service.impl;

import com.shdwraze.metro.repository.impl.StationRepository;
import com.shdwraze.metro.service.MetroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class MetroServiceImpl implements MetroService {

    private final StationRepository stationRepository;

    @Override
    public Set<String> getCities() {
        return stationRepository.getCities();
    }

    @Override
    public Set<String> getMetroLinesByCity(String city) {
        return stationRepository.getMetroLinesByCity(city);
    }
}
