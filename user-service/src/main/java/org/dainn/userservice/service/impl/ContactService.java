package org.dainn.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dainn.userservice.dto.PageResponse;
import org.dainn.userservice.dto.contact.ContactDto;
import org.dainn.userservice.dto.contact.ContactReq;
import org.dainn.userservice.exception.AppException;
import org.dainn.userservice.exception.ErrorCode;
import org.dainn.userservice.feignclient.ITicketClient;
import org.dainn.userservice.mapper.IContactMapper;
import org.dainn.userservice.model.Contact;
import org.dainn.userservice.repository.IContactRepository;
import org.dainn.userservice.repository.ISubAccountRepository;
import org.dainn.userservice.service.IContactService;
import org.dainn.userservice.util.Paging;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ContactService implements IContactService {
    private final ISubAccountRepository subAccountRepository;
    private final IContactRepository contactRepository;
    private final IContactMapper contactMapper;
    private final ITicketClient ticketClient;

    @Transactional
    @Override
    public ContactDto create(ContactDto dto) {
        contactRepository.findByEmailAndSubAccountId(dto.getEmail(), dto.getSubAccountId())
                .ifPresent(contact -> {
                    throw new AppException(ErrorCode.EMAIL_EXISTED);
                });
        Contact contact = contactMapper.toEntity(dto);
        contact.setSubAccount(subAccountRepository.findById(dto.getSubAccountId())
                .orElseThrow(() -> new AppException(ErrorCode.SA_NOT_EXISTED)));
        return contactMapper.toDto(contactRepository.save(contact));
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        subAccountRepository.deleteById(id);
    }

    @Override
    public ContactDto findById(String id) {
        return contactMapper.toDto(contactRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CONTACT_NOT_EXISTED)));
    }

    @Override
    public PageResponse<ContactDto> findBySA(String subAccountId, ContactReq request) {
        Pageable pageable = Paging.getPageable(request);
        Page<Contact> page;
        if (StringUtils.hasText(request.getKeyword())) {
            page = contactRepository.findBySubAccountIdAndEmailContainingIgnoreCase(subAccountId, request.getKeyword(), pageable);
        } else {
            page = contactRepository.findBySubAccountId(subAccountId, pageable);
        }
        Page<ContactDto> result = page.map(item -> {
                    ContactDto dto = contactMapper.toDto(item);
                    boolean isActive = Boolean.TRUE.equals(ticketClient.isAssigned(dto.getId()).getBody());
                    dto.setActive(isActive);
                    return dto;
                }
        );
        return PageResponse.<ContactDto>builder()
                .page(result.getNumber())
                .size(result.getSize())
                .totalElements(result.getTotalElements())
                .content(result.getContent())
                .build();
    }
}
