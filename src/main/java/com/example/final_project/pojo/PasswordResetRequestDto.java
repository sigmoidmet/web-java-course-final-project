package com.example.final_project.pojo;

import lombok.Data;

@Data
public class PasswordResetRequestDto {

    private String token;

    private String newPassword;
}
