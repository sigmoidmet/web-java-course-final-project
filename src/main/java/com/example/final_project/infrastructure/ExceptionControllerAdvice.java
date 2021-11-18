package com.example.final_project.infrastructure;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException responseStatusException,
                                                           HttpServletRequest request) {
        return createResponseEntity(responseStatusException,
                                    request,
                                    responseStatusException.getStatus(),
                                    responseStatusException.getReason());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> handleValidationExceptions(BindException bindException,
                                                           HttpServletRequest request) {

        return createResponseEntity(bindException,
                                    request,
                                    HttpStatus.BAD_REQUEST,
                                    getValidationReason(bindException));
    }

    private String getValidationReason(BindException bindException) {
        return bindException.getFieldErrors().stream()
                .collect(groupingBy(ObjectError::getObjectName))
                .entrySet().stream()
                .collect(toMap(Map.Entry::getKey,
                               entry -> entry.getValue().stream()
                                       .map(fieldError -> FieldValidation.builder()
                                               .field(fieldError.getField())
                                               .message(fieldError.getDefaultMessage())
                                               .rejectedValue(ObjectUtils.nullSafeToString(fieldError.getRejectedValue()))
                                               .build())
                                       .collect(Collectors.toList()))
                ).toString();
    }

    @ExceptionHandler({HttpMessageConversionException.class, HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<?> handleBadRequestException(RuntimeException badRequestException, HttpServletRequest request) {

        return createResponseEntity(badRequestException,
                                    request,
                                    HttpStatus.BAD_REQUEST,
                                    badRequestException.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException badCredentialsException,
                                                               HttpServletRequest request) {

        return createResponseEntity(badCredentialsException,
                                    request,
                                    HttpStatus.BAD_REQUEST,
                                    badCredentialsException.getMessage());
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<?> handleBadCredentialsException(DisabledException disabledException,
                                                           HttpServletRequest request) {

        return createResponseEntity(disabledException,
                                    request,
                                    HttpStatus.LOCKED,
                                    disabledException.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobal(Exception exception, HttpServletRequest request) {
        return createResponseEntity(exception,
                                    request,
                                    HttpStatus.INTERNAL_SERVER_ERROR,
                                    "");
    }

    private ResponseEntity<?> createResponseEntity(Exception exception,
                                                   HttpServletRequest webRequest,
                                                   HttpStatus status,
                                                   String reason) {
        if (status == HttpStatus.INTERNAL_SERVER_ERROR) {
            log.error("Some error", exception);
        }

        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .message(reason)
                        .path(webRequest.getRequestURI())
                        .timestamp(LocalDateTime.now())
                        .status(status.value())
                        .error(status.getReasonPhrase())
                        .build(),
                status
        );
    }
}
