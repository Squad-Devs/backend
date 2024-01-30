package com.shdwraze.metro.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.shdwraze.metro.exception.RepositoryException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractFirestoreCrudRepository<T> {

    private final Firestore firestore;

    protected AbstractFirestoreCrudRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    protected abstract Class<T> getEntityType();

    protected abstract String getId(T element);

    protected abstract String getCollectionName();

    protected CollectionReference getCollectionReference() {
        return firestore.collection(getCollectionName());
    }

    public List<T> findAll() {
        ApiFuture<QuerySnapshot> future = getCollectionReference().get();
        QuerySnapshot querySnapshot;
        try {
            querySnapshot = future.get();
        } catch (Exception e) {
            throw new RepositoryException(e);
        }

        return querySnapshot.toObjects(getEntityType());
    }

    public T findById(String id) {
        ApiFuture<DocumentSnapshot> future = getCollectionReference().document(id).get();
        DocumentSnapshot document;
        try {
            document = future.get();
        } catch (Exception e) {
            throw new RepositoryException(e);
        }

        return document.toObject(getEntityType());
    }

    public void save(T element) {
        getCollectionReference().add(element);
    }

    public void update(String id, T element) {
        Map<String, Object> updates = new HashMap<>();

        DocumentReference documentReference = getCollectionReference().document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document;
        try {
            document = future.get();
        } catch (Exception e) {
            throw new RepositoryException(e);
        }

        if (document.exists()) {
            T existingElement = document.toObject(getEntityType());
            if (existingElement != null) {
                Map<String, Object> existingFields = new ObjectMapper().convertValue(existingElement, Map.class);
                Map<String, Object> newFields = new ObjectMapper().convertValue(element, Map.class);

                for (Map.Entry<String, Object> entry : newFields.entrySet()) {
                    Object newValue = entry.getValue();
                    Object existingValue = existingFields.get(entry.getKey());

                    if (newValue != null && !newValue.equals(existingValue)) {
                        updates.put(entry.getKey(), newValue);
                    }
                }
            }
        }

        if (!updates.isEmpty()) {
            documentReference.update(updates);
        }
    }

    public void delete(String id) {
        getCollectionReference().document(id).delete();
    }
}
