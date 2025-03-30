package org.dainn.funnelservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.funnelservice.dto.ClassNameDto;
import org.dainn.funnelservice.mapper.IClassNameMapper;
import org.dainn.funnelservice.repository.IClassNameRepository;
import org.dainn.funnelservice.service.IClassNameService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClassNameService implements IClassNameService {
    private final IClassNameRepository classNameRepository;
    private final IClassNameMapper classNameMapper;

    @Override
    public ClassNameDto create(ClassNameDto dto) {
        return null;
    }

    @Override
    public ClassNameDto findById(String id) {
        return null;
    }

    @Override
    public ClassNameDto update(ClassNameDto dto) {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
