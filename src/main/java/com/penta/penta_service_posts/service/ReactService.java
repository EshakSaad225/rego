package com.penta.penta_service_posts.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.penta.penta_service_posts.domain.React;
import com.penta.penta_service_posts.enums.ReactType;
import com.penta.penta_service_posts.model.ReactDTO;
import com.penta.penta_service_posts.repos.ReactRepository;
import com.penta.penta_service_posts.util.NotFoundException;


@Service
public class ReactService {

    private final ReactRepository reactRepository;

    public ReactService(final ReactRepository reactRepository) {
        this.reactRepository = reactRepository;
    }

    public List<ReactDTO> findAll() {
        final List<React> reacts = reactRepository.findAll(Sort.by("id"));
        return reacts.stream()
                .map(react -> mapToDTO(react, new ReactDTO()))
                .toList();
    }

    public List<ReactDTO> getPostReacts(UUID postId) {
        final List<React> reacts = reactRepository.findByPostId(postId);
        return reacts.stream()
                .map(react -> mapToDTO(react, new ReactDTO()))
                .toList();
    }

    // public ReactDTO get(final UUID id) {
    //     return reactRepository.findById(id)
    //             .map(react -> mapToDTO(react, new ReactDTO()))
    //             .orElseThrow(NotFoundException::new);
    // }

    public UUID create(final ReactDTO reactDTO) {
        final React react = new React();
        mapToEntity(reactDTO, react);
        return reactRepository.save(react).getId();
    }

    public void update(final UUID id, final ReactType type ) {
        final React react = reactRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        react.setType(type);
        reactRepository.save(react);
    }

    public void delete(final UUID id) {
        reactRepository.deleteById(id);
    }

    private ReactDTO mapToDTO(final React react, final ReactDTO reactDTO) {
        reactDTO.setId(react.getId());
        reactDTO.setType(react.getType());
        reactDTO.setPost(react.getPost());
        reactDTO.setCreatedBy(react.getCreatedBy());
        return reactDTO;
    }

    private React mapToEntity(final ReactDTO reactDTO, final React react) {
        react.setType(reactDTO.getType());
        react.setPost(reactDTO.getPost());
        react.setCreatedBy(reactDTO.getCreatedBy());
        return react;
    }

}
