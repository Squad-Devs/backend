package com.shdwraze.metro.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Configuration
public class FirestoreConfig {

    @Value("${firestore.key}")
    private String firestoreKey;

    @Bean
    public Firestore getFirestore() throws IOException {
        ByteArrayInputStream serviceAccount = new ByteArrayInputStream(firestoreKey.getBytes());
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        FirestoreOptions firestoreOptions =
                FirestoreOptions.getDefaultInstance().toBuilder()
                        .setCredentials(credentials)
                        .build();
        return firestoreOptions.getService();
    }
}
