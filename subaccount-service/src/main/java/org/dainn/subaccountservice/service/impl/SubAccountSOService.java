package org.dainn.subaccountservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.subaccountservice.dto.subaccount.SubAccountSODto;
import org.dainn.subaccountservice.mapper.ISubAccountSOMapper;
import org.dainn.subaccountservice.model.SubAccount;
import org.dainn.subaccountservice.model.SubAccountSidebarOption;
import org.dainn.subaccountservice.repository.ISubAccountSORepository;
import org.dainn.subaccountservice.service.ISubAccountSOService;
import org.dainn.subaccountservice.util.enums.Icon;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubAccountSOService implements ISubAccountSOService {
    private final ISubAccountSORepository subAccountSORepository;
    private final ISubAccountSOMapper subAccountSOMapper;


    @Transactional
    @Override
    public List<SubAccountSODto> create(SubAccount subAccount) {
        List<SubAccountSidebarOption> options = initSubAccountSO(subAccount.getId()).stream().map(agencySODto -> {
            SubAccountSidebarOption option = subAccountSOMapper.toEntity(agencySODto);
            option.setSubAccount(subAccount);
            return option;
        }).toList();
        options = subAccountSORepository.saveAll(options);
        return options.stream().map(subAccountSOMapper::toDto).toList();
    }

    @Transactional
    @Override
    public void delete(String id) {
        subAccountSORepository.deleteById(id);
    }

    @Transactional
    @Override
    public void update(String id) {

    }

    @Override
    public SubAccountSODto findById(String id) {
        return null;
    }

    @Override
    public List<SubAccountSODto> findBySA(String saId) {
        return subAccountSORepository.findAllBySubAccountId(saId).stream()
                .map(subAccountSOMapper::toDto).toList();
    }

    @Override
    public Page<SubAccountSODto> findAll() {
        return null;
    }

    private List<SubAccountSODto> initSubAccountSO(String subAccountId) {
        return List.of(
                SubAccountSODto.builder()
                        .name("Launchpad")
                        .icon(Icon.clipboardIcon)
                        .link("/subaccount/" + subAccountId + "/launchpad")
                        .build(),

                SubAccountSODto.builder()
                        .name("Settings")
                        .icon(Icon.settings)
                        .link("/subaccount/" + subAccountId + "/settings")
                        .build(),

                SubAccountSODto.builder()
                        .name("Funnels")
                        .icon(Icon.pipelines)
                        .link("/subaccount/" + subAccountId + "/funnels")
                        .build(),

                SubAccountSODto.builder()
                        .name("Media")
                        .icon(Icon.database)
                        .link("/subaccount/" + subAccountId + "/media")
                        .build(),

                SubAccountSODto.builder()
                        .name("Automations")
                        .icon(Icon.chip)
                        .link("/subaccount/" + subAccountId + "/automations")
                        .build(),

                SubAccountSODto.builder()
                        .name("Pipelines")
                        .icon(Icon.flag)
                        .link("/subaccount/" + subAccountId + "/pipelines")
                        .build(),

                SubAccountSODto.builder()
                        .name("Contacts")
                        .icon(Icon.person)
                        .link("/subaccount/" + subAccountId + "/contacts")
                        .build(),

                SubAccountSODto.builder()
                        .name("Dashboard")
                        .icon(Icon.category)
                        .link("/subaccount/" + subAccountId)
                        .build()
        );
    }

}
