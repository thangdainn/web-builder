package org.dainn.subscriptionservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.subscriptionservice.dto.SubscriptionDto;
import org.dainn.subscriptionservice.exception.AppException;
import org.dainn.subscriptionservice.exception.ErrorCode;
import org.dainn.subscriptionservice.mapper.ISubscriptionMapper;
import org.dainn.subscriptionservice.model.Subscription;
import org.dainn.subscriptionservice.repository.ISubscriptionRepository;
import org.dainn.subscriptionservice.service.ISubscriptionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SubscriptionService implements ISubscriptionService {
    private final ISubscriptionRepository subscriptionRepository;
    private final ISubscriptionMapper subscriptionMapper;

    @Transactional
    @Override
    public Mono<SubscriptionDto> create(SubscriptionDto dto) {
        Subscription subscription = subscriptionMapper.toEntity(dto);
        subscription.markNew();
        return subscriptionRepository.save(subscription)
                .map(subscriptionMapper::toDto);
    }

    @Transactional
    @Override
    public Mono<SubscriptionDto> update(SubscriptionDto dto) {
        return subscriptionRepository.findById(dto.getId())
                .switchIfEmpty(Mono.error(new AppException(ErrorCode.SUBSCRIPTION_NOT_EXISTED)))
                .flatMap(existing -> {
                    existing = subscriptionMapper.updateEntity(existing, dto);
                    existing.markExisting();
                    return subscriptionRepository.save(existing);
                })
                .map(subscriptionMapper::toDto);
    }

    @Override
    public Mono<SubscriptionDto> findById(String id) {
        return subscriptionRepository.findById(id)
                .switchIfEmpty(Mono.error(new AppException(ErrorCode.SUBSCRIPTION_NOT_EXISTED)))
                .map(subscriptionMapper::toDto);
    }

    @Override
    public Mono<SubscriptionDto> findByAgencyId(String id) {
        return subscriptionRepository.findByAgencyId(id)
                .switchIfEmpty(Mono.error(new AppException(ErrorCode.SUBSCRIPTION_NOT_EXISTED)))
                .map(subscriptionMapper::toDto);
    }

    @Transactional
    @Override
    public Mono<Void> delete(String id) {
        return subscriptionRepository.deleteById(id);
    }
}
