package org.dainn.funnelservice.service;

import org.dainn.funnelservice.dto.FunnelPageDto;

public interface IFunnelPageService {
    FunnelPageDto create(FunnelPageDto dto);
    FunnelPageDto findById(String id);
    FunnelPageDto update(FunnelPageDto dto);
    void delete(String id);
}
