package com.penta.penta_service_posts.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.penta.penta_service_posts.domain.Users;
import com.penta.penta_service_posts.model.UsersDTO;
import com.penta.penta_service_posts.repos.UsersRepository;
import com.penta.penta_service_posts.util.NotFoundException;


@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(final UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<UsersDTO> findAll() {
        final List<Users> users = usersRepository.findAll();
        return users.stream()
                .map(user -> mapToDTO(user, new UsersDTO()))
                .toList();
    }

    public UsersDTO get(final String id) {
        return usersRepository.findById(id)
                .map(users -> mapToDTO(users, new UsersDTO()))
                .orElseThrow(NotFoundException::new);
    }

    private UsersDTO mapToDTO(final Users users, final UsersDTO usersDTO) {
        usersDTO.setId(users.getId());
        usersDTO.setUsername(users.getUsername());
        return usersDTO;
    }

}
