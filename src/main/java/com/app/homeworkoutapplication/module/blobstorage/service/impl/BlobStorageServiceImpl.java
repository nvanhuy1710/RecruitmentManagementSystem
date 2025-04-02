package com.app.homeworkoutapplication.module.blobstorage.service.impl;

import com.app.homeworkoutapplication.module.blobstorage.service.BlobStorageService;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.azure.storage.blob.options.BlobParallelUploadOptions;
import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import org.apache.tika.Tika;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.OffsetDateTime;

@Service
public class BlobStorageServiceImpl implements BlobStorageService {

    private final BlobServiceClient blobServiceClient;
    private final Tika tika = new Tika();

    public BlobStorageServiceImpl(BlobServiceClient blobServiceClient) {
        this.blobServiceClient = blobServiceClient;
    }

    @Override
    public void save(MultipartFile multipartFile, String filePath) {
        filePath = filePath.replaceAll("\\\\", "/");

        String containerName = getContainerName(filePath);

        BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);

        String contentType;
        InputStream inputStream;

        try {
            inputStream = multipartFile.getInputStream();

            contentType = this.tika.detect(inputStream, multipartFile.getOriginalFilename());
            if (!StringUtils.hasText(contentType)) {
                contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }

            inputStream.close();

        } catch (Exception exception) {
            throw new RuntimeException("Error when put blob to azure blob storage!", exception);
        }

        try {
            inputStream = multipartFile.getInputStream();

            BlobClient blobClient = blobContainerClient.getBlobClient(filePath);
            BlobHttpHeaders headers = new BlobHttpHeaders().setContentType(contentType);

            BlobParallelUploadOptions options = new BlobParallelUploadOptions(inputStream);
            options.setHeaders(headers);
            blobClient.uploadWithResponse(options, null, null);

            inputStream.close();
        }catch (Exception exception) {
            throw new RuntimeException("Error when put blob to azure blob storage!", exception);
        }
    }

    @Override
    public void copy(String sourcePath, String destinationPath) {
        sourcePath = sourcePath.replaceAll("\\\\", "/");
        destinationPath = destinationPath.replaceAll("\\\\", "/");

        String srcContainerName = getContainerName(sourcePath);
        String dstContainerName = getContainerName(destinationPath);

        BlobContainerClient blobSourceContainerClient = blobServiceClient.getBlobContainerClient(srcContainerName);

        BlobContainerClient blobDestinationContainerClient = blobServiceClient.getBlobContainerClient(dstContainerName);
        try {
            BlobClient dst = blobDestinationContainerClient.getBlobClient(destinationPath);
            BlobClient src = blobSourceContainerClient.getBlobClient(sourcePath);
            dst.beginCopy(src.getBlobUrl(), null);
        } catch (Exception exception) {
            throw new RuntimeException("Error when copy blob to azure blob storage!", exception);
        }
    }

    @Override
    public void delete(String filePath) {
        filePath = filePath.replaceAll("\\\\", "/");

        String containerName = getContainerName(filePath);

        BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);

        try {
            BlobClient blob = blobContainerClient.getBlobClient(filePath);
            blob.delete();
        } catch (Exception exception) {
            throw new RuntimeException("Error when remove blob to azure blob storage!", exception);
        }
    }

    @Override
    public String getUrl(String filePath) {
        filePath = filePath.replaceAll("\\\\", "/");

        String containerName = getContainerName(filePath);

        BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);


        try {
            BlobSasPermission permission = new BlobSasPermission().setReadPermission(true);
            OffsetDateTime expiryTime = OffsetDateTime.now().plusHours(24);

            BlobServiceSasSignatureValues sasSignatureValues = new BlobServiceSasSignatureValues(expiryTime, permission);

            BlobClient blobClient = blobContainerClient.getBlobClient(filePath);

            return String.format("%s?%s", blobClient.getBlobUrl(), blobClient.generateSas(sasSignatureValues));
        } catch (Exception exception) {
            throw new RuntimeException("Error when get presigned blob to azure blob storage!", exception);
        }
    }

    private String getContainerName(String path) {
        return path.split("/")[0].replace("_", "-").toLowerCase();
    }
}
