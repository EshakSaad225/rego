package com.penta.penta_service_posts.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.penta.penta_service_posts.domain.Post;
import com.penta.penta_service_posts.domain.Users;
import com.penta.penta_service_posts.enums.ReportType;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class ReportDTO {

    private UUID id;

    private Post post;

    private ReportType reason;

    @Size(max = 255)
    private String reasonText;

    private Users createdBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
