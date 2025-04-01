package org.dainn.subscriptionservice.service;

import org.dainn.subscriptionservice.dto.AddOnsDto;
import reactor.core.publisher.Mono;

public interface IAddOnsService {
    Mono<AddOnsDto> create(AddOnsDto dto);
    Mono<AddOnsDto> update(AddOnsDto dto);
    Mono<AddOnsDto> findById(String id);
    Mono<Void> delete(String id);
}
