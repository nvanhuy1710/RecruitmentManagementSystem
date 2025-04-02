package com.app.homeworkoutapplication.config;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.common.StorageSharedKeyCredential;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BlobStorageConfig {

    @Value("${storage.account-name}")
    private String accountName;

    @Value("${storage.account-key}")
    private String accountKey;

    @Value("${storage.endpoint}")
    private String endpoint;

    @Bean
    public BlobServiceClient blobServiceClient() {

        StorageSharedKeyCredential credential = new StorageSharedKeyCredential(accountName, accountKey);

        return new BlobServiceClientBuilder()
                .endpoint(endpoint)
                .credential(credential)
                .buildClient();
    }
}
