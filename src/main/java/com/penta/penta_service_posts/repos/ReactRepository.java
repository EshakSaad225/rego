package com.penta.penta_service_posts.repos;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.penta.penta_service_posts.domain.React;


public interface ReactRepository extends JpaRepository<React, UUID> {
    List<React> findByPostId(UUID postId);
}
