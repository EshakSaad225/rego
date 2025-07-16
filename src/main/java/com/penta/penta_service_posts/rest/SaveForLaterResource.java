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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.penta.penta_service_posts.model.SaveForLaterDTO;
import com.penta.penta_service_posts.service.SaveForLaterService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;


@RestController
@RequestMapping(value = "/api/saveForLaters", produces = MediaType.APPLICATION_JSON_VALUE)
public class SaveForLaterResource {

    private final SaveForLaterService saveForLaterService;

    public SaveForLaterResource(final SaveForLaterService saveForLaterService) {
        this.saveForLaterService = saveForLaterService;
    }

    @GetMapping
    public ResponseEntity<List<SaveForLaterDTO>> getAllSaveForLaters() {
        return ResponseEntity.ok(saveForLaterService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<SaveForLaterDTO>> getUsersSaveForLaterPosts(
            @PathVariable(name = "id") final String id) {
        return ResponseEntity.ok(saveForLaterService.getUserSaveForLaterPosts(id));
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<SaveForLaterDTO> getSaveForLater(
    //         @PathVariable(name = "id") final UUID id) {
    //     return ResponseEntity.ok(saveForLaterService.get(id));
    // }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createSaveForLater(
            @RequestBody @Valid final SaveForLaterDTO saveForLaterDTO) {
        final UUID createdId = saveForLaterService.create(saveForLaterDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSaveForLater(@PathVariable(name = "id") final UUID id) {
        saveForLaterService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
