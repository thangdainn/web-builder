package org.dainn.mediaservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.mediaservice.dto.FirebaseResponse;
import org.dainn.mediaservice.dto.MediaDto;
import org.dainn.mediaservice.dto.MediaReq;
import org.dainn.mediaservice.event.EventProducer;
import org.dainn.mediaservice.event.NotificationEvent;
import org.dainn.mediaservice.exception.AppException;
import org.dainn.mediaservice.exception.ErrorCode;
import org.dainn.mediaservice.mapper.IMediaMapper;
import org.dainn.mediaservice.model.Media;
import org.dainn.mediaservice.repository.IMediaRepository;
import org.dainn.mediaservice.service.IFirebaseService;
import org.dainn.mediaservice.service.IMediaService;
import org.dainn.mediaservice.util.Paging;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MediaService implements IMediaService {
    private final IMediaRepository mediaRepository;
    private final IMediaMapper mediaMapper;
    private final IFirebaseService firebaseService;
    private final EventProducer eventProducer;

    @Transactional
    @Override
    public MediaDto create(MediaDto dto) {
        Media media = mediaMapper.toEntity(dto);
        return mediaMapper.toDto(mediaRepository.save(media));
    }

    @Override
    public MediaDto findById(String id) {
        return mediaMapper.toDto(mediaRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.MEDIA_NOT_EXISTED)));
    }

    @Transactional
    @Override
    public void delete(String id) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            log.error("No authenticated user found in SecurityContext");
            throw new AppException(ErrorCode.USER_NOT_AUTHENTICATED);
        }
        String userId = authentication.getName();
        Media media = mediaRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.MEDIA_NOT_EXISTED));
        mediaRepository.deleteById(id);
        eventProducer.sendEvent(NotificationEvent.builder()
                .notification("Deleted a media file " + media.getName())
                .subAccountId(media.getSubAccountId())
                .userId(userId)
                .build());
    }

    @Override
    public Page<MediaDto> findByFilters(MediaReq request) {
        Pageable pageable = Paging.getPageable(request);
        Page<Media> page;
        if (StringUtils.hasText(request.getKeyword())) {
            page = mediaRepository.findByNameContainingIgnoreCaseAndSubAccountId(request.getKeyword(), request.getSubAccountId(), pageable);
        } else {
            page = mediaRepository.findBySubAccountId(request.getSubAccountId(), pageable);
        }
        return page.map(mediaMapper::toDto);
    }

    @Override
    public FirebaseResponse upload(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename() + ".jpg";
        String imageUrl = firebaseService.upload(file, fileName);
        return FirebaseResponse.builder()
                .url(imageUrl)
                .success(true)
                .build();
    }
}
