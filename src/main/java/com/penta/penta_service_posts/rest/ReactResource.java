package com.penta.penta_service_posts.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.penta.penta_service_posts.enums.ReactType;
import com.penta.penta_service_posts.model.ReactDTO;
import com.penta.penta_service_posts.service.ReactService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;


@RestController
@RequestMapping(value = "/api/reacts", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReactResource {

    private final ReactService reactService;

    public ReactResource(final ReactService reactService) {
        this.reactService = reactService;
    }

    @GetMapping
    public ResponseEntity<List<ReactDTO>> getAllReacts() {
        return ResponseEntity.ok(reactService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ReactDTO>> getpostReacts(@PathVariable(name = "id") final UUID id) {
        return ResponseEntity.ok(reactService.getPostReacts(id));
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<ReactDTO> getReact(@PathVariable(name = "id") final UUID id) {
    //     return ResponseEntity.ok(reactService.get(id));
    // }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createReact(@RequestBody @Valid final ReactDTO reactDTO) {
        final UUID createdId = reactService.create(reactDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UUID> updateReact(@PathVariable(name = "id") final UUID id,
            @RequestBody @Valid final ReactType type ) {
        reactService.update(id, type );
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteReact(@PathVariable(name = "id") final UUID id) {
        reactService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
