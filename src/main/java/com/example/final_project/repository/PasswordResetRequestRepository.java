package com.example.final_project.repository;


import com.example.final_project.model.PasswordResetRequest;

import org.springframework.data.repository.CrudRepository;

public interface PasswordResetRequestRepository extends CrudRepository<PasswordResetRequest, String> {

}
