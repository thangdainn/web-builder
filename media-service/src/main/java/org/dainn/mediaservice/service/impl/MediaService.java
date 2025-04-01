package org.dainn.mediaservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.mediaservice.dto.MediaDto;
import org.dainn.mediaservice.dto.MediaReq;
import org.dainn.mediaservice.exception.AppException;
import org.dainn.mediaservice.exception.ErrorCode;
import org.dainn.mediaservice.mapper.IMediaMapper;
import org.dainn.mediaservice.model.Media;
import org.dainn.mediaservice.repository.IMediaRepository;
import org.dainn.mediaservice.service.IMediaService;
import org.dainn.mediaservice.util.Paging;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MediaService implements IMediaService {
    private final IMediaRepository mediaRepository;
    private final IMediaMapper mediaMapper;

    @Transactional
    @Override
    public Mono<MediaDto> create(MediaDto dto) {
        Media media = mediaMapper.toEntity(dto);
        media.markNew();
        return mediaRepository.save(media)
                .map(mediaMapper::toDto);
    }

    @Override
    public Mono<MediaDto> findById(String id) {
        return mediaRepository.findById(id)
                .switchIfEmpty(Mono.error(new AppException(ErrorCode.MEDIA_NOT_EXISTED)))
                .map(mediaMapper::toDto);
    }

    @Transactional
    @Override
    public Mono<Void> delete(String id) {
        return mediaRepository.deleteById(id);
    }

    @Override
    public Mono<Page<MediaDto>> findByFilters(MediaReq request) {
        Pageable pageable = Paging.getPageable(request);
        Mono<Page<Media>> page;
        if (StringUtils.hasText(request.getKeyword())) {
            page = mediaRepository.findByNameContainingIgnoreCaseAndSubAccountId(request.getKeyword(), request.getSubAccountId(), pageable)
                    .collectList()
                    .zipWith(mediaRepository.count())
                    .map(m -> new PageImpl<>(m.getT1(), pageable, m.getT2()));
        } else {
            page = mediaRepository.findBySubAccountId(request.getSubAccountId(), pageable)
                    .collectList()
                    .zipWith(mediaRepository.count())
                    .map(m -> new PageImpl<>(m.getT1(), pageable, m.getT2()));
        }
        return page.map(m -> m.map(mediaMapper::toDto));
    }
}
