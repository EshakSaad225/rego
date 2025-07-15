package com.penta.penta_service_posts.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.penta.penta_service_posts.domain.SaveForLater;
import com.penta.penta_service_posts.model.SaveForLaterDTO;
import com.penta.penta_service_posts.repos.SaveForLaterRepository;
import com.penta.penta_service_posts.util.NotFoundException;


@Service
public class SaveForLaterService {

    private final SaveForLaterRepository saveForLaterRepository;

    public SaveForLaterService(final SaveForLaterRepository saveForLaterRepository) {
        this.saveForLaterRepository = saveForLaterRepository;
    }

    public List<SaveForLaterDTO> findAll() {
        final List<SaveForLater> saveForLaters = saveForLaterRepository.findAll(Sort.by("id"));
        return saveForLaters.stream()
                .map(saveForLater -> mapToDTO(saveForLater, new SaveForLaterDTO()))
                .toList();
    }

    public List<SaveForLaterDTO> getUserSaveForLaterPosts(UUID userId) {
        final List<SaveForLater> saveForLaters = saveForLaterRepository.findByCreatedById(userId);
        return saveForLaters.stream()
                .map(saveForLater -> mapToDTO(saveForLater, new SaveForLaterDTO()))
                .toList();
    }

    public SaveForLaterDTO get(final UUID id) {
        return saveForLaterRepository.findById(id)
                .map(saveForLater -> mapToDTO(saveForLater, new SaveForLaterDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final SaveForLaterDTO saveForLaterDTO) {
        final SaveForLater saveForLater = new SaveForLater();
        mapToEntity(saveForLaterDTO, saveForLater);
        return saveForLaterRepository.save(saveForLater).getId();
    }

    public void delete(final UUID id) {
        saveForLaterRepository.deleteById(id);
    }

    private SaveForLaterDTO mapToDTO(final SaveForLater saveForLater,
            final SaveForLaterDTO saveForLaterDTO) {
        saveForLaterDTO.setId(saveForLater.getId());
        saveForLaterDTO.setPost(saveForLater.getPost());
        saveForLaterDTO.setCreatedBy(saveForLater.getCreatedBy());
        saveForLaterDTO.setCreatedAt(saveForLater.getCreatedAt());
        return saveForLaterDTO;
    }

    private SaveForLater mapToEntity(final SaveForLaterDTO saveForLaterDTO,
            final SaveForLater saveForLater) {
        saveForLater.setPost(saveForLaterDTO.getPost());
        saveForLater.setCreatedBy(saveForLaterDTO.getCreatedBy());
        return saveForLater;
    }

}
