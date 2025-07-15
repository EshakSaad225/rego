package com.penta.penta_service_posts.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.penta.penta_service_posts.domain.Post;
import com.penta.penta_service_posts.domain.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class SaveForLaterDTO {

    private UUID id;
    private Post post;
    private Users createdBy;
    private LocalDateTime createdAt;

}
