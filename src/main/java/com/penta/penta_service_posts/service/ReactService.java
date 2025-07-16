package com.penta.penta_service_posts.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.penta.penta_service_posts.Converter.SpringSecurityAuditorAware;
import com.penta.penta_service_posts.domain.React;
import com.penta.penta_service_posts.domain.Users;
import com.penta.penta_service_posts.enums.ReactType;
import com.penta.penta_service_posts.model.ReactDTO;
import com.penta.penta_service_posts.repos.ReactRepository;
import com.penta.penta_service_posts.util.NotFoundException;


@Service
public class ReactService {

    private final ReactRepository reactRepository;
    private final SpringSecurityAuditorAware auditorAware;

    public ReactService(final ReactRepository reactRepository , final SpringSecurityAuditorAware auditorAware) {
        this.reactRepository = reactRepository;
        this.auditorAware = auditorAware ;
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
        Optional<Users> currentUser = auditorAware.getCurrentAuditor();
        if(currentUser.isPresent() && react.getCreatedBy().getId().equals(currentUser.get().getId())){
            react.setType(type);
            reactRepository.save(react);
        }
        else {
            throw new AccessDeniedException("You are not allowed to update this React.");
        }
    }

    public void delete(final UUID id) {
        final React react = reactRepository.findById(id)
            .orElseThrow(NotFoundException::new);
        Optional<Users> currentUser = auditorAware.getCurrentAuditor();
        System.out.println(currentUser.isPresent() && react.getCreatedBy().getId().equals(currentUser.get().getId()));
        System.out.println(currentUser.isPresent());
        System.out.println(react.getCreatedBy().getId().equals(currentUser.get().getId()));
        if(currentUser.isPresent() && react.getCreatedBy().getId().equals(currentUser.get().getId())){
            reactRepository.deleteById(id);
        }
        else {
            throw new AccessDeniedException("You are not allowed to Delete this React.");
        }
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
        return react;
    }

}
