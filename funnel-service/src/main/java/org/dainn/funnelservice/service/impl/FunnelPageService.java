package org.dainn.funnelservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.funnelservice.dto.FunnelPageDto;
import org.dainn.funnelservice.mapper.IFunnelPageMapper;
import org.dainn.funnelservice.repository.IFunnelPageRepository;
import org.dainn.funnelservice.service.IFunnelPageService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FunnelPageService implements IFunnelPageService {
    private final IFunnelPageRepository funnelPageRepository;
    private final IFunnelPageMapper funnelPageMapper;


    @Override
    public FunnelPageDto create(FunnelPageDto dto) {
        return null;
    }

    @Override
    public FunnelPageDto findById(String id) {
        return null;
    }

    @Override
    public FunnelPageDto update(FunnelPageDto dto) {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
