package org.dainn.mediaservice.service;

import org.dainn.mediaservice.dto.FirebaseResponse;
import org.dainn.mediaservice.dto.MediaDto;
import org.dainn.mediaservice.dto.MediaReq;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IMediaService {
    MediaDto create(MediaDto dto);
    MediaDto findById(String id);
    void delete(String id, String userId) throws Exception;
    Page<MediaDto> findByFilters(MediaReq request);

    FirebaseResponse upload(MultipartFile file) throws IOException;
}
