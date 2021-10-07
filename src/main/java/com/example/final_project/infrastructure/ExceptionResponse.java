package com.example.final_project.infrastructure;

import lombok.Builder;
import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
public class ExceptionResponse {

    private String message;

    private int status;

    private String error;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime timestamp;

    private String path;
}
