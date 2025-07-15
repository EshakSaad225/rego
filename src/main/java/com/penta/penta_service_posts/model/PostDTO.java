package com.penta.penta_service_posts.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.penta.penta_service_posts.domain.Post;
import com.penta.penta_service_posts.domain.Users;
import com.penta.penta_service_posts.enums.PostType;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class PostDTO {

    private UUID id;

    private PostType type;

    @Size(max = 255)
    private String text;

    private List<@Size(max = 255) String> attachments;

    @JsonProperty("isSaved")
    private Boolean isSaved;

    @JsonProperty("isShared")
    private Boolean isShared;

    private Post sharedPost;

    private Users createdBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<@Size(max = 255) String> moreData;

    private List<Users> mentions;

    private List<@Size(max = 255) String> hashtags;

}
