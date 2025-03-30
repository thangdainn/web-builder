package org.dainn.subaccountservice.controller;


import lombok.RequiredArgsConstructor;
import org.dainn.subaccountservice.config.endpoint.Endpoint;
import org.dainn.subaccountservice.dto.SubAccountDto;
import org.dainn.subaccountservice.service.ISubAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.SubAccount.BASE)
@RequiredArgsConstructor
public class SubAccountController {
    private final ISubAccountService subAccountService;

    @GetMapping(Endpoint.SubAccount.ID)
//    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> get(@PathVariable String id) {
        return ResponseEntity.ok(subAccountService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody SubAccountDto dto) {
        return ResponseEntity.ok(subAccountService.create(dto));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(String id) {
        subAccountService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
