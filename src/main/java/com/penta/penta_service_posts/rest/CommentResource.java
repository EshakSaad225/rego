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

import com.penta.penta_service_posts.model.CommentDTO;
import com.penta.penta_service_posts.service.CommentService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;


@RestController
@RequestMapping(value = "/api/comments", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentResource {

    private final CommentService commentService;

    public CommentResource(final CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        return ResponseEntity.ok(commentService.findAll());
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<List<CommentDTO>> getPostComments(@PathVariable(name = "id") final UUID id) {
        return ResponseEntity.ok(commentService.getPostComments(id));
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<CommentDTO> getComment(@PathVariable(name = "id") final UUID id) {
    //     return ResponseEntity.ok(commentService.get(id));
    // }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createComment(@RequestBody @Valid final CommentDTO commentDTO) {
        final UUID createdId = commentService.create(commentDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UUID> updateComment(@PathVariable(name = "id") final UUID id,
            @RequestBody @Valid final CommentDTO commentDTO) {
        commentService.update(id, commentDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteComment(@PathVariable(name = "id") final UUID id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
