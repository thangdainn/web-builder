package org.dainn.agencyservice.controller;


import lombok.RequiredArgsConstructor;
import org.dainn.agencyservice.config.endpoint.Endpoint;
import org.dainn.agencyservice.dto.AgencyDto;
import org.dainn.agencyservice.dto.UpdateGoalDto;
import org.dainn.agencyservice.service.IAgencyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.Agency.BASE)
@RequiredArgsConstructor
public class AgencyController {
    private final IAgencyService agencyService;

    @GetMapping(Endpoint.Agency.ID)
    public ResponseEntity<?> get(@PathVariable String id) {
        return ResponseEntity.ok(agencyService.findById(id));
    }

    @GetMapping(Endpoint.Agency.CUSTOMER)
    public ResponseEntity<?> getByCustomerId(@PathVariable String id) {
        return ResponseEntity.ok(agencyService.findByCustomerId(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AgencyDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agencyService.create(dto));
    }

    @PutMapping(Endpoint.Agency.ID)
    public ResponseEntity<?> update(@RequestBody AgencyDto dto) {
        return ResponseEntity.ok(agencyService.update(dto));
    }

    @PutMapping(Endpoint.Agency.GOAL)
    public ResponseEntity<?> updateGoal(@PathVariable String id, @RequestBody UpdateGoalDto dto) {
        return ResponseEntity.ok(agencyService.updateGoal(id, dto));
    }

    @PutMapping(Endpoint.Agency.CONNECT_ACC_ID)
    public ResponseEntity<?> updateConnectAccId(@PathVariable String id, @RequestBody String connectAccId) {
        agencyService.updateConnectAccId(id, connectAccId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> delete(String id) {
        agencyService.delete(id);
        return ResponseEntity.ok().build();
    }
}
