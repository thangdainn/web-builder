package org.dainn.mediaservice.service.impl;

import com.amazonaws.services.s3.AmazonS3;
//import com.cloudinary.Cloudinary;
//import com.cloudinary.utils.ObjectUtils;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.mediaservice.service.IFirebaseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class FirebaseService implements IFirebaseService {
    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

//    @Override
//    public void upload(MultipartFile file, String publicId) {
//        try {
//            cloudinary.uploader().upload(file.getBytes(),
//                    ObjectUtils.asMap(
//                            "public_id", publicId,
//                            "overwrite", true
//                    ));
//        } catch (Exception e) {
//            throw new RuntimeException("Error generating upload URL", e);
//        }
//    }

    @Override
    public void upload(MultipartFile file, String publicId) {
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            String key = publicId;
            amazonS3.putObject(bucketName, key, file.getInputStream(), objectMetadata);
            String publicUrl = amazonS3.getUrl(bucketName, key).toString();
            log.info("URL image uploaded to S3: {}", publicUrl);
        } catch (Exception e) {
            throw new RuntimeException("Error generating upload URL", e);
        }
    }
}
