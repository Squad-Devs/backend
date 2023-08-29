package com.shdwraze.metro.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Configuration
public class FirestoreConfig {

    @Bean
    public Firestore getFirestore() throws IOException {
        ClassLoader classLoader = this.getClass().getClassLoader();

        InputStream serviceAccount = new FileInputStream(
                Objects.requireNonNull(classLoader.getResource("serviceAccountKey.json")).getFile()
        );
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        FirestoreOptions firestoreOptions =
                FirestoreOptions.getDefaultInstance().toBuilder()
                        .setCredentials(credentials)
                        .build();
        return firestoreOptions.getService();
    }
}
