package com.penta.penta_service_posts.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.penta.penta_service_posts.domain.Comment;
import com.penta.penta_service_posts.domain.Post;
import com.penta.penta_service_posts.domain.Users;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data 
@AllArgsConstructor 
@NoArgsConstructor 

public class CommentDTO {

    private UUID id;

    private Post post;

    private Comment parent;

    @Size(max = 255)
    private String text;

    private List<@Size(max = 255) String> attachments;

    private Integer score;

    private Users createdBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<Users> mentions;

}
