package org.dainn.pipelineservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dainn.pipelineservice.config.endpoint.Endpoint;
import org.dainn.pipelineservice.dto.tag.TagDto;
import org.dainn.pipelineservice.service.ITagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.Tag.BASE)
@RequiredArgsConstructor
public class TagController {
    private final ITagService tagService;

    @GetMapping(Endpoint.Tag.ID)
    public ResponseEntity<?> get(@PathVariable String id) {
        return ResponseEntity.ok(tagService.findById(id));
    }

    @GetMapping(Endpoint.Tag.SUB_ACCOUNT)
    public ResponseEntity<?> getBySubAccount(@PathVariable String id) {
        return ResponseEntity.ok(tagService.findBySubAccount(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody TagDto dto) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.create(dto));
    }

    @PutMapping(Endpoint.Tag.ID)
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody TagDto dto) throws Exception {
        dto.setId(id);
        return ResponseEntity.ok(tagService.update(dto));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(String id) {
        tagService.delete(id);
        return ResponseEntity.ok().build();
    }
}
