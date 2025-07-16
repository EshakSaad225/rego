package com.penta.penta_service_posts.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.penta.penta_service_posts.domain.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {
    List<Users> findByIdIn(List<String> ids);
}
