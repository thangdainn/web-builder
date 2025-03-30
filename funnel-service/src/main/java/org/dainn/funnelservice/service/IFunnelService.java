package org.dainn.funnelservice.service;

import org.dainn.funnelservice.dto.FunnelDto;

public interface IFunnelService {
    FunnelDto create(FunnelDto dto);
    FunnelDto findById(String id);
    FunnelDto update(FunnelDto dto);
    void delete(String id);
}
