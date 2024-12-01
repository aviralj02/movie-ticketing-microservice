package com.user_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user_service.entity.Client;

public interface UserRepository extends JpaRepository<Client, Long> {
	Optional<Client> findByUsername(String username);
}
