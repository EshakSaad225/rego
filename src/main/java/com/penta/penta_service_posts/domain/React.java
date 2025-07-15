package com.penta.penta_service_posts.domain;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.penta.penta_service_posts.enums.ReactType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
    
@Entity
@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class React {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Enumerated(EnumType.ORDINAL)
    @Column
    private ReactType type;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by")
    private Users createdBy;

}
