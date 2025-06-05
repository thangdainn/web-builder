package org.dainn.mediaservice.service.impl;

import com.amazonaws.services.s3.AmazonS3;
//import com.cloudinary.Cloudinary;
//import com.cloudinary.utils.ObjectUtils;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.mediaservice.service.IS3Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service implements IS3Service {
    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Override
    public void upload(MultipartFile file, String publicId) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            metadata.setCacheControl("max-age=31536000");

            metadata.setContentDisposition("inline");
            String key = publicId;
            amazonS3.putObject(bucketName, key, file.getInputStream(), metadata);
            String publicUrl = amazonS3.getUrl(bucketName, key).toString();
            log.info("URL image uploaded to S3: {}", publicUrl);
        } catch (Exception e) {
            throw new RuntimeException("Error generating upload URL", e);
        }
    }
}
