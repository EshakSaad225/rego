package com.penta.penta_service_posts.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.penta.penta_service_posts.domain.Report;
import com.penta.penta_service_posts.model.ReportDTO;
import com.penta.penta_service_posts.repos.ReportRepository;
import com.penta.penta_service_posts.util.NotFoundException;


@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(final ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
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
        mapToEntity(reportDTO, report);
        reportRepository.save(report);
    }

    public void delete(final UUID id) {
        reportRepository.deleteById(id);
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
        report.setCreatedBy(reportDTO.getCreatedBy());
        return report;
    }

}
