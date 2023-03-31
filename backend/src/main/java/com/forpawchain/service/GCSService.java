package com.forpawchain.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Service
public class GCSService {
	@Value("${spring.cloud.gcp.storage.bucket}")
	private String bucket;
	@Value("${spring.cloud.gcp.credentials.location}")
	private String location;
	@Value("${spring.cloud.gcp.storage.project-id}")
	private String projectId;

	public Blob uploadFileToGCS(MultipartFile file) throws IOException {
		ClassPathResource resource = new ClassPathResource("for-paw-chain-c2d3ba7aaab6.json");

		StorageOptions storageOptions = StorageOptions.newBuilder()
			.setProjectId(projectId)
			.setCredentials(GoogleCredentials.fromStream(resource.getInputStream())).build();
		Storage storage = storageOptions.getService();
		// Storage storage = StorageOptions.getDefaultInstance().getService();

		String fileName = UUID.randomUUID().toString();
		BlobInfo blobInfo = BlobInfo.newBuilder(bucket, fileName).build();
		Blob blob = storage.create(blobInfo, file.getBytes());

		return blob;
	}
}
