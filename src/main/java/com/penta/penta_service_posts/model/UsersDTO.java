package com.penta.penta_service_posts.model;

import com.penta.penta_service_posts.domain.UserAttribute;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class UsersDTO {

    private String id;

    @Size(max = 255)
    private String username;

    private UserAttribute userAttribute ;

}
