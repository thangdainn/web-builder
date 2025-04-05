package org.dainn.mediaservice.service.impl;

import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import lombok.RequiredArgsConstructor;
import org.dainn.mediaservice.service.IFirebaseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class FirebaseService implements IFirebaseService {

    @Value("${image.base.url}")
    private String imageBaseUrl;
    @Override
    public String upload(MultipartFile file, String fileName) throws IOException {
        InputStream inputStream = file.getInputStream();
        Bucket bucket = StorageClient.getInstance().bucket();
        bucket.create(fileName, inputStream, "image/jpeg");
        return imageBaseUrl + fileName + "?alt=media";
    }
}
