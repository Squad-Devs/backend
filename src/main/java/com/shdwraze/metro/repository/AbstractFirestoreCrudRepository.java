package com.shdwraze.metro.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.shdwraze.metro.exception.RepositoryException;

import java.util.List;

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
        getCollectionReference().document(id).set(element);
    }

    public void delete(String id) {
        getCollectionReference().document(id).delete();
    }
}
