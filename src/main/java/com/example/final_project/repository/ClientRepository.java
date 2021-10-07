package com.example.final_project.repository;

import com.example.final_project.model.Client;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Long> {

   Optional<Client> findByEmail(String email);
}
