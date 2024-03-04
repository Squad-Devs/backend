package com.shdwraze.metro.repository;

import com.shdwraze.metro.model.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StationRepository extends JpaRepository<Station, Integer> {
    List<Station> findByCityName(String name);

    List<Station> findByCityNameAndLineName(String city, String line);


}
