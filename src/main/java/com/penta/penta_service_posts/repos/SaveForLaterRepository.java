package com.penta.penta_service_posts.repos;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.penta.penta_service_posts.domain.SaveForLater;


public interface SaveForLaterRepository extends JpaRepository<SaveForLater, UUID> {
    List<SaveForLater> findByCreatedById(UUID userId);
}
