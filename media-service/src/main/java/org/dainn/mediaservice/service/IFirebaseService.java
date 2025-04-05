package org.dainn.mediaservice.service;

import org.dainn.mediaservice.dto.MediaDto;
import org.dainn.mediaservice.dto.MediaReq;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface IFirebaseService {
    String upload(MultipartFile file, String fileName) throws IOException;
}
