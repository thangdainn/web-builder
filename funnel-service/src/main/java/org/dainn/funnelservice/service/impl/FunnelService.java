package org.dainn.funnelservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.funnelservice.dto.FunnelDto;
import org.dainn.funnelservice.mapper.IFunnelMapper;
import org.dainn.funnelservice.repository.IFunnelRepository;
import org.dainn.funnelservice.service.IFunnelService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FunnelService implements IFunnelService {
    private final IFunnelRepository funnelRepository;
    private final IFunnelMapper funnelMapper;

    @Override
    public FunnelDto create(FunnelDto dto) {
        return null;
    }

    @Override
    public FunnelDto findById(String id) {
        return null;
    }

    @Override
    public FunnelDto update(FunnelDto dto) {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
