package com.api.chatop.service;

import com.amazonaws.services.s3.AmazonS3;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Data
@Service
public class S3FileService {

    @Value("${s3.bucket.name}")
    private String s3BucketName;

    @Autowired
    private AmazonS3 amazonS3;

    // Upload images to the bucket
    public String uploadObject(String s3BucketName, String objectName, File objectToUpload) {
        amazonS3.putObject(s3BucketName, objectName, objectToUpload);
        return amazonS3.getUrl(s3BucketName, objectName).toString();
    }

    public String deleteObject(String s3BucketName, String objectName) {
        amazonS3.deleteObject(s3BucketName, objectName);
        return "Image deleted";
    }
}