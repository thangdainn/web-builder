package org.dainn.mediaservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFirebaseService {
    void upload(MultipartFile file, String fileName) throws IOException;
}
