package org.dainn.mediaservice.service;

import org.dainn.mediaservice.dto.MediaDto;
import org.dainn.mediaservice.dto.MediaReq;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;

public interface IMediaService {
    Mono<MediaDto> create(MediaDto dto);
    Mono<MediaDto> findById(String id);
    Mono<Void> delete(String id);
    Mono<Page<MediaDto>> findByFilters(MediaReq request);
}
