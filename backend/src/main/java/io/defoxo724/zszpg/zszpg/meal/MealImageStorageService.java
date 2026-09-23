package io.defoxo724.zszpg.zszpg.meal;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.SetBucketPolicyArgs;

@Service
public class MealImageStorageService {
    private final MinioClient minioClient;
    private final String publicUrl;
    private final String bucket;

    public MealImageStorageService(
            @Value("${app.minio.endpoint}") String endpoint,
            @Value("${app.minio.public-url}") String publicUrl,
            @Value("${app.minio.access-key}") String accessKey,
            @Value("${app.minio.secret-key}") String secretKey,
            @Value("${app.minio.bucket}") String bucket) {
        this.minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
        this.publicUrl = publicUrl.replaceAll("/+$", "");
        this.bucket = bucket;
    }

    public String store(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            return null;
        }
        if (image.getContentType() == null || !image.getContentType().startsWith("image/")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The uploaded file must be an image.");
        }

        String objectName = UUID.randomUUID() + getFileExtension(image.getOriginalFilename());
        try {
            ensurePublicBucketExists();
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .stream(image.getInputStream(), image.getSize(), -1L)
                    .contentType(image.getContentType())
                    .build());
            return publicUrl + "/" + bucket + "/" + objectName;
        } catch (ResponseStatusException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "The meal image could not be stored.", exception);
        }
    }

    private void ensurePublicBucketExists() throws Exception {
        boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        if (!bucketExists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        }

        String policy = "{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\","
                + "\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetObject\"],"
                + "\"Resource\":[\"arn:aws:s3:::" + bucket + "/*\"]}]}";
        minioClient.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucket).config(policy).build());
    }

    private String getFileExtension(String filename) {
        if (filename == null) {
            return "";
        }
        int extensionIndex = filename.lastIndexOf('.');
        if (extensionIndex < 0 || extensionIndex == filename.length() - 1) {
            return "";
        }
        return filename.substring(extensionIndex).toLowerCase().replaceAll("[^a-z0-9.]", "");
    }
}
