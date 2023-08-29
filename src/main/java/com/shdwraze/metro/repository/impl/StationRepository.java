package com.shdwraze.metro.repository.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.shdwraze.metro.exception.RepositoryException;
import com.shdwraze.metro.model.entity.Station;
import com.shdwraze.metro.repository.AbstractFirestoreCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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
