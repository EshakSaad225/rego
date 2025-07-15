package com.penta.penta_service_posts.repos;

import com.penta.penta_service_posts.domain.Report;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReportRepository extends JpaRepository<Report, UUID> {
}
