package com.penta.penta_service_posts.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.penta.penta_service_posts.Converter.SpringSecurityAuditorAware;
import com.penta.penta_service_posts.domain.Report;
import com.penta.penta_service_posts.domain.Users;
import com.penta.penta_service_posts.model.ReportDTO;
import com.penta.penta_service_posts.repos.ReportRepository;
import com.penta.penta_service_posts.util.NotFoundException;


@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final SpringSecurityAuditorAware auditorAware;

    public ReportService(final ReportRepository reportRepository , final SpringSecurityAuditorAware auditorAware ) {
        this.reportRepository = reportRepository;
        this.auditorAware = auditorAware ;
    }

    public List<ReportDTO> findAll() {
        final List<Report> reports = reportRepository.findAll(Sort.by("id"));
        return reports.stream()
                .map(report -> mapToDTO(report, new ReportDTO()))
                .toList();
    }

    public ReportDTO get(final UUID id) {
        return reportRepository.findById(id)
                .map(report -> mapToDTO(report, new ReportDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final ReportDTO reportDTO) {
        final Report report = new Report();
        mapToEntity(reportDTO, report);
        return reportRepository.save(report).getId();
    }

    public void update(final UUID id, final ReportDTO reportDTO) {
        final Report report = reportRepository.findById(id)
            .orElseThrow(NotFoundException::new);
        Optional<Users> currentUser = auditorAware.getCurrentAuditor();
        if(currentUser.isPresent() && report.getCreatedBy().getId().equals(currentUser.get().getId())){
            mapToEntity(reportDTO, report);
            reportRepository.save(report);
        }
        else {
            throw new AccessDeniedException("You are not allowed to update this Report.");
        }
    }

    public void delete(final UUID id) {
        final Report report = reportRepository.findById(id)
            .orElseThrow(NotFoundException::new);
                Optional<Users> currentUser = auditorAware.getCurrentAuditor();
        if(currentUser.isPresent() && report.getCreatedBy().getId().equals(currentUser.get().getId())){
            reportRepository.deleteById(id);
        }
        else {
            throw new AccessDeniedException("You are not allowed to Delete this Report.");
        }
    }

    private ReportDTO mapToDTO(final Report report, final ReportDTO reportDTO) {
        reportDTO.setId(report.getId());
        reportDTO.setPost(report.getPost());
        reportDTO.setReason(report.getReason());
        reportDTO.setReasonText(report.getReasonText());
        reportDTO.setCreatedBy(report.getCreatedBy());
        reportDTO.setCreatedAt(report.getCreatedAt());
        reportDTO.setUpdatedAt(report.getUpdatedAt());
        return reportDTO;
    }

    private Report mapToEntity(final ReportDTO reportDTO, final Report report) {
        report.setPost(reportDTO.getPost());
        report.setReason(reportDTO.getReason());
        report.setReasonText(reportDTO.getReasonText());
        return report;
    }

}
