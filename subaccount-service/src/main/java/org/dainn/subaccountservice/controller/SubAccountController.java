package org.dainn.subaccountservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dainn.subaccountservice.config.endpoint.Endpoint;
import org.dainn.subaccountservice.dto.subaccount.CreateSubAccount;
import org.dainn.subaccountservice.dto.subaccount.SubAccountDto;
import org.dainn.subaccountservice.dto.subaccount.SubAccountReq;
import org.dainn.subaccountservice.service.ISubAccountService;
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
    public ResponseEntity<?> get(@PathVariable String id) {
        return ResponseEntity.ok(subAccountService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateSubAccount dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subAccountService.create(dto));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(String id) {
        subAccountService.deleteBy(id);
        return ResponseEntity.ok().build();
    }
}
