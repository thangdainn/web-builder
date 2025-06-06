package org.dainn.userservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dainn.userservice.config.endpoint.Endpoint;
import org.dainn.userservice.dto.subaccount.*;
import org.dainn.userservice.service.ISubAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.SubAccount.BASE)
@RequiredArgsConstructor
public class SubAccountController {
    private final ISubAccountService subAccountService;

    @GetMapping
    public ResponseEntity<?> getAll(@ModelAttribute SubAccountReq request) {
        return ResponseEntity.ok(subAccountService.findAll(request));
    }

    @GetMapping(Endpoint.SubAccount.ID)
    public ResponseEntity<?> getById(@PathVariable String id) {
        return ResponseEntity.ok(subAccountService.findById(id));
    }

    @GetMapping(Endpoint.SubAccount.AGENCY)
    public ResponseEntity<?> getByAgencyId(@PathVariable String id) {
        return ResponseEntity.ok(subAccountService.findByAgencyId(id));
    }

    @GetMapping(Endpoint.SubAccount.DETAIL)
    public ResponseEntity<?> getDetailById(@PathVariable String id) {
        return ResponseEntity.ok(subAccountService.findDetailById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateSubAccount dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subAccountService.create(dto));
    }

    @PutMapping(Endpoint.SubAccount.ID)
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody UpdateSubAccount dto) {
        return ResponseEntity.ok(subAccountService.update(id, dto));
    }

    @PutMapping(Endpoint.SubAccount.CONNECT_ACC_ID)
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody UpdateConnectAccDto dto) {
        subAccountService.updateConnectAccId(id, dto.getConnectAccountId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@Valid @RequestBody DeleteSubAccount dto) {
        subAccountService.delete(dto.getId(), dto.getEmail());
        return ResponseEntity.ok().build();
    }
}
