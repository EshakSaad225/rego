package com.penta.penta_service_posts.repos;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.penta.penta_service_posts.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findByCreatedById(String userId);
}
