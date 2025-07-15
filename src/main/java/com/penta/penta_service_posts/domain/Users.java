package com.penta.penta_service_posts.domain;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data 
@AllArgsConstructor 
@NoArgsConstructor 
@Table(name="user_entity")
public class Users {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column
    private String username;

}
