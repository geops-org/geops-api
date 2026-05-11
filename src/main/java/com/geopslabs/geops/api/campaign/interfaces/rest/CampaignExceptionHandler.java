package com.geopslabs.geops.api.campaign.interfaces.rest;

import com.geopslabs.geops.api.campaign.domain.model.exceptions.CampaignUserNotFoundException;
import com.geopslabs.geops.api.campaign.domain.model.exceptions.InvalidCampaignRoleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice(basePackageClasses = CampaignController.class)
public class CampaignExceptionHandler {

    @ExceptionHandler(InvalidCampaignRoleException.class)
    public ResponseEntity<CampaignErrorResponse> handleInvalidRole(InvalidCampaignRoleException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new CampaignErrorResponse("CAMPAIGN_ROLE_FORBIDDEN", exception.getMessage()));
    }

    @ExceptionHandler(CampaignUserNotFoundException.class)
    public ResponseEntity<CampaignErrorResponse> handleUserNotFound(CampaignUserNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new CampaignErrorResponse("CAMPAIGN_USER_NOT_FOUND", exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CampaignErrorResponse> handleValidationErrors(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getAllErrors().stream()
                .map(error -> error instanceof FieldError fe
                        ? fe.getField() + ": " + fe.getDefaultMessage()
                        : error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return ResponseEntity.badRequest()
                .body(new CampaignErrorResponse("VALIDATION_ERROR", message));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CampaignErrorResponse> handleIllegalArgument(IllegalArgumentException exception) {
        return ResponseEntity.badRequest()
                .body(new CampaignErrorResponse("INVALID_ARGUMENT", exception.getMessage()));
    }
}
