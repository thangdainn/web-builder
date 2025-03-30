package org.dainn.subscriptionservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.subscriptionservice.dto.AddOnsDto;
import org.dainn.subscriptionservice.exception.AppException;
import org.dainn.subscriptionservice.exception.ErrorCode;
import org.dainn.subscriptionservice.mapper.IAddOnsMapper;
import org.dainn.subscriptionservice.model.AddOns;
import org.dainn.subscriptionservice.repository.IAddOnsRepository;
import org.dainn.subscriptionservice.service.IAddOnsService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AddOnsService implements IAddOnsService {
    private final IAddOnsRepository addOnsRepository;
    private final IAddOnsMapper addOnsMapper;


    @Override
    public Mono<AddOnsDto> create(AddOnsDto dto) {
        AddOns entity = addOnsMapper.toEntity(dto);
        entity.markNew();
        return addOnsRepository.save(entity).map(addOnsMapper::toDto);
    }

    @Override
    public Mono<AddOnsDto> update(AddOnsDto dto) {
        return addOnsRepository.findById(dto.getId())
                .switchIfEmpty(Mono.error(new AppException(ErrorCode.ADDONS_NOT_EXISTED)))
                .flatMap(existing -> {
                    AddOns updated = addOnsMapper.updateEntity(existing, dto);
                    updated.markExisting();
                    return addOnsRepository.save(updated);
                })
                .map(addOnsMapper::toDto);
    }

    @Override
    public Mono<AddOnsDto> findById(String id) {
        return addOnsRepository.findById(id)
                .switchIfEmpty(Mono.error(new AppException(ErrorCode.ADDONS_NOT_EXISTED)))
                .map(addOnsMapper::toDto);
    }

    @Override
    public void delete(String id) {

    }
}
