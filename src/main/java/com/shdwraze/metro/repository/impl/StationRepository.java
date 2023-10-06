package com.shdwraze.metro.repository.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.shdwraze.metro.exception.RepositoryException;
import com.shdwraze.metro.model.entity.Station;
import com.shdwraze.metro.repository.AbstractFirestoreCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Repository
public class StationRepository extends AbstractFirestoreCrudRepository<Station> {

    private static final String COLLECTION_NAME = "stations";

    protected StationRepository(Firestore firestore) {
        super(firestore);
    }

    public List<Station> findAllByCityAndLine(String city, String line) {
        ApiFuture<QuerySnapshot> future = getCollectionReference()
                .whereEqualTo("city", city)
                .whereEqualTo("line", line)
                .get();

        QuerySnapshot querySnapshot;
        try {
            querySnapshot = future.get();
        } catch (Exception e) {
            throw new RepositoryException(e);
        }

        return querySnapshot.toObjects(getEntityType());
    }

    public List<Station> findAllByCity(String city) {
        ApiFuture<QuerySnapshot> future = getCollectionReference()
                .whereEqualTo("city", city)
                .get();

        QuerySnapshot querySnapshot;
        try {
            querySnapshot = future.get();
        } catch (Exception e) {
            throw new RepositoryException(e);
        }

        return querySnapshot.toObjects(getEntityType());
    }

    public Set<String> getCities() {
        Set<String> cities = new HashSet<>();
        ApiFuture<QuerySnapshot> future = getCollectionReference().get();

        try {
            QuerySnapshot querySnapshot = future.get();

            for (QueryDocumentSnapshot document : querySnapshot) {
                String city = document.getString("city");
                if (city != null) {
                    cities.add(city);
                }
            }

        } catch (InterruptedException | ExecutionException e) {
            throw new RepositoryException(e);
        }

        return cities;
    }

    public Set<String> getMetroLinesByCity(String city) {
        Set<String> lines = new HashSet<>();
        ApiFuture<QuerySnapshot> future = getCollectionReference()
                .whereEqualTo("city", city)
                .get();

        try {
            QuerySnapshot querySnapshot = future.get();

            for (QueryDocumentSnapshot document : querySnapshot) {
                String line = document.getString("line");
                if (line != null) {
                    lines.add(line);
                }
            }

        } catch (InterruptedException | ExecutionException e) {
            throw new RepositoryException(e);
        }

        return lines;
    }

    @Override
    protected Class<Station> getEntityType() {
        return Station.class;
    }

    @Override
    protected String getId(Station element) {
        return element.getId();
    }

    @Override
    protected String getCollectionName() {
        return COLLECTION_NAME;
    }
}
