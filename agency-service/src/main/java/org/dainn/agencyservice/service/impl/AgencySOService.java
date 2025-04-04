package org.dainn.agencyservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.agencyservice.dto.AgencySODto;
import org.dainn.agencyservice.mapper.IAgencySOMapper;
import org.dainn.agencyservice.model.Agency;
import org.dainn.agencyservice.model.AgencySidebarOption;
import org.dainn.agencyservice.repository.IAgencySORepository;
import org.dainn.agencyservice.service.IAgencySOService;
import org.dainn.agencyservice.util.enums.Icon;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgencySOService implements IAgencySOService {
    private final IAgencySORepository agencySORepository;
    private final IAgencySOMapper agencySOMapper;

    @Transactional
    @Override
    public List<AgencySODto> create(Agency agency) {
        List<AgencySidebarOption> options = initAgencySO(agency.getId()).stream().map(agencySODto -> {
            AgencySidebarOption option = agencySOMapper.toEntity(agencySODto);
            option.setAgency(agency);
            return option;
        }).toList();
        options = agencySORepository.saveAll(options);
        return options.stream().map(agencySOMapper::toDto).toList();
    }

    private List<AgencySODto> initAgencySO(String agencyId) {
        return List.of(
                AgencySODto.builder()
                        .name("Dashboard")
                        .icon(Icon.category)
                        .link("/agency/" + agencyId)
                        .build(),

                AgencySODto.builder()
                        .name("Launchpad")
                        .icon(Icon.clipboardIcon)
                        .link("/agency/" + agencyId + "/launchpad")
                        .build(),

                AgencySODto.builder()
                        .name("Billing")
                        .icon(Icon.payment)
                        .link("/agency/" + agencyId + "/billing")
                        .build(),

                AgencySODto.builder()
                        .name("Settings")
                        .icon(Icon.settings)
                        .link("/agency/" + agencyId + "/settings")
                        .build(),

                AgencySODto.builder()
                        .name("Sub Accounts")
                        .icon(Icon.person)
                        .link("/agency/" + agencyId + "/all-subaccounts")
                        .build(),

                AgencySODto.builder()
                        .name("Team")
                        .icon(Icon.shield)
                        .link("/agency/" + agencyId + "/team")
                        .build()
        );
    }

    @Override
    public AgencySODto findById(String id) {
        return null;
    }

    @Transactional
    @Override
    public AgencySODto update(AgencySODto dto) {
        return null;
    }


    @Transactional
    @Override
    public void delete(String id) {
        agencySORepository.deleteById(id);
    }

    @Override
    public List<AgencySODto> findByAgency(String agencyId) {
        return agencySORepository.findAllByAgencyId(agencyId)
                .stream().map(agencySOMapper::toDto).toList();
    }
}
