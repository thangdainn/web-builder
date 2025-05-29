package org.dainn.funnelservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.funnelservice.dto.FPOrderDto;
import org.dainn.funnelservice.dto.FunnelPageDto;
import org.dainn.funnelservice.dto.event.FPOrderEvent;
import org.dainn.funnelservice.event.EventProducer;
import org.dainn.funnelservice.exception.AppException;
import org.dainn.funnelservice.exception.ErrorCode;
import org.dainn.funnelservice.mapper.IFunnelPageMapper;
import org.dainn.funnelservice.model.FunnelPage;
import org.dainn.funnelservice.repository.IFunnelPageRepository;
import org.dainn.funnelservice.service.IFunnelPageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FunnelPageService implements IFunnelPageService {
    private final IFunnelPageRepository funnelPageRepository;
    private final IFunnelPageMapper funnelPageMapper;
    private final EventProducer eventProducer;

    @Transactional
    @Override
    public Mono<FunnelPageDto> create(FunnelPageDto dto) {
        FunnelPage funnelPage = funnelPageMapper.toEntity(dto);
        funnelPage.markNew();
        return funnelPageRepository.save(funnelPage)
                .map(funnelPageMapper::toDto);

    }

    @Transactional
    @Override
    public Mono<FunnelPageDto> update(FunnelPageDto dto) {
        return funnelPageRepository.findById(dto.getId())
                .switchIfEmpty(Mono.error(new AppException(ErrorCode.FUNNEL_PAGE_NOT_EXISTED)))
                .flatMap(funnel -> {
                    funnel = funnelPageMapper.toUpdate(funnel, dto);
                    funnel.markExisting();
                    return funnelPageRepository.save(funnel);
                })
                .map(funnelPageMapper::toDto);
    }

    @Transactional
    @Override
    public Mono<FunnelPageDto> updateName(FunnelPageDto dto) {
        return funnelPageRepository.findById(dto.getId())
                .switchIfEmpty(Mono.error(new AppException(ErrorCode.FUNNEL_PAGE_NOT_EXISTED)))
                .flatMap(existingPage -> funnelPageRepository.updateName(dto.getId(), dto.getName())
                        .then(funnelPageRepository.findById(dto.getId())))
                .map(funnelPageMapper::toDto);
    }

    @Transactional
    @Override
    public Mono<FunnelPageDto> updateContent(FunnelPageDto dto) {
        return funnelPageRepository.findById(dto.getId())
                .switchIfEmpty(Mono.error(new AppException(ErrorCode.FUNNEL_PAGE_NOT_EXISTED)))
                .flatMap(existingPage -> funnelPageRepository.updateContent(dto.getId(), dto.getContent())
                        .then(funnelPageRepository.findById(dto.getId())))
                .map(funnelPageMapper::toDto);
    }

    @Override
    public Mono<FunnelPageDto> updateVisits(FunnelPageDto dto) {
        return funnelPageRepository.findById(dto.getId())
                .switchIfEmpty(Mono.error(new AppException(ErrorCode.FUNNEL_PAGE_NOT_EXISTED)))
                .flatMap(existingPage -> funnelPageRepository.updateVisits(dto.getId(), dto.getVisits())
                        .then(funnelPageRepository.findById(dto.getId())))
                .map(funnelPageMapper::toDto);
    }

    @Override
    public Mono<Void> changeOrderWithKafka(String funnelId, List<FPOrderDto> list) {
        FPOrderEvent event = FPOrderEvent.builder()
                .funnelId(funnelId)
                .list(list)
                .build();
        eventProducer.changeFPOrderEvent(event);
        return Mono.empty();
    }

    @Override
    public Mono<Void> changeOrder(FPOrderEvent data) {
        return funnelPageRepository.findALlByFunnelId(data.getFunnelId())
                .collectMap(FunnelPage::getId)
                .flatMap(laneMap -> {
                    data.getList().forEach(item -> {
                        FunnelPage fp = laneMap.get(item.getFunnelPageId());
                        if (fp != null) {
                            fp.setOrder(item.getOrder());
                            fp.markExisting();
                        }
                    });
                    return funnelPageRepository.saveAll(laneMap.values()).then();
                });
    }

    @Override
    public Mono<FunnelPageDto> findById(String id) {
        return funnelPageRepository.findById(id)
                .switchIfEmpty(Mono.error(new AppException(ErrorCode.FUNNEL_NOT_EXISTED)))
                .map(funnelPageMapper::toDto);
    }

    @Transactional
    @Override
    public Mono<Void> delete(String id) {
        return funnelPageRepository.deleteById(id);
    }

    @Override
    public Flux<FunnelPageDto> findByFunnel(String funnelId) {
        return funnelPageRepository.findAllByFunnelIdOrderByOrderAsc(funnelId)
                .map(funnelPageMapper::toDto);
    }
}
