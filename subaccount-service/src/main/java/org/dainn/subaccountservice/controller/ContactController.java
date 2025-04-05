package org.dainn.subaccountservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dainn.subaccountservice.config.endpoint.Endpoint;
import org.dainn.subaccountservice.dto.contact.ContactDto;
import org.dainn.subaccountservice.dto.contact.ContactReq;
import org.dainn.subaccountservice.service.IContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.Contact.BASE)
@RequiredArgsConstructor
public class ContactController {
    private final IContactService contactService;

    @GetMapping(Endpoint.Contact.SUB_ACCOUNT)
    public ResponseEntity<?> getAll(@PathVariable String id, @ModelAttribute ContactReq request) {
        return ResponseEntity.ok(contactService.findBySA(id, request));
    }

    @GetMapping(Endpoint.Contact.ID)
    public ResponseEntity<?> getById(@PathVariable String id) {
        return ResponseEntity.ok(contactService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ContactDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contactService.create(dto));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(String id) {
        contactService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
