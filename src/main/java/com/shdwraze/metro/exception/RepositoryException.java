package com.shdwraze.metro.exception;

public class RepositoryException extends RuntimeException {

    private static final String MESSAGE = "Error while fetching data from Firestore";

    public RepositoryException(Throwable cause) {
        super(MESSAGE, cause);
    }
}