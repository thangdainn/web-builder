package org.dainn.funnelservice.service;

import org.dainn.funnelservice.dto.ClassNameDto;

public interface IClassNameService {
    ClassNameDto create(ClassNameDto dto);
    ClassNameDto findById(String id);
    ClassNameDto update(ClassNameDto dto);
    void delete(String id);
}
