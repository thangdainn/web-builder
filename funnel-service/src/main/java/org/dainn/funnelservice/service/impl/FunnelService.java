package org.dainn.funnelservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.funnelservice.dto.FunnelPageDto;
import org.dainn.funnelservice.dto.funnel.FunnelDetailDto;
import org.dainn.funnelservice.dto.funnel.FunnelDto;
import org.dainn.funnelservice.dto.funnel.FunnelReq;
import org.dainn.funnelservice.exception.AppException;
import org.dainn.funnelservice.exception.ErrorCode;
import org.dainn.funnelservice.mapper.IFunnelMapper;
import org.dainn.funnelservice.model.Funnel;
import org.dainn.funnelservice.repository.IClassNameRepository;
import org.dainn.funnelservice.repository.IFunnelPageRepository;
import org.dainn.funnelservice.repository.IFunnelRepository;
import org.dainn.funnelservice.service.IFunnelPageService;
import org.dainn.funnelservice.service.IFunnelService;
import org.dainn.funnelservice.util.Paging;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FunnelService implements IFunnelService {
    private final IFunnelRepository funnelRepository;
    private final IFunnelPageRepository funnelPageRepository;
    private final IClassNameRepository classNameRepository;
    private final IFunnelPageService funnelPageService;
    private final IFunnelMapper funnelMapper;

    @Transactional
    @Override
    public Mono<FunnelDto> create(FunnelDto dto) {
        return funnelRepository.existsBySubDomainName(dto.getSubDomainName())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new AppException(ErrorCode.DOMAIN_ALREADY_EXISTED));
                    }
                    Funnel funnel = funnelMapper.toEntity(dto);
                    funnel.markNew();
                    return funnelRepository.save(funnel)
                            .map(funnelMapper::toDto);
                });

    }

    @Transactional
    @Override
    public Mono<FunnelDto> update(FunnelDto dto) {
        return funnelRepository.findById(dto.getId())
                .switchIfEmpty(Mono.error(new AppException(ErrorCode.FUNNEL_NOT_EXISTED)))
                .flatMap(funnel -> {
                    funnel = funnelMapper.toUpdate(funnel, dto);
                    funnel.markExisting();
                    return funnelRepository.save(funnel);
                })
                .map(funnelMapper::toDto);
    }

    @Transactional
    @Override
    public Mono<FunnelDto> updateLiveProducts(FunnelDto dto) {
        return funnelRepository.findById(dto.getId())
                .switchIfEmpty(Mono.error(new AppException(ErrorCode.FUNNEL_NOT_EXISTED)))
                .flatMap(existing -> funnelRepository.updateLiveProducts(dto.getId(), dto.getLiveProducts())
                        .then(funnelRepository.findById(dto.getId()))
                )
                .map(funnelMapper::toDto);
    }

    @Override
    public Mono<FunnelDto> findById(String id) {
        return funnelRepository.findById(id)
                .switchIfEmpty(Mono.error(new AppException(ErrorCode.FUNNEL_NOT_EXISTED)))
                .map(funnelMapper::toDto);
    }

    @Override
    public Mono<FunnelDetailDto> findDetailById(String id) {
        Mono<Funnel> funnel = funnelRepository.findById(id)
                .switchIfEmpty(Mono.error(new AppException(ErrorCode.FUNNEL_NOT_EXISTED)));
        Flux<FunnelPageDto> funnelPages = funnelPageService.findByFunnel(id);
        return funnel
                .map(funnelMapper::toDetail)
                .flatMap(detail -> funnelPages
                        .collectList()
                        .map(pages -> {
                            detail.setFunnelPages(pages);
                            return detail;
                        }));
    }

    @Override
    public Mono<FunnelDetailDto> findDetailByDomain(String domain) {
        Mono<Funnel> funnel = funnelRepository.findBySubDomainName(domain)
                .switchIfEmpty(Mono.error(new AppException(ErrorCode.FUNNEL_NOT_EXISTED)));

        return funnel.flatMap(f -> {
            Flux<FunnelPageDto> funnelPages = funnelPageService.findByFunnel(f.getId());

            return Mono.just(funnelMapper.toDetail(f))
                    .flatMap(detail -> funnelPages
                            .collectList()
                            .map(pages -> {
                                detail.setFunnelPages(pages);
                                return detail;
                            }));
        });
    }

    @Transactional
    @Override
    public Mono<Void> delete(String id) {
        return funnelRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Mono<Void> deleteBySA(String subAccountId) {
        return funnelRepository.deleteAllBySubAccountId(subAccountId);
    }

    @Override
    public Mono<Page<FunnelDto>> findByFilters(String subAccountId, FunnelReq request) {
        Pageable pageable = Paging.getPageable(request);
        Mono<Page<Funnel>> page;
        if (StringUtils.hasText(request.getKeyword())) {
            page = funnelRepository.findAllBySubAccountIdAndNameContainingIgnoreCase(request.getKeyword(), subAccountId, pageable)
                    .collectList()
                    .zipWith(funnelRepository.count())
                    .map(m -> new PageImpl<>(m.getT1(), pageable, m.getT2()));
        } else {
            page = funnelRepository.findAllBySubAccountId(subAccountId, pageable)
                    .collectList()
                    .zipWith(funnelRepository.count())
                    .map(m -> new PageImpl<>(m.getT1(), pageable, m.getT2()));
        }
        return page.map(m -> m.map(funnelMapper::toDto));
    }
}
