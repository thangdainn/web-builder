package org.dainn.agencyservice.service;


import org.dainn.agencyservice.dto.*;

import java.util.List;

public interface ITemplateService {
    TemplateDto create(TemplateDto dto);
    List<TemplateDto> findAll();
}
