package com.penta.penta_service_posts.model;

import java.util.UUID;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class UsersDTO {

    private UUID id;

    @Size(max = 255)
    private String username;

}
