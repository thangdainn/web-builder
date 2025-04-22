package org.dainn.userservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dainn.userservice.config.endpoint.Endpoint;
import org.dainn.userservice.dto.invitation.InvitationDto;
import org.dainn.userservice.dto.invitation.InvitationReq;
import org.dainn.userservice.dto.invitation.UserInfo;
import org.dainn.userservice.service.IInvitationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.Invitation.BASE)
@RequiredArgsConstructor
public class InvitationController {
    private final IInvitationService invitationService;

    @GetMapping
    public ResponseEntity<?> getAll(@ModelAttribute InvitationReq request) {
        return ResponseEntity.ok(invitationService.findAll(request));
    }

    @GetMapping(Endpoint.Invitation.AGENCY)
    public ResponseEntity<?> getByAgency(@PathVariable String id) {
        return ResponseEntity.ok(invitationService.findAllByAgency(id));
    }

    @GetMapping(Endpoint.Invitation.PENDING)
    public ResponseEntity<?> getEmailPending(@PathVariable String email) {
        return ResponseEntity.ok(invitationService.findByEmailPending(email));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody InvitationDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(invitationService.create(dto));
    }

    @PostMapping(Endpoint.Invitation.VERIFY)
    public ResponseEntity<?> verify(@Valid @RequestBody UserInfo userInfo) {
        return ResponseEntity.ok(invitationService.verify(userInfo));
    }

    @PutMapping
    public ResponseEntity<Void> updateStatus(@RequestBody InvitationDto dto) {
        invitationService.updateStatus(dto.getId(), dto.getStatus());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(String email) {
        invitationService.delete(email);
        return ResponseEntity.ok().build();
    }
}
